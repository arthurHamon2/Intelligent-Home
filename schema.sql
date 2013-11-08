/*==============================================================*/
/* Nom de SGBD :  ORACLE Version 11g                            */
/* Date de création :  14/01/2013 09:22:10                      */
/*==============================================================*/


/*==============================================================*/
/* Table : T_OPERATOR                                           */
/*==============================================================*/
create table T_OPERATOR 
(            
   OPERATORREF          INTEGER             not null,
   IDROOM               INTEGER,
   IDOPERATORTYPE	    INTEGER,
   TITLE				CLOB,
   DESCRIPTION			CLOB,
   constraint PK_T_OPERATOR primary key (OPERATORREF),
   constraint FK_OPERATOR_TYPE foreign key (IDOPERATORTYPE)
         references T_OPERATOR_TYPE (IDTYPE),
   constraint FK_T_operator_T_ROOM foreign key (IDROOM)
         references T_ROOM (IDROOM)
);

/*==============================================================*/
/* Table : T_OPERATOR_TYPE                                           */
/*==============================================================*/
create table T_OPERATOR_TYPE 
(            
	IDTYPE				INTEGER				not null,
    CLASSNAME           CLOB,
	TITLETYPE			CLOB,
	DESCRIPTIONTYPE		CLOB,
    IMAGE		        CLOB,
	constraint PK_T_OPERATOR_TYPE primary key (IDTYPE)
);

/*==============================================================*/
/* Table : T_ACTION                                           */
/*==============================================================*/
create table T_ACTION 
(            
	IDACTION			INTEGER				not null,
	OPERATORTYPE		INTEGER,
	FRAME				CLOB,
	TITLEACTION			CLOB,
	DESCRIPTIONACTION	CLOB,
    IMAGE				CLOB,
	constraint PK_T_ACTION primary key (IDACTION),
    constraint FK_OPERATOR_TYPE_ACTION foreign key (OPERATORTYPE)
         references T_OPERATOR_TYPE (IDTYPE)
);

/*==============================================================*/
/* Table : T_ACTIONS_TODO                                          */
/*==============================================================*/
create table T_ACTIONS_TODO 
(            
	IDACTION			INTEGER				not null,
	OPERATORREF			INTEGER				not null,
	ADDEDAT				DATE,
    
	constraint PK_T_ACTIONS_TODO primary key (IDACTION,OPERATORREF),
	constraint FK_T_ACTION_TODO foreign key (IDACTION)
         references T_ACTION (IDTYPE),
    constraint FK_T_ACTION_TODO2 foreign key (OPERATORREF)
         references T_OPERATOR (OPERATORREF)
);

/*==============================================================*/
/* Table : TH_ACTION                                        */
/*==============================================================*/
create table TH_ACTION 
(
   IDTEMPACTION         INTEGER             not null,
   IDACTION				INTEGER				not null,
   OPERATORREF			INTEGER				not null,
   ADDEDAT           	DATE,
   EXECUTEDAT           DATE,
   constraint PK_TH_ACTION primary key (IDTEMPACTION)
);


/*==============================================================*/
/* Table : T_SENSOR_TYPE                                        */
/*==============================================================*/
create table T_SENSOR_TYPE 
(
   IDTYPE               INTEGER              not null,
   EED                  CLOB,
   SENSORCLASS          CLOB,
   FRAMECLASS           CLOB,
   TITLETYPE            CLOB,
   IMAGE				CLOB,
   constraint PK_T_SENSOR_TYPE primary key (IDTYPE)
);

/*==============================================================*/
/* Table : T_SENSOR                                             */
/*==============================================================*/
create table T_SENSOR 
(
   SENSORREF            INTEGER              not null,
   IDTYPE               INTEGER 			 not null,
   IDROOM               INTEGER				 not null,
   SENSORTITLE          CLOB,
   INSTALLED            SMALLINT,
   constraint PK_T_SENSOR primary key (SENSORREF),
   constraint FK_T_SENSOR_RELATION__T_SENSOR foreign key (IDTYPE)
         references T_SENSOR_TYPE (IDTYPE),
   constraint FK_T_SENSOR_T_ROOM foreign key (IDROOM)
         references T_ROOM (IDROOM)
);

