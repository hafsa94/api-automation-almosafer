# API Automation Using RestAssured and Cucumber
This project will be used to implement functional API automation test cases using Cucumber, RestAssured and JUnit.

## Prerequisites
- Maven
- Java

## Project Installation:
1. Clone the git project https://github.com/hafsa94/api-automation-almosafer.git
2. Open the project in the preferred IDE (We are using IntelliJ)
3. Import the maven dependencies

## Running Test Scenarios:

To run the scenarios, use the below command in your terminal
```
mvn clean test
```
To run the scenario with a specific tag, use the below command
```
mvn test -Dcucumber.filter.tags="@smoke"
```
## Test Report:
After running the test scenarios, the full report cucumber.html can be found under the `target/cucumber/` path.
