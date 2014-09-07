Intelligent Home Project
========================

Project background
=================
I did this project with a team of 6 people during my master's degree. 
I worked on this project as a developer and a manager. I mostly worked on the back-end of the application.
In two months, we were able to provide a functionnal prototype with a lot of features which are describing below.

Quick explanations
==================
The project was about creating an intelligent home. To explain the main concept, let's say we have a house with multiple sensors
which could capture temperature, light presence or a switch. We wanted to take control of these sensors and do actions related to their values, for instance,
if the temperature is equal to 25 degrees, the heater will be switched off. In our case, the set of actions was limited to a plug which can be turned on or off.

For this project, we got sensor devices such as temperature sensor, light sensor and a switch. We got also a plug as an operator. 
The sensors was sending frames to our server that could send frames to the plug in order to switch it on or off. 
The real challenge was to know when a condition is true according to the sensor values to send an action to an operator. We will discussed about how we did to manage
this problem.

What can you do ?
-----------------
Now that you know a bit more about the project, how can we interact with the system as a user  ? 
We chose to implement a web interface for both desktop and smartphone device. 
In this interface, you can set up your house configuration, like choosing your avatar picture, the type of your house and the name of your rooms.

Main features
-------------

* __Detecting new sensors__  

    The sensors are detected automaticaly when they send frames to the server. 
The server tries to find a frame in its predifined set of sensors which matches the sending frames and if this is the case, 
the sensor is added in the database. The user has the possibility to enable or disable the sensors.

* __Following the sensor historics__  

    In the sensors page, we can see the current values of each enable sensors as well as its previous values.

* __Adding rules to the sensors and operators__  

    The most challenging part of the project was to trigger some actions related to the sensor values. 
We used a DSL framwork to generate triggers in the database which are triggered according to the current sensor values in the database.
Using a DSL framework allowed the user to definined conditions with many possibilities. 
For instance, we could state that when the temperature in room 1 is under 10 and the temperature in room 2 is under 0 then we turn the plug on 
(which can be plugged into a heater). __The possibilities are limitless here__.    

Technologies used
-----------------
* Database: SQLite
* Back-end: Java, Servlet, Antlr.
* Front-end: HTML, Bootstrap and AngularJS.

Play with the project
=====================
As you might not have the sensors we used in this project, it will be more tricky to play with it. 
Nevertheless, you can run our simulator in the manual mode which sends some frames to the server. 
You need to launch the main class of the JavaServer project before, the web server runs on localhost on port 8081 by default.
