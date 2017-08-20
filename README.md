Task Manager - REST API Service
====================
The projects Spring-Boot application is meant to interface with a cloud foundry Postgres service to handle all interactions between the database and the users/system. 


##Enviorment Setup

* Set the following environment variables on your computer 
    * export spring_datasource_password=*your localdatabase password here*
    * export spring_datasource_url=jdbc:postgresql://localhost:5432/*your local database name here*
    * export spring_datasource_username=*your local database username here*
    * export spring_jpa_hibernate_ddl-auto=update
    * export sendgrid_username=gzL8TBKeBc
    * export sendgrid_password=culOoL5InvmA5190
* Download and run Redis locally on your computer

##Install and Run

1. git clone this repository into your server
1. mvn clean install from the root directory
1. mvn spring-boot:run from the root directory to run the application






	
	
	
