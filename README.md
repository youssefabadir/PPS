# Picture Publishing Service
A web application built with Spring Boot.

## Overview
Picture Publishing Service allows users to upload pictures, and an administrator to review and approve or reject those pictures.

## Prerequisites
* [Maven](https://maven.apache.org/download.cgi)
* [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)
* [Postgresql](https://www.postgresql.org/download/)

## Steps to deploy
* Import project to your IDE. 
* Run the database script to initialize the database found at [DB script](db/postgres.sql).
* Credentials for admin user:
  * email: admin@mail.com
  * password: admin123
* Credentials for normal user:
  * email: user@mail.com
  * password: user123

## Links
swagger: http://localhost:8080/swagger-ui/index.html
