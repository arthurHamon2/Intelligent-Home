function HomeCtrl($scope, $routeParams) {
			
}

function AlertCtrl($scope) {

	  $scope.closeAlert = function(index) {
	    $scope.alerts.splice(index, 1);
	  };

	}

function MainCtrl($scope, $routeParams, $location, $http) {
	
		$scope.addAlert = function(message,type) {
		    $scope.alerts.push({ type: type, msg: message } );
		  };
		  
		$scope.resetAlerts = function() {
            $scope.alerts = [];
		  };
		  
		$scope.tryUserLogin = function() {
				$http({
					method: 'GET',
					url: "/login",
					}).success(function(data) {
					    $scope.resetAlerts();
					    $scope.user=data;
					    $scope.fetchHouses();
					    
					    
					
				  }).
			      error(function(data) {
					    $location.path('/login');
			      });
				
				  }
				  
		
	
		  
		  
	  $scope.logUserOut = function() {
	  
	  $http({
			method: 'POST',
			url: "/logout",
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
			    $scope.user=null; 
                $scope.resetAlerts();
			    $location.path('/login');
			
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
	  
	  };
	  
	  $scope.fetchHouses = function() {
			$http({method: 'GET',url: "/house"}).success(function(data) {
				$scope.houses = data;
				$scope.user.house = $scope.houses[$scope.user.house.id];
				
				if($scope.user.login=='admin'){ // TODO : a nettoyer
			    	$location.path('/setup');
			    }else{
			    	$location.path('/');
			    	$scope.setupFinished=true;
			    }
			  }).
		      error(function(data) {
		    	  $scope.addAlert('Server communication failed !!','error');
		      });
		}
	
	$scope.houses = [];
	
	
	$scope.alerts = [];
	
	$scope.tryUserLogin();
	
	
	  
}

function SetupCtrl($scope, $routeParams) {
	
}

function SetupPersonalInfoCtrl($scope, $routeParams, $location,$http) {

	
	$scope.save = function() {
		$http({
			method: 'PUT',
			url: "/user/1",data:$.param($scope.user),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
			$location.path('/setup/avatar');
			
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
		
	};

	
}

function SetupAvatarCtrl($scope, $routeParams,$location,$http) {

	$scope.theImages = [
			"amsterdam.jpg",
			"breadedcat.jpg",
			"chaton.jpg",
			"fingers.jpg",
			"fleur.jpg",
			"Koala.jpg",
			"prolog.jpg",
			"rock.jpg",
			"ski.jpg",
			"violaine.jpg"
		];


	$scope.save = function() {
		$http({
			method: 'PUT',
			url: "/user/1",data:$.param($scope.user),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
		$location.path('/setup/house');			
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
	};
	
}

function SetupHouseCtrl($scope,$routeParams,$location,$http) {


	$scope.save = function() {	
		$http({method: 'PUT', url: "/user/1",data:$.param($scope.user),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
			$location.path('/setup/rooms');			
		 	}).
	     	error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      	});		
	};
	


}

function SetupRoomsCtrl($scope, $routeParams,$location,$http) {


	

	
	$scope.save = function() { 
		var data = {'houseId':$scope.user.house.id,'rooms':{}};

		angular.forEach($scope.user.house.rooms, function(room) {
			data.rooms[room.id]=room.title;
		    });
		$http({
			method: 'PUT',
			url: "/house/",data:$.param(data),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
				$scope.setupFinished=true;
				console.log($scope.setupFinished);
				$location.path('/');			
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });		
	};
	

	
}

function LoginCtrl($scope, $routeParams,$location,$http) {

	$scope.login= function(){
		
		$http({
			method: 'POST',
			url: "/login",data:$.param($scope.d),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
			$scope.tryUserLogin();
			
		  }).
	      error(function(data) {
                $scope.addAlert('Wrong user/password combination!','error');
	      });
		
	}
}

function HouseCtrl($scope, $routeParams, $location, $http) {
	
}


function OperatorCtrl($scope, $routeParams, $location, $http) {
	$scope.newOp = {"type":{"title":"Type"},"room":{"title":"Pièce"}};
	
	$scope.fetch = function() {
		$http({method: 'GET',url: "/operatorType"}).success(function(data) {
				    
					$scope.operatorTypes = data;
					
			$http({method: 'GET',url: "/operator"}).success(function(data) {
									$scope.operators = data;
								  })
					
				  }).
			      error(function(data) {
			    	  $scope.addAlert('Server communication failed !!','error');
			      });
	}
	
	$scope.putOp = function() {
		$http({
			method: 'PUT',
			url: "/operator",data:$.param($scope.newOp),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
			$scope.fetch();
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
	}
	

	
	$scope.fetch();

}

function ActionCtrl($scope, $routeParams, $location, $http) {
	$scope.newAc={"type":{"title":"Actionneur"}};
	$scope.fetch = function() {
		$http({method: 'GET',url: "/operatorType"}).success(function(data) {
				    
					$scope.operatorTypes = data;
					
					$http({method: 'GET',url: "/action"}).success(function(data) {
					    
						$scope.actions = data;
						
					  })
					
				  }).
			      error(function(data) {
			    	  $scope.addAlert('Server communication failed !!','error');
			      });
	}
	
	$scope.putAc = function() {
		$http({
			method: 'PUT',
			url: "/action",data:$.param($scope.newAc),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
				$scope.fetch();
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
	}
	

	
	$scope.fetch();

}



function RoomCtrl($scope, $routeParams,$location,$http, $timeout, $filter) {
	
	

		$scope.selectedRoom = {"title":"Toutes"};

	angular.forEach($scope.user.house.rooms, function(room) {
		if($routeParams.roomId==room.id.toString()){
			$scope.selectedRoom = {"title":room.title};
			console.log([room.id.toString(),$routeParams.roomId]);
		}
		
	    });
	
	
	
	$scope.graphShow = [];
	$scope.time = new Date();
	    
	    $scope.$watch('time', function(){
	        $timeout(function(){
	            $scope.time = new Date();
	            $scope.fetch();
	            console.log(2);
	        },5000);
	    });
	
		
	$scope.executeAction = function($oid,$aid){
		$http({
			method: 'POST',
			url: "/operator/"+$oid,data:$.param({"actionId":$aid}),
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {

			
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
		
	}
	
	
	$scope.fetch = function() {
		$http({method: 'GET',url: "/sensor"}).success(function(data) {
		    
			$scope.sensors = data;
			
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
		$http({method: 'GET',url: "/operator"}).success(function(data) {
				    
					$scope.operators = data;
					
				  }).
			      error(function(data) {
			    	  $scope.addAlert('Server communication failed !!','error');
			      });
	}
	

	$scope.showMeasure = function(measure){
		if(measure.unity=='Time'){
			return $filter('date')(measure.value,'medium') ;
		}else{
			return measure.value+' '+measure.unity;
		}
	}
	
	
$scope.setAlarm = function(){
	$http({
		method: 'PUT',
		url: "/rule",
		data:$.param({
			'title':'Reveil',
			'contents':'ON M99999999 > M99999998 DO P4288617990:1',
			'enable':'true'
			}),
		headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
		

		
	  }).
      error(function(data) {
    	  $scope.addAlert('Server communication failed !!','error');
      });
}
	
	
	
	$scope.fetch();
			
	
}


function AdministrationCtrl($scope) {

}

function AdminUsersCtrl($scope) {
	
	$scope.fetch = function() {
		$http({method: 'GET',url: "/user"}).success(function(data) {
		    
			$scope.users = data;
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
	}
	
		   		
	$scope.giveName = function($row) {
		if ($row.name) {
			return $row.name;
		}
		else {
			return $row.id;
		}
	}	   		
		
}

function AdminSensorsCtrl($scope, $routeParams,$location,$http) {

		
	$scope.fetch = function() {
		$http({method: 'GET',url: "/sensorType"}).success(function(data) {
		    
			$scope.sensorTypes = data;
			$scope.fetchSensors();
			
		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });

	      $http({method: 'GET',url: "/user/1"}).success(function(data) {
		    
			$scope.rooms = data[0].rooms;
		}).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });
	}
	
	$scope.fetchSensors= function(){
		
		$http({method: 'GET',url: "/allsensor"}).success(function(data) {
		    
			$scope.sensors = data;

		  }).
	      error(function(data) {
	    	  $scope.addAlert('Server communication failed !!','error');
	      });		
	}

	$scope.selectedRoom = {"title":"Toutes"};
	
	
	$scope.save = function(sensor) {
			$http({
				method: 'PUT',
				url: "/allsensor/"+sensor.ref,
				data:$.param({
					'title':sensor.title,
					'idRoom':sensor.room.id,
					'idType':sensor.type.id,
					'installed':sensor.installed
					}),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
					$scope.fetchSensors();

				
			  }).
		      error(function(data) {
		    	  $scope.addAlert('Server communication failed !!','error');
		      });	
		
	};
	


	$scope.giveName = function($row) {
		if ($row.name) {
			return $row.name;
		}
		else {
			var theType = $row.type;
			return theType.defaultName+" "+$row.id;
		}
	}

	$scope.giveType = function($row) {
		var theType = $row.type;
		return theType.actCapt+" "+theType.ressource;
	}

	$scope.fetch();
	

}



function AdminRulesCtrl($scope, $routeParams,$location, $http) {

	// pour empêcher que les <li> se ferment quand on clique pour modifier les rowOn rowThen
	$scope.stopProp = function() {
	    $("ul.dropdown-menu").on("click", "[data-stopPropagation]", function(e) {e.stopPropagation(); });
	};

	$scope.removeFirst = function($mystring) {
		var res = "";
		var longueur = lastIndexOf($mystring);
	}

	// fonction qui retire $val de la liste $myarray
	$scope.removeByValue = function($myarray, $val) {
		for(var i=0; i < $myarray.length; i++) {
			if($myarray[i] == $val) {
				$myarray.splice(i, 1);
				break;
			}
		}
	}

	// trouver un objet dans une liste, à partir de son id
	$scope.getObjectById = function($objects,$id) {
		for (var i = 0; i<$objects.length; i++) {
			if ($objects[i].id == $id) {
				return $objects[i];
			}
		}
		return 0;
	}

	$scope.getObjectByRef = function($objects,$id) {
		for (var i = 0; i<$objects.length; i++) {
			if ($objects[i].ref == $id) {
				return $objects[i];
			}
		}
		return 0;
	}

	// data
	$scope.theInfSup = [
		{
			"value":"==",
			"string":"="
		},
		{
			"value":"<=",
			"string":"≤"
		},
		{
			"value":">=",
			"string":"≥"
		},
		{
			"value":"<",
			"string":"<"
		},
		{
			"value":">",
			"string":">"
		},
	]
	$scope.infSup_ValueToObject = function($value) {
		switch($value) {
			case "==": return $scope.theInfSup[0];
			case "<=": return $scope.theInfSup[1];
			case ">=": return $scope.theInfSup[2];
			case "<": return $scope.theInfSup[3];
			case ">": return $scope.theInfSup[4];
			default : return 0;
		}
	}

	// load
	$scope.fetch = function() {
		// récupérer les capteurs
		$http({method: 'GET',url: "/sensor"})
		.success(function(data) {
			$scope.sensors = data;
			$scope.measures = [];
			// récupérer les actionneurs
			$http({method: 'GET',url: "/operator"})
			.success(function(data) {
				$scope.operators = data;
				$scope.actions = [];
				// récupérer les pièces
				$scope.thePieces = $scope.user.house.rooms;
				// récupérer les règles
				$http({method: 'GET',url: "/rule"})
				.success(function(data) {
					$scope.rules = data;
					// faire les conversions + récupérer les actions
					$scope.getOns();
					$scope.getThens();
					$scope.getRows();
				})
				.error(function(data) {
					$scope.addAlert('Server communication failed !!','error');
				});					
			})
			.error(function(data) {
				$scope.addAlert('Server communication failed !!','error');
			});
				
		})
		.error(function(data) {
			$scope.addAlert('Server communication failed !!','error');
		});
	}

	// renvoie la liste des "On" (= ses mesures) associés à un sensor
	$scope.convertSensorToMeasures = function($sensor) {
		var res = [];

		var measures = $sensor.measures;
		for(var i=0;i<measures.length;i++) {
			$scope.measures.push(measures[i]);
			var currentMeasure = {
				"id":measures[i].id,
				"string":measures[i].nom,
				"unit":measures[i].unity,
				"icon":"images/sensors/"+$sensor.type.image,
				"sensor":{
					"id":$sensor.ref,
					"title":$sensor.title,
					"piece":{
						"id":$sensor.room.id,
						"title":$sensor.room.title
					}
				}
			};
			res.push(currentMeasure);
		}		

		return res;
	}

	// renvoie la liste des "Then" (= ses actions) associés à un operator
	$scope.convertOperatorToActions = function($operator) {
		var res = [];

		var actions = $operator.actions;
		for(var i=0;i<actions.length;i++) {
			$scope.actions.push(actions[i]);
			var currentAction = {
				"id":actions[i].id,
				"string":actions[i].title,
				"unit":"",
				"icon":"images/operators/"+$operator.type.image,
				"operator":{
					"id":$operator.ref,
					"title":$operator.title,
					"piece":{
						"id":$operator.room.id,
						"title":$operator.room.title
					}
				}
			};
			res.push(currentAction);
		}
		return res;
	}
	
	

	// fonction récupérant TOUTE la liste des mesures dans la BD
	$scope.getOns = function() {
		$scope.allOns = [];

		var sensors = $scope.sensors;
		if (sensors) {
			for (var i=0; i<$scope.sensors.length; i++ ) {
				// récupérer les ons associés au capteur
				var ons = $scope.convertSensorToMeasures(sensors[i]);
				for (var j=0;j<ons.length;j++){
					$scope.allOns.push(ons[j]);
				}
			}
		}
	}

	// fonction récupérant TOUTE la liste des opérateurs + actions dans la BD TEST
	$scope.getThens = function() {
		$scope.allThens = [];
		$scope.allThensActions = [];

		var operators = $scope.operators;
		if (operators) {
			for (var i=0; i<operators.length; i++ ) {
				var thens = $scope.convertOperatorToActions(operators[i]);
				for (var j=0; j<thens.length; j++){
					$scope.allThens.push( {"operator":operators[i],"action":thens[j] } );
					$scope.allThensActions.push(thens[j]);
				}				
			}
		}
	}


	// convertir les règles
	$scope.convertRuleToRow = function($rule) {		
		// string (expression de type "ON M2 == 4 && M5 > 6 DO P1:45 P4:32")
		var theRow = {
			"ruleId":$rule.id,
			"ruleName":$rule.title,
			"active":$rule.enable,
			"rowOns":[],
			"rowThens":[]
		};
		// convertir le content en objet Js

		// mettre les mots de l'expression dans un tableau
		var mots = $rule.contents.split(' ');
		if (mots[0]!="ON") {
			return theRow; // renvoie une règle de content vide
		}

		var i = 1;

		// traiter les ON
		while(mots[i] != "DO") {
			if (mots[i] == "&&") {
				i+=1; // ATTENTION il n'ya pas de "ou" logique pour l'utilisateur, seulement des "et"
			}
			if (mots[i].substring(0,1)!="M") {
				return theRow;
			}
			var rowOn = {
				// récupérer la mesure
				"rowOnInstance":$scope.getObjectById( $scope.allOns , parseInt(mots[i].substring(1,mots[i].length))),
				// récupérer la comparaison
				"rowOnInfSup":$scope.infSup_ValueToObject(mots[i+1]),
				// récupérer la valeur
				"rowOnValue":parseFloat(mots[i+2])
			};
			if ((!rowOn.rowOnInstance) || (!rowOn.rowOnInfSup)) {
				return theRow;
			}
			theRow.rowOns.push(rowOn);
			i+=3;
		}

		i+=1;

		// traiter les DO
		while (i<mots.length) {
			if (mots[i].substring(0,1)!="P") {
				return theRow;
			}

			var theDO = mots[i].split(':');

			if (theDO.length!=2) {
				return theRow;
			}

			var rowThen = {
				// récupérer l'actionneur
				"rowThenInstance":$scope.getObjectByRef( $scope.operators , parseInt(theDO[0].substring(1,theDO[0].length)) ),
				// récupérer l'action
				"rowThenValue":$scope.getObjectById( $scope.allThensActions , parseInt(theDO[1]) )
			};

			theRow.rowThens.push(rowThen);
			i+=1;
		}

		return theRow;
	}


	// convertir les rows en règles java
	$scope.convertRowToRule = function($row) {
		var theRule = {
			"id":$row.ruleId,
			"ref":$row.ruleName,
			"title":$row.ruleName,
			"contents":"ON ",
			"enable":$row.active
		};

		// écrire contents

		// écrire les ON
		if ($row.rowOns.length > 0) {
			theRule.contents = theRule.contents+"M"+$row.rowOns[0].rowOnInstance.id+" "+$row.rowOns[0].rowOnInfSup.value+" "+$row.rowOns[0].rowOnValue;
			var i = 1;
			while(i < $row.rowOns.length) {
				theRule.contents = theRule.contents+" && M"+$row.rowOns[i].rowOnInstance.id+" "+$row.rowOns[i].rowOnInfSup.value+" "+ $row.rowOns[i].rowOnValue;
				i+=1;
			}
		}

		// écrire les DO
		theRule.contents = theRule.contents+ " DO";
		if ($row.rowThens.length > 0) {
			var rowThen = $row.rowThens[0];
			for (var i=0; i<$row.rowThens.length;i++) {
				rowThen = $row.rowThens[i];
				theRule.contents = theRule.contents+" P"+rowThen.rowThenValue.operator.id+":"+rowThen.rowThenValue.id;
			}
		}

		return theRule;
	}


	


	// fonction qui récupère TOUTES les rules dans la BD et les convertit en rows
	$scope.getRows = function() {
		$scope.rows = [];

		var rules = $scope.rules;
		if (rules) {
			for (var i=0 ; i<rules.length; i++) {
				var row = $scope.convertRuleToRow(rules[i]);
				$scope.rows.push(row);
			}
		}
	}




	// AFFICHAGE



		// affiche les infos de la mesure (pour la rowOnInstance)
	$scope.afficheOnInfos = function($theOn) {
		res = [];
		res.push("Capteur : "+$theOn.rowOnInstance.sensor.title);
		res.push("Id : "+$theOn.rowOnInstance.sensor.id);
		res.push("Piece : "+$theOn.rowOnInstance.sensor.piece.title);
		res.push("Mesure : "+$theOn.rowOnInstance.string+" ("+$theOn.rowOnInstance.id+")");		
		res.push("Unite : "+$theOn.rowOnInstance.unit);
		return res;
	}

	// affiche les infos de l'actionneur (pour la rowThenInstance)
	$scope.afficheThenInfos = function($theThen) {
		res = [];
		res.push("Actionneur : "+$theThen.rowThenInstance.title);
		res.push("Id : "+$theThen.rowThenInstance.ref);
		res.push("Piece : "+$theThen.rowThenInstance.room.title);
		res.push("Action : "+$theThen.rowThenValue.string);
		return res;
	}

	$scope.switchActive = function($row) {
			if ($row.active) {
				$row.active = false;
			}
			else {
				$row.active = true;
			}
		}

	// setter du "==", ">" ou "<" pour un On donné pour une ligne donnée
	$scope.selectOnInfSup = function($rowOn,$infSup) {
		$rowOn.rowOnInfSup = $infSup;
	}

	$scope.selectThenValue = function($rowThen,$value) {
		$rowThen.rowThenValue = $value;
	}


	// renvoie la liste des On (sous-liste des On-Do) dans une pièce
	$scope.theOns = function($piece)
	{
		var res = [];
		if ($scope.allOns) {
			for(i=0;i<$scope.allOns.length;i++)
			{
				var current = $scope.allOns[i];
				if (current.sensor.piece.id == $piece.id)
				{
					res.push(current);
				}
			}
		}
		return res;
	}

	// renvoie la liste des Do (sous liste des On-Do) dans une pièce
	$scope.theThens = function($piece) {
		var res = [];
		if ($scope.allThens) {
			for(i=0;i<$scope.allThens.length;i++) {
				var current = $scope.allThens[i];
				if (current.operator.room.id == $piece.id) {
					res.push(current);
				}
			}
		}
		return res;
	}

	// ajoute une règle vide
	$scope.addEmptyRule = function() {
		$scope.rows.push({"ruleId":-1, "ruleRef":"","ruleName":"","active":false,"rowOns":[],"rowThens":[]});
	}

	// setter - désactive toutes les règles
	$scope.deactivateAllRules = function() {
		for(i=0;i<$scope.rows.length;i++) {
			var row = $scope.rows[i];
			row.active = false;
		}
	}

	$scope.addOn = function($row,$current) {
		$row.rowOns.push({"rowOnInstance":$current,"rowOnInfSup":$scope.theInfSup[0],"rowOnValue":0});
	}

	$scope.addThen = function($row,$current) {
		$row.rowThens.push({"rowThenInstance":$current.operator,"rowThenValue":$current.action});
	}

    $scope.removedRows=[];
	$scope.deleteRow = function($row) {
        $scope.removedRows.push($row.ruleId);
		$scope.removeByValue($scope.rows,$row);
	}

	$scope.deleteAllRules = function() {
		angular.forEach($scope.rows, function(row) {
            $scope.removedRows.push(row.ruleId);
        });
		$scope.rows=[];
	}

	$scope.deleteOn = function($row,$rowOn) {
		$scope.removeByValue($row.rowOns,$rowOn);
	}

	$scope.deleteThen = function($row,$rowThen) {
		$scope.removeByValue($row.rowThens,$rowThen);
	}

	// tout lancer :
	$scope.launch = function () {
		$scope.stopProp();
		$scope.fetch();
	}
	
	$scope.fetch();

	$scope.save = function() {
		angular.forEach($scope.rows, function(row) {
			$http({
				method: 'PUT',
				url: "/rule" + (row.ruleId > -1 ? "/" + row.ruleId : ''),
				data:$.param($scope.convertRowToRule(row)),
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
					$scope.fetch();				
			  }).
		      error(function(data) {
		    	  $scope.addAlert('Server communication failed !!','error');
		      });
        });
		
		angular.forEach($scope.removedRows, function(rowId) {
			$http({
				method: 'DELETE',
				url: "/rule/" + rowId,
				data: '',
				headers: {'Content-Type': 'application/x-www-form-urlencoded'}}).success(function(data) {
					$scope.fetch();				
			  }).
		      error(function(data) {
		    	  $scope.addAlert('Server communication failed !!','error');
		      });
        });
        $scope.removedRows=[];
	};
	



}

