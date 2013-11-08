google.load('visualization', '1.0', {'packages':['corechart','annotatedtimeline']});
google.setOnLoadCallback(function() {
	  angular.bootstrap(document.body, ['ghome']);
	});

var app = angular.module('ghome', ['ui','ui.bootstrap']).
  config(['$routeProvider', function($routeProvider) {
  $routeProvider.
      when('/', {templateUrl: 'partials/home.html',   controller: HomeCtrl}).
      when('/setup', {templateUrl: 'partials/setup/home.html',   controller: SetupCtrl}).
      when('/setup/personalInfo', {templateUrl: 'partials/setup/personalInfo.html',   controller: SetupPersonalInfoCtrl}).
      when('/setup/avatar', {templateUrl: 'partials/setup/avatar.html',   controller: SetupAvatarCtrl}).
      when('/setup/house', {templateUrl: 'partials/setup/house.html',   controller: SetupHouseCtrl}).
      when('/setup/rooms', {templateUrl: 'partials/setup/rooms.html',   controller: SetupRoomsCtrl}).
      when('/login', {templateUrl: 'partials/login.html',   controller: LoginCtrl}).
      when('/house', {templateUrl: 'partials/house.html', controller: HouseCtrl}).
      when('/room/:roomId', {templateUrl: 'partials/room.html',   controller: RoomCtrl}).
      when('/operators', {templateUrl: 'partials/operator.html',   controller: OperatorCtrl}).
      when('/actions', {templateUrl: 'partials/action.html',   controller: ActionCtrl}).
      when('/administration', {templateUrl: 'partials/administration.html', controller: AdministrationCtrl}).
      when('/administration/sensors', {templateUrl: 'partials/adminSensors.html',   controller: AdminSensorsCtrl}).
      when('/administration/rules', {templateUrl: 'partials/adminRules.html',   controller: AdminRulesCtrl}).
      otherwise({redirectTo: '/'});
}]);

app.directive('ghBargraph', function() {
	return function(scope, element, attrs) { 
		var data;

		scope.$watch(attrs.ghBargraph, function(value) {

			var e =element[0];
			
			 var data = new google.visualization.DataTable();
			 
			 data.addColumn('datetime', 'Date');
			 data.addColumn('number', value.nom);
			 for(var i= 0;i<value.history.length;i++){

				 data.addRow([new Date(value.history[i].modifiedAt),value.history[i].value])
			 }


             var options = {
               title: ''
             };

             var chart = new google.visualization.LineChart(e);
             chart.draw(data, options);
		});
	    
	}
	});

app.directive('ghBargraph2', function() {
	return function(scope, element, attrs) { 
		var data;
		scope.$watch(attrs.ghBargraph2, function(value) {
			var e =element[0];
			
			 var data = new google.visualization.DataTable();
			 
			 data.addColumn('datetime', 'Date');
			 data.addColumn('string', value.title);
			 for(var i= 0;i<value.history.length;i++){

				 data.addRow([new Date(value.history[i]['executedAt']),value.history[i]['title']])
			 }

             var options = {
               title: ''
             };

             var chart = new google.visualization.AnnotatedTimeLine(e);
             chart.draw(data, options);
		});
	    
	}
	});

