ssa-carpark (In Progress)
========================

What is it?
-----------
This is the car park space allocation project for my current employer. I have started this as a side project. The goal of this project is to create a web-app which will give an interface to do the following:-

1) help the users (admin role) to create new employees and new car parks
2) allocate the car parks to the employees
3) Send notifications to the employees about their allocated car parks
4) Allow employees with allocated car parks to reject the parking , notify the system and allow the system to re-allocate the car parks

Technology stack:-
1) Java, Spring Core, Spring MVC, maybe Spring Boot
2) Maven 
3) Hibernate with JPA
4) For UI Smart GWT which is based on Google WebToolkit


This is your project! It is a sample, deployable Maven 3 project to help you get your foot in the door developing with Spring on JBoss Enterprise Application Platform 6 or JBoss AS 7.1. 

This project is setup to allow you to create a compliant Spring 3.1 application using Spring MVC, JPA 2.0 and Bean Validation 1.0. It includes a persistence unit and some sample persistence and transaction code to introduce you to database access in enterprise Java. 

The example uses the `java:jboss/datasources/SpringQuickstartDS` database, configured and deployed by the application.

System requirements
-------------------

All you need to build this project is Java 6.0 (Java SDK 1.6) or better, Maven 3.0 or better.

The application this project produces is designed to be run on JBoss Enterprise Application Platform 6 or JBoss AS 7.1. 

Start JBoss Enterprise Application Platform 6 or JBoss AS 7.1
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat


Build and Deploy the Application
-------------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package jboss-as:deploy

4. This will deploy `target/ssa-carpark.war` to the running instance of the server.


Access the application 
---------------------
 
The application will be running at the following URL: <http://localhost:8080/ssa-carpark/>.


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn jboss-as:undeploy


Debug the Application
------------------------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc

