# Poseidon - Capital Solutions

## Description

The application is a financial aggregator that integrates various market tools to process financial transactions.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

### Prerequisites

What things you need to install the software and how to install them :

- Java 21
- Maven 3.9.9
- MySQL 8.x
- Docker (only for running integration tests)

### Installing

1.Install Java:  
https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html

2.Install Maven:  
https://maven.apache.org/install.html

3.Install MySQL:  
https://dev.mysql.com/downloads/

4.Install Docker: (only for integration tests)  
https://www.docker.com/get-started/

### Configuration

Copy and rename the "/src/main/ressources/script/.env.template" file into the projet directory as "/.env".  
In the ".env" file you have to :

- configure your MySQL connection

There is two profiles available in the "application.properties" file : "dev" and "prod",
by default the application is set to "dev" mode.

### Database Scripts

Hibernate will automatically create the database schema if it does not exist.  
The schema is also available in the SQL file "/src/main/resources/script/db_schema.sql".

In order to create the default user, you can use the file "/src/main/resources/script/create_admin.sql".

- username : admin.admin
- password : Password@1

Please, for security, create a new admin after first connection and delete the default one.

### Run the application

To run the application, you can :

- use the command : `mvn spring-boot:run`
- run the main class : `PoseidonApplication.java`

Then go to the URL http://localhost:8080/

## Database MPD

![MDP Schema](/src/main/resources/documentation/MPD.png)

## Testing

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

For unit tests and reports : `mvn clean test site`

For unit, integration tests and reports :  `mvn clean verify site`  
Integration tests used TestContainer, so don't forget to start your docker first (https://testcontainers.com/).

Surefire, JaCoCo, JavaDoc reporting are available in the project directory : "/target/site/index.html"

