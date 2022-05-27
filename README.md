# tabcorp-microservice

> About the project

tabcorp-microservice  is a sample Microservice which provides the below functionalities.

* Pre-populate Customer and Product table on server start.
* Accept a large amount of transactions per second
* Store data in any database ( Ex: embedded H2 database) 
* Add following validations to the service
    - Date must not be in the past
    - Total cost of transaction must not exceed 5000
    - Product must be active
* Add report endpoints for the following
    - Total cost of transactions per customer
    - Total cost of transactions per product
    - Number of transactions sold to customer from Australia
* Add security to the Microservice

## Running the project

### Prerequisites
* Java 8
* Maven
* Postman
* CMD

## Build and Run

```
Use below command to Build and Run the Application.

Build:
> mvn clean install
    The above command will build the application and create a jar file with name 'tabcorp-1.0.0.jar'

Run:
> mvn spring-boot:run
or
> java -jar target/tabcorp-1.0.0.jar
    The above two command will run the application from the command line

```

### Functionalities

Customer and Product data

```

The data for customer and product will be pre-populated in the embedded H2 database eachtime the server is started.
The schema of the tables and data is located at /src/java/resources/data.sql and /src/java/resources/schema.sql respectively

The validation of the data will be done before inserting the transaction to the database and appropriate transaction status will be sent as
a response.

```
***The request now needs to be serviced with https or else the request will be rejected by the service. Also, since the certificate is 
generated locally, the certification validation needs to be disabled in postman for successful transaction.***

Endpoints

```
Different endpoints have been exposed to handle various functionalities

* Endpoint for inserting transaction to the database - https://localhost:8040/addTransaction. The request method is post.

Report generation Endpoint (The request method is get for all endpoints)

* Endpoint for total cost of transactions per customer - https://localhost:8040/customerTransaction. 
* Total cost of transactions per product - https://localhost:8040/productTransaction.
* Number of transactions sold to customer from Australia - https://localhost:8040/findByLocation.
```

### Postman collection

```
Sample postman collection is placed in the root project
collection : src\main\resources\static\postman-collection\

* To insert transactions - dummy-transaction-valid.json is provided
* To validate date must not be in the past - dummy-transaction-past-date.json
* To validate product must be active - dummy-transaction-inactive.json
* Total cost of transaction must not exceed 5000 - dummy-transaction-highest-cost.json is provided
```

## Jacoco Coverage Report

```
In this application used Jacoco Plug-In to verify the coverage report, can found after the build under: target\jacoco-report/index.html
To buid and generate report, please running below command
> mvn test

```
