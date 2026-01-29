# Calculator Spring Boot Application

A simple RESTful calculator service built with Spring Boot and Gradle.

## Features

- Addition
- Subtraction
- Multiplication
- Division (with division by zero handling)

## Technologies

- Java 17
- Spring Boot 3.2.1
- Gradle 8.5
- JUnit 5 for testing

## Project Structure

```
calculator/
├── src/
│   ├── main/
│   │   ├── java/com/example/calculator/
│   │   │   ├── CalculatorApplication.java     # Main application class
│   │   │   ├── CalculatorController.java      # REST controller
│   │   │   └── CalculatorService.java         # Business logic
│   │   └── resources/
│   │       └── application.properties         # Application configuration
│   └── test/
│       └── java/com/example/calculator/
│           ├── CalculatorApplicationTests.java
│           ├── CalculatorControllerTest.java
│           └── CalculatorServiceTest.java
├── build.gradle                               # Gradle build configuration
└── settings.gradle                            # Gradle settings
```

## Building the Application

```bash
./gradlew build
```

## Running Tests

```bash
./gradlew test
```

## Running the Application

```bash
./gradlew bootRun
```

The application will start on port 8080.

## API Endpoints

All endpoints are under `/api/calculator` and accept two query parameters `a` and `b`.

### Addition
```bash
GET /api/calculator/add?a=5&b=3
```
Response:
```json
{
  "operandA": 5.0,
  "operandB": 3.0,
  "result": 8.0,
  "operation": "addition",
  "error": null
}
```

### Subtraction
```bash
GET /api/calculator/subtract?a=10&b=4
```
Response:
```json
{
  "operandA": 10.0,
  "operandB": 4.0,
  "result": 6.0,
  "operation": "subtraction",
  "error": null
}
```

### Multiplication
```bash
GET /api/calculator/multiply?a=6&b=7
```
Response:
```json
{
  "operandA": 6.0,
  "operandB": 7.0,
  "result": 42.0,
  "operation": "multiplication",
  "error": null
}
```

### Division
```bash
GET /api/calculator/divide?a=20&b=4
```
Response:
```json
{
  "operandA": 20.0,
  "operandB": 4.0,
  "result": 5.0,
  "operation": "division",
  "error": null
}
```

### Division by Zero
```bash
GET /api/calculator/divide?a=10&b=0
```
Response (HTTP 400 Bad Request):
```json
{
  "operandA": 10.0,
  "operandB": 0.0,
  "result": 0.0,
  "operation": "division",
  "error": "Cannot divide by zero"
}
```

## Example Usage

Using `curl`:
```bash
curl "http://localhost:8080/api/calculator/add?a=5&b=3"
curl "http://localhost:8080/api/calculator/subtract?a=10&b=4"
curl "http://localhost:8080/api/calculator/multiply?a=6&b=7"
curl "http://localhost:8080/api/calculator/divide?a=20&b=4"
```

Using `wget`:
```bash
wget -q -O - "http://localhost:8080/api/calculator/add?a=5&b=3"
```