/*==============================================================*/
/* Table : T_VIRTUAL_FRAME                                      */
/*==============================================================*/
create table T_VIRTUAL_FRAME
(
   IDFRAME              INTEGER              not null,
   IDSENSOR				INTEGER				 not null,
   FRAMETYPE            CLOB,
   constraint PK_T_VIRTUAL_FRAME primary key (IDFRAME),
   constraint FK_T_VIRTUAL_FRAME foreign key (IDSENSOR)
         references T_SENSOR (SENSORREF)
);

/*==============================================================*/
/* Table : TH_SENSOR                                        */
/*==============================================================*/
create table TH_SENSOR 
(
   IDTEMP               INTEGER              not null,
   SENSORREF            INTEGER              not null,
   IDMEASURE            INTEGER              not null,
   IDMEASURETYPE        INTEGER              not null,
   MODIFIEDAT           DATE,
   VALUE                NUMBER,
   constraint PK_TH_SENSOR primary key (IDTEMP)
);

/*==============================================================*/
/* Table : T_MEASURE_TYPE                                       */
/*==============================================================*/
create table T_MEASURE_TYPE 
(
   IDMEASURETYPE        INTEGER              not null,
   TITLEMEASURE         CLOB,
   UNITY                CLOB,
   IMAGE                CLOB,
   constraint PK_T_MEASURE_TYPE primary key (IDMEASURETYPE)
);

/*==============================================================*/
/* Table : T_MEASURE                                            */
/*==============================================================*/
create table T_MEASURE 
(
   IDMEASURE            INTEGER              not null,
   SENSORREF            INTEGER              not null,
   IDMEASURETYPE        INTEGER              not null,
   VALUE                NUMBER,
   constraint PK_T_MEASURE primary key (IDMEASURE),
   constraint FK_T_MEASUR_RELATION__T_SENSOR foreign key (SENSORREF)
         references T_SENSOR (SENSORREF),
   constraint FK_T_MEASUR_RELATION__T_MEASUR foreign key (IDMEASURETYPE)
         references T_MEASURE_TYPE (IDMEASURETYPE)
);

/*==============================================================*/
/* Table : T_RULE                                               */
/*==============================================================*/
create table T_RULE 
(
   IDRULE               INTEGER              not null,
   REF                  CLOB,
   TITLE                CLOB,
   CONTENTS             CLOB,
   ENABLE				SMALLINT,
   constraint PK_T_RULE primary key (IDRULE)
);

/*==============================================================*/
/* Table : T_USER                                               */
/*==============================================================*/
create table T_USER 
(
   IDUSER               INTEGER              not null,
   HOUSE				INTEGER				 ,
   LOGIN                CLOB,
   PWD                  CLOB,
   MAIL					CLOB,
   IMAGE				CLOB,
   constraint PK_T_USER primary key (IDUSER),
   constraint FK_T_ROOM_REFERENCE foreign key (HOUSE)
         references T_HOUSE (IDHOUSE)
);

/*==============================================================*/
/* Table : T_ROOM                                               */
/*==============================================================*/
create table T_ROOM 
(
   IDROOM               INTEGER              not null,
   IDHOUSE              INTEGER               not null,
   ROOMTITLE            CLOB,
   Xleft				INTEGER,
   Yleft				INTEGER,
   Xright				INTEGER,
   Yright				INTEGER,
   constraint PK_T_ROOM primary key (IDROOM),
   constraint FK_T_ROOM_REFERENCE_T_HOUSE foreign key (IDHOUSE)
         references T_HOUSE (IDHOUSE)
);

/*==============================================================*/
/* Table : T_HOUSE                                              */
/*==============================================================*/
create table T_HOUSE 
(
   IDHOUSE              INTEGER              not null,
   HOUSENAME            CLOB,
   IMAGE				CLOB,
   constraint PK_T_HOUSE primary key (IDHOUSE)
);

