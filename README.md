# Project Description

This project is implemented for **Takeaway** as an assignment project. It's a Spring Boot project which developed by using Java language.

## Build & Run

This project uses Mysql as database server and Kafka as messaging queue. You can follow the next instructions to run the application.

### Docker containers

You can easily run needed servers as dockerised containers at root directory. Please run the following command:


```bash
docker-compose up
```

By completing this step, you will have a running mysql server at `localhost:3306` and kafka at `localhost:9092`

### Run Application

This project uses Maven as build tool. You can run the following commands to build and run the application:

```bash
mvn clean package spring-boot:repackage
java -jar target/employeeService-0.0.1-SNAPSHOT.jar
```

### Run Application as Docker Container

In the root directory you can find the Dockerfile to dockerize the application. 

Go to root project and run following commands:

```
docker build -t challenge . 
docker run --rm -p 8080:8080 challenge
```


## REST APIs

### Create Department

Creates a department with giving name.


URL path: ``` POST /employees/department```


Request Model: 
```json
{
"name":"Engineering"
}
```

Response Model: API will return id and name of the created entity if successful.
```json
{
    "id": 1,
    "name": "Engineering"
}
```


### Create Employee

Creates an employee with giving information.

URL path: ``` POST /employees```

Request Model:
* unique email
* creates department if doesn't exist with giving name

```json
{
	"name": "Zehra Komurcu",
	"email": "zehra@test.com",
	"birthday": "1992-07-03",
	"department": "Engineering"
}
```

### Get Employee by Id

Get an employee for giving id.

URL path: ```GET employees/{id}```

Response Model:
```json
{
    "id": "68951edf-39df-47c9-809b-b9bc9cf7a75f",
    "name": "Zehra Komurcu",
    "email": "zehra@test.com",
    "birthday": "1992-07-03",
    "department": {
        "id": 1,
        "name": "Engineering"
    }
}
```


### Update Employee

Updates employee for giving id.

URL path: ```PUT /employees/{id}```

Request Model:

* This API gets updated information and only update giving attributes. Returns the updated entity as a reponse object.
```json
{
    "name": "Update name",
    "email": "updated@email.com"
}
```

Response Model:
```json
{
    "id": "68951edf-39df-47c9-809b-b9bc9cf7a75f",
    "name": "Update name",
    "email": "updated@email.com",
    "birthday": "1992-07-03",
    "department": {
        "id": 1,
        "name": "Engineering"
    }
}
```

### Delete Employee

Deletes employee for giving id.

URL path: ```DELETE /employees/{id}```

Response Model:
* Returns 200 OK with no response body.


## Postman Collections

You can find the postman collection for each REST API call in project's root directory. 

The file name:
```
takeaway.postman_collection.json
```

## Author

Zehra Nur Komurcu
