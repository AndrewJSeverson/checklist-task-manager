Task Manager - REST API Service
====================
The Checklist Task Manager Spring-Boot application is meant to interface with a cloud foundry Postgres service to handle all interactions between the database and the users/system. 


##Enviorment Setup

* Download and run Redis locally on your computer [https://redis.io/download] (https://redis.io/download)
* Download and run Postgres locally on your computer: [https://www.postgresql.org/download/] (https://www.postgresql.org/download/)
* Set the following environment variables on your computer 
    * export spring_datasource_password=__*your_local_database_password_here*__
    * export spring_datasource_url=jdbc:postgresql://localhost:5432/__*your_local_database_name_here*__
    * export spring_datasource_username=__*your_local_database_username_here*__
    * export spring_jpa_hibernate_ddl-auto=update
    * export sendgrid_username=__*your_sendgrid_username*__
    * export sendgrid_password=__*your_sendgrid_password*__

##Install and Run

1. git clone this repository into your server
1. mvn clean install from the root directory
1. mvn spring-boot:run from the root directory to run the application






	
	
	