/* -----------------------TRIGGER SENSOR HISTORIC-----------------------*/
CREATE TRIGGER [UPDATE_HISTORIC_SENSOR]
UPDATE OF [VALUE] ON [T_MEASURE]
FOR EACH ROW
BEGIN

INSERT INTO TH_SENSOR(IDTEMP,SENSORREF,IDMEASURE, IDMEASURETYPE,MODIFIEDAT,VALUE)
VALUES( NULL, NEW.SENSORREF, NEW.IDMEASURE,
 NEW.IDMEASURETYPE, datetime('now'), NEW.VALUE );
 
END;
/* -----------------------/TRIGGER SENSOR HISTORIC------------------------*/

/* -----------------------TRIGGER ACTION HISTORIC------------------------*/
CREATE TRIGGER [DELETE_HISTORIC_ACTION]
DELETE ON [T_ACTIONS_TODO]
FOR EACH ROW
BEGIN

INSERT INTO TH_ACTION(IDTEMPACTION,IDACTION,OPERATORREF,ADDEDAT,EXECUTEDAT)
VALUES( NULL, OLD.IDACTION,OLD.OPERATORREF, OLD.ADDEDAT, datetime('now'));
 
END;
/* -----------------------/TRIGGER ACTION HISTORIC------------------------*/


/* **************************************************************** DATA *********************************************************************************/
/* **************************************************************** SENSOR TYPE  *****************************************************************/
INSERT INTO T_SENSOR_TYPE(IDTYPE, EED, SENSORCLASS, FRAMECLASS, TITLETYPE, IMAGE) 
VALUES(1, '7-8-1', 'server.models.OccupancySensor', 'server.Sensors.Frame.FrameLightTemperatureOccupancy', 'Capteur de présence','capt_presence.png');
INSERT INTO T_SENSOR_TYPE(IDTYPE, EED, SENSORCLASS, FRAMECLASS, TITLETYPE, IMAGE) 
VALUES(2, '5-3-1', 'server.models.Rocker2Sensor', 'server.Sensors.Frame.RockerFrame', 'Interrupteur deux boutons','capt_interr.png');
INSERT INTO T_SENSOR_TYPE(IDTYPE, EED, SENSORCLASS, FRAMECLASS, TITLETYPE, IMAGE) 
VALUES(3, '7-2-5', 'server.models.TemperatureSensor', 'server.Sensors.Frame.TemperatureFrame_7_2_5', 'Temperature entre 0° et 40°','capt_temperature.png');
INSERT INTO T_SENSOR_TYPE(IDTYPE, EED, SENSORCLASS, FRAMECLASS, TITLETYPE, IMAGE) 
VALUES(4, '6-0-1', 'server.models.SwitchWindowSensor', 'server.Sensors.Frame.SwitchFrame', 'Capteur de l état de la fenêtre','capt_contact.png');
INSERT INTO T_SENSOR_TYPE(IDTYPE, EED, SENSORCLASS, FRAMECLASS, TITLETYPE, IMAGE) 
VALUES(5, '0-0-0', 'server.models.UnknowSensor', 'server.Sensors.Frame.UnknowFrame', 'Unknow Sensor','');
INSERT INTO T_SENSOR_TYPE(IDTYPE, EED, SENSORCLASS, FRAMECLASS, TITLETYPE, IMAGE) 
VALUES(6, '9-9-9', 'server.models.VirtualMeteoSensor', '', 'Virtual Meteo Sensor','meteo_sensor.jpg');
INSERT INTO T_SENSOR_TYPE(IDTYPE, EED, SENSORCLASS, FRAMECLASS, TITLETYPE, IMAGE) 
VALUES(7, '9-9-9', 'server.models.VirtualCalendarSensor', '', 'Virtual Calendar Sensor','calendar_sensor.png');

