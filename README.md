# SafetyNet Alerts

## Description
SafetyNet Alerts is an application designed to send informations to emergency services. It exposes several endpoints to obtain or modify data. When an endpoint is called, the application reads a data file and returns the expected information in JSON format. It was created as part of the OpenClassrooms Java Application Developer course. The data used is fake.

## Features
- Data reading from a JSON file
- Multiple GET endpoints to recover specific data, result written in a JSON file
- POST/PUT/DELETE endpoints to modify data in the source file
- Responses in JSON format
- Extensive logging with Log4j2

## Prerequisites
- Java 17
- Maven
- Libraries: 
  - Spring Boot
  - Log4j2
  - JUnit
  - JaCoCo

## Installation
Clone project:
   git clone <URL>
   cd safetynet-alerts
Data file should be place in scr/main/resources/data.json

## Tests
Code is covered by unit tests that are runnable with Spring Boot test.
