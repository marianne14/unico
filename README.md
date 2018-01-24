================================================================

Assumptions :
The following has been setup before deployment.

-----------------------------

Wildfly Version : 
wildfly-11.0.0.Final

-----------------------------

Wildfly JMS Queue Configuration : 
queue-address = paramsQueue
entries = queue/params, java:jboss/exported/jms/queue/params

-----------------------------

Wildfly DataSource Configuration : 
java:/UnicoDS

-----------------------------

Database (MySQL) Connector :
mysql-connector-java-5.1.45

Database Schema : unicodb

CREATE DATABASE unicodb;

CREATE TABLE param ( 
ID smallint unsigned not null auto_increment, 
PARAM int not null, 
PRIMARY KEY (ID));

CREATE TABLE gcd ( 
ID smallint unsigned not null auto_increment, 
GCD int not null, 
PRIMARY KEY (ID));

CREATE TABLE user ( 
ID smallint unsigned not null auto_increment, 
USER varchar(50) not null, 
PASSWORD varchar(50) not null, 
PRIMARY KEY (ID));

INSERT INTO USER (USER , PASSWORD )
values("user1", "user1");

-----------------------------
================================================================
REST APIs :
http://localhost:8080/UnicoWeb/rest/service/push/{param1}/{param2}

http://localhost:8080/UnicoWeb/rest/service/list

Web Service WSDL :
http://localhost:8080/UnicoWeb/UnicoSOAPWebService?wsdl

Authentication :
Basic (username/password);