/* ************************************************************** /SENSORTYPE *****************************************************************/

/* **************************************************************** SENSOR *****************************************************************/
INSERT INTO T_SENSOR (SENSORREF, IDTYPE, IDROOM, SENSORTITLE, INSTALLED) 
VALUES(2214883, 2, 1, "ROCKER2SENSOR",0);
INSERT INTO T_SENSOR (SENSORREF, IDTYPE, IDROOM, SENSORTITLE, INSTALLED) 
VALUES(112022, 4, 1, "ROCKER2SENSOR",0);
INSERT INTO T_SENSOR (SENSORREF, IDTYPE, IDROOM, SENSORTITLE, INSTALLED) 
VALUES(1, 7, 1, "Calendar Sensor",0);
INSERT INTO T_SENSOR (SENSORREF, IDTYPE, IDROOM, SENSORTITLE, INSTALLED) 
VALUES(2, 6, 1, "Température Extérieure Lyon",0);


/* **************************************************************  /SENSOR *****************************************************************/

/* **************************************************************** /MEASURE TYPE *****************************************************************/
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(1, "Luminosite", "Lux");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(2, "Temperature", "°C");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(3, "Pression", "hPa");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(4, "EtatBouton", "-");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(5, "Presence", "-");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(6, "Fenetre", "-");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(7, "Voltage", "V");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(8, "Heure_Reveil", "Time");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(9, "Heure_Actuelle", "Time");
INSERT INTO T_MEASURE_TYPE(IDMEASURETYPE, TITLEMEASURE, UNITY) VALUES(10, "Vent", "Km/H");
/* **************************************************************** /MEASURE TYPE *****************************************************************/

/* ****************************************************************  OPERATOR TYPE *****************************************************************/
INSERT INTO T_OPERATOR_TYPE(IDTYPE,CLASSNAME, TITLETYPE, DESCRIPTIONTYPE, IMAGE) 
VALUES(1,"server.models.PlugOperator", "Prise", " ","prise_on.png");
INSERT INTO T_OPERATOR_TYPE(IDTYPE,CLASSNAME, TITLETYPE, DESCRIPTIONTYPE, IMAGE) 
VALUES(2,"server.models.MailSender", "Mail actionneur", "Permet d envoyer des mails d alertes et d informations","mail.jpg");
/* **************************************************************** /OPERATOR TYPE *****************************************************************/

INSERT INTO T_VIRTUAL_FRAME(IDFRAME,IDSENSOR,FRAMETYPE)
VALUES(1,1,"server.models.VirtualCalendarFrame");
INSERT INTO T_VIRTUAL_FRAME(IDFRAME,IDSENSOR,FRAMETYPE)
VALUES(2,2,"server.models.VirtualMeteoFrame");

/* **************************************************************** OPERATOR  *****************************************************************/
INSERT INTO T_OPERATOR(OPERATORREF,IDROOM, IDOPERATORTYPE, TITLE, DESCRIPTION) 
VALUES(4288617990,1, 1, "Prise du salon", " ");
INSERT INTO T_OPERATOR(OPERATORREF,IDROOM, IDOPERATORTYPE, TITLE, DESCRIPTION) 
VALUES(1,1, 2, "Envoyeur d alerte", " ");
/* **************************************************************** /OPERATOR *****************************************************************/

/* **************************************************************** ACTION *****************************************************************/
INSERT INTO T_ACTION(IDACTION, OPERATORTYPE, FRAME, TITLEACTION, DESCRIPTIONACTION) 
VALUES(1, 1, '50000000', "S_ON", "Allumer");
INSERT INTO T_ACTION(IDACTION, OPERATORTYPE, FRAME, TITLEACTION, DESCRIPTIONACTION) 
VALUES(2, 1, '70000000', "S_OFF", "Eteindre");
INSERT INTO T_ACTION(IDACTION, OPERATORTYPE, FRAME, TITLEACTION, DESCRIPTIONACTION) 
VALUES(3, 2,'Frame' , "Attention, ceci est une alerte", '<h1>Attention, une alerte a été déclenchée</h1>');
INSERT INTO T_ACTION(IDACTION, OPERATORTYPE, FRAME, TITLEACTION, DESCRIPTIONACTION) 
VALUES(4, 2,'Frame' , "Note d informations", '<h1>Note d informations déclenchée</h1>');
/* **************************************************************** /ACTION *****************************************************************/

