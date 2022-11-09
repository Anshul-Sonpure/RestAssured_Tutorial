# RestAssured_Tutorial
Hi All,
This repository is for all those who want to learn API testing using rest assured.\
In this repo I have written testscripts on how we can make GET,POST,PATCH/PUT and DELETE request in restassured for API Testing.\
I have also covered some other testscripts like setting query,path and form parameters,how we can log data and how to do validation.\
Also I have tried to cover an end to end flow for and API Testing.\
Since this is not a framework hence no implementation for reporting is done.\
But we can still integrate Extent Report with rest assured and build an API Testing Framework.\
In Repo you will learn how to make GET call, POST using map,POJO class,JSON Object,Json file, PUT/PATCH/DELETE request.\
How to set query,path parameter and how to log header,body, cookies.\
How to blacklist header being print on console.\
How to upload and download file using api call.\
How to Test asynchronous systems, using Awaitility which is a DSL that\
allows you to express expectations of an asynchronous system in a concise and easy to read manner.

## Features
- Rest assured is java library for testing Restful Web services. It can be used to test XML & JSON based web services. 
- It supports GET, POST, PUT, PATCH, DELETE, OPTIONS and HEAD requests and can be used to validate and verify the response of these requests. 
- Also it can be integrated with testing frameworks like JUnit, TestNG etc.

## Methods

HTTP methods (GET, PUT, POST, PATCH and DELETE) and these methods can be mapped to CRUD operations.

    GET retrieves the resource at a specified URI.
    PUT updates a resource at a specified URI. Also be used to create a new resource at a specified URI. Replaces the entire product entity.
    PATCH support partial updates.
    POST creates a new resource.
    DELETE deletes a resource at a specified URI.

## Error Codes
HTTP response status codes are grouped in five classes:

    Informational responses (100–199),
    Successful responses (200–299),
    Redirects (300–399),
    Client errors (400–499),
    Server errors (500–599)


## Installation
IDE - IntelliJ / Eclipse
Version Control - Git
Build tool - Maven,TestNG

Clone the repo
```
git clone https://github.com/Anshul-Sonpure/RestAssured_Tutorial.git
```
Build the project
```
mvn clean 
mvn install
```
To run the project either execute each testscript or
```
mvn test
```
Thank You\
Happy Coding,\
Learn,Code and Earn\
Stay Safe and Stay Positive :)
