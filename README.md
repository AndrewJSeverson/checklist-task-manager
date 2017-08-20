Task Manager - REST API Service
====================
The Checklist Task Manager Spring-Boot application is meant to interface with a cloud foundry Postgres service to handle all interactions between the database and the users/system. 


## Enviorment Setup

1. Download and run Redis locally on your computer [https://redis.io/download]
2. Download and run Postgres locally on your computer: [https://www.postgresql.org/download/]
3. Set the following environment variables on your computer 
	```bash
    export spring_datasource_password=__*your_local_database_password_here*__
    export spring_datasource_url=jdbc:postgresql://localhost:5432/__*your_local_database_name_here*__
    export spring_datasource_username=__*your_local_database_username_here*__
    export spring_jpa_hibernate_ddl-auto=update
    export sendgrid_username=__*your_sendgrid_username*__
    export sendgrid_password=__*your_sendgrid_password*__
    ```
4. (OPTIONAL) Swap our Spring Security for your needs or swap out different auth0 credentials 
⋅⋅* auto0 information stored at /src/main/resources/

## Install and Run

1. git clone this repository into your server
	```bash
	git clone https://github.com/AndrewJSeverson/checklist-task-manager.git
	```
1. mvn clean install from the root directory
	```bash
	mvn clean install
	```
1. mvn spring-boot:run from the root directory to run the application
	```bash
	mvn spring-boot:run
	```
	
## Test Locally
1. Once the application is running locally, navigate to [http://localhost:9000/swagger-ui.html] in your browser, or download and import the postman file at the root directory
1. You will first want to obtain your __BEARER__ token
1. Create a user and store the ID, it will be needed for some of the APIs






	
	
	
