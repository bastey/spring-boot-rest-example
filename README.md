# Spring Boot "Microservice" Example Project

This is a sample Java / Maven / Spring Boot (version 1.5.6) application that can be used as a starter for creating a microservice complete with built-in health check, metrics and much more. I hope it helps you.

## How to Run

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test target/spring-boot-rest-example-0.4.0.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
* Check the stdout or boot_example.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2017-08-30 17:31:23.091  INFO 19387 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090 (http)
2017-08-30 17:31:23.097  INFO 19387 --- [           main] com.bstey.example.Application        : Started Application in 22.285 seconds (JVM running for 23.032)
```

## About the Service

The service is just a simple hotel review REST service. It uses an in-memory database (H2) to store the data. You can also do with a relational database like MySQL or PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in ```com.bstey.example.api.rest.hotelController``` on **port 8090**. (see below)

More interestingly, you can start calling some of the operational endpoints (see full list below) like ```/metrics``` and ```/health``` (these are available on **port 8091**)

You can use this sample service to understand the conventions and configurations that allow you to create a DB-backed RESTful service. Once you understand and get comfortable with the sample app you can add your own services following the same patterns as the sample service.

Here is what this little application demonstrates:

* Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single war with embedded container (tomcat 8): No need to install a container separately on the host just run using the ``java -jar`` command
* Demonstrates how to set up healthcheck, metrics, info, environment, etc. endpoints automatically on a configured port. Inject your own health / metrics info with a few lines of code.
* Writing a RESTful service using annotation: supports both XML and JSON request / response; simply use desired ``Accept`` header in your request
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations.
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* Demonstrates MockMVC test framework with associated libraries
* All APIs are "self-documented" by Swagger2 using annotations

Here are some endpoints you can call:

### Get information about system health, configurations, etc.

```
http://localhost:8091/env
http://localhost:8091/health
http://localhost:8091/info
http://localhost:8091/metrics
```

### Create a hotel resource

```
POST /example/v1/hotels
Accept: application/json
Content-Type: application/json

{
"name" : "Beds R Us",
"description" : "Very basic, small rooms but clean",
"city" : "Santa Ana",
"rating" : 2
}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8090/example/v1/hotels/1
```

### Retrieve a paginated list of hotels

```
http://localhost:8090/example/v1/hotels?page=0&size=10

Response: HTTP 200
Content: paginated list
```

### Update a hotel resource

```
PUT /example/v1/hotels/1
Accept: application/json
Content-Type: application/json

{
"name" : "Beds R Us",
"description" : "Very basic, small rooms but clean",
"city" : "Santa Ana",
"rating" : 3
}

RESPONSE: HTTP 204 (No Content)
```
### To view Swagger 2 API docs

Run the server and browse to localhost:8090/swagger-ui.html

# About Spring Boot

Spring Boot is an "opinionated" application bootstrapping framework that makes it easy to create new RESTful services (among other types of applications). It provides many of the usual Spring facilities that can be configured easily usually without any XML. In addition to easy set up of Spring Controllers, Spring Data, etc. Spring Boot comes with the Actuator module that gives the application the following endpoints helpful in monitoring and operating the service:

**/metrics** Shows “metrics” information for the current application.

**/health** Shows application health information.

**/info** Displays arbitrary application info.

**/configprops** Displays a collated list of all @ConfigurationProperties.

**/mappings** Displays a collated list of all @RequestMapping paths.

**/beans** Displays a complete list of all the Spring Beans in your application.

**/env** Exposes properties from Spring’s ConfigurableEnvironment.

**/trace** Displays trace information (by default the last few HTTP requests).

### To view your H2 in-memory datbase

The 'test' profile runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8090/h2-console. Default username is 'sa' with a blank password. Make sure you disable this in your production profiles. For more, see https://goo.gl/U8m62X

# Running the project with MySQL

This project uses an in-memory database so that you don't have to install a database in order to run it. However, converting it to run with another relational database such as MySQL or PostgreSQL is very easy. Since the project uses Spring Data and the Repository pattern, it's even fairly easy to back the same service with MongoDB.

Here is what you would do to back the services with MySQL, for example:

### In pom.xml add:

```
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
```

### Append this to the end of application.yml:

```
---
spring:
  profiles: mysql

  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://<your_mysql_host_or_ip>/bootexample
    username: <your_mysql_username>
    password: <your_mysql_password>

  jpa:
    hibernate:
      dialect: org.hibernate.dialect.MySQLInnoDBDialect
      ddl-auto: update # todo: in non-dev environments, comment this out:


hotel.service:
  name: 'test profile:'
```

### Then run is using the 'mysql' profile:

```
        java -jar -Dspring.profiles.active=mysql target/spring-boot-rest-example-0.4.0.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=mysql"
```

# Attaching to the app remotely from your IDE

Run the service with these command line options:

```
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
or
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dspring.profiles.active=test -Ddebug -jar target/spring-boot-rest-example-0.4.0.war
```
and then you can connect to it remotely using your IDE. For example, from IntelliJ You have to add remote debug configuration: Edit configuration -> Remote.


# TP
## TP1 : Basic Auth sur Java avec Spring security

Résulat final :
```
git checkout basic_auth
```
1) Configurer le Basic Auth avec Spring Security ==> SecurityConfiguration
2) Définir un Entry Point dans le cas ou l'authentification est incorrecte => CustomBasicAuthenticationEntryPoint
3) Tester avec postman : http://localhost:8090/example/v1/hotels + toto:titi
4) Tester avec un client Java : SpringRestHotelClient


## TP2 : DEMO SSL one way sur Java avec Spring Boot

Résulat final :
```
git checkout basic_auth_and_ssl_one_way
```

Avec Spring Boot on peut configurer HTTP ou HTTPS mais pas les 2.  
Si vous voulez activer les 2, vous devez configurer l'un des 2 par du code.  
La documentation Spring Boot recommande de configurer le HTTPS dans le application.properties parce que c'est le + compliqué à définir  

Convertir le keystore p12 en jks :
```
cd C:\certificat
keytool -importkeystore -srckeystore server_keystore.p12 -srcstoretype PKCS12 -deststoretype JKS -destkeystore server_keystore.jks
password : secret
```

Copier le fichier .jks dans src/main/resources

Dans le application.yml
```
\#enable HTTPS
server:
  port: 9443
  ssl:
    key-store: src\main\resources\server_keystore.jks
    key-store-password: secret
```

Ajouter le keystore (au format p12) dans les certificats de Chrome
Redémarrer Chrome
Accéder à l'URL https://localhost:9443/example/v1/hotels  depuis CHROME et accepter le certificat non signé

Tester dans POSTMAN l'URL : https://localhost:9443/example/v1/hotels et le login:password : toto:titi