/* **************************************************************** USER *****************************************************************/
INSERT INTO T_USER (IDUSER,HOUSE,LOGIN,PWD,MAIL,IMAGE) VALUES(1,2, "admin", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4","ghomepowerhouse@gmail.com","amal.jpg");

/* **************************************************************** /USER *****************************************************************/

/* **************************************************************** HOUSE *****************************************************************/
INSERT INTO T_HOUSE(IDHOUSE,HOUSENAME,IMAGE) VALUES(2,"T1","t1.png");
INSERT INTO T_HOUSE(IDHOUSE,HOUSENAME,IMAGE) VALUES(3,"T2","t2.png");
INSERT INTO T_HOUSE(IDHOUSE,HOUSENAME,IMAGE) VALUES(4,"T3","t3.png");
INSERT INTO T_HOUSE(IDHOUSE,HOUSENAME,IMAGE) VALUES(5,"T4","t4.png");
INSERT INTO T_HOUSE(IDHOUSE,HOUSENAME,IMAGE) VALUES(6,"T5","t5.png");
/* **************************************************************** /HOUSE *****************************************************************/

/* **************************************************************** ROOM *****************************************************************/
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(2,2,"Pièce verte",40,150,135,265);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(3,2,"Pièce bleue",140,150,355,265);

INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(4,3,"Pièce azur",55,115,255,217);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(5,3,"Pièce mauve",262,115,335,217);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(6,3,"Pièce beige",55,222,335,340);

INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(7,4,"Pièce violette",35,110,260,225);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(8,4,"Pièce bleue",267,110,362,225);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(9,4,"Pièce beige",35,230,150,340);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(10,4,"Pièce orange",155,230,362,340);

INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(11,5,"Pièce rose",19,125,126,215);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(12,5,"Pièce verte",130,125,300,215);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(13,5,"Pièce bleue",305,125,380,215);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(14,5,"Pièce grise",19,220,257,327);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(15,5,"Pièce orange",262,220,380,327);

INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(16,6,"Pièce rose",18,116,113,202);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(17,6,"Pièce azur",118,116,210,202);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(18,6,"Pièce jaune",215,116,305,202);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(19,6,"Pièce violette",310,116,380,202);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(20,6,"Pièce rouge",18,207,165,282);
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE,Xleft,Yleft,Xright,Yright) VALUES(21,6,"Pièce verte",170,207,380,282);
/* **************************************************************** /ROOM *****************************************************************/

/* ************************************** JUNIT TEST + INITIALISATION (EMPTY HOUSE AND ROOM) **********************************************/
INSERT INTO T_OPERATOR_TYPE(IDTYPE, TITLETYPE, DESCRIPTIONTYPE, CLASSNAME) VALUES(99, "Test", " ","server.models.PlugOperator");
INSERT INTO T_HOUSE(IDHOUSE,HOUSENAME,IMAGE) VALUES(1,"empty house","empty");
INSERT INTO T_ROOM(IDROOM,IDHOUSE,ROOMTITLE) VALUES(1,1,"empty room");
/* ************************************* /JUNIT TEST + INITIALISATION (EMPTY HOUSE AND ROOM) ***********************************************/

/* **************************************************************** /DATA

 *********************************************************************************/
INSERT INTO T_MEASURE(IDMEASURE ,SENSORREF,IDMEASURETYPE,VALUE) VALUES (99999998,1,8,99999999999999999);
INSERT INTO T_MEASURE(IDMEASURE ,SENSORREF,IDMEASURETYPE,VALUE) VALUES (99999999,1,9,99999999999999999)




