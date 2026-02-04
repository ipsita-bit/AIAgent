# Calculator Spring Boot Application

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen.svg)
![Gradle](https://img.shields.io/badge/Gradle-8.5-blue.svg)
![License](https://img.shields.io/badge/license-MIT-blue.svg)

A simple RESTful calculator service built with Spring Boot and Gradle.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Building the Application](#building-the-application)
  - [Running the Application](#running-the-application)
  - [Running Tests](#running-tests)
- [Code Coverage](#code-coverage)
  - [Running Tests with Coverage](#running-tests-with-coverage)
  - [Using the Code Coverage Agent](#using-the-code-coverage-agent)
  - [Coverage Verification](#coverage-verification)
  - [Current Coverage Statistics](#current-coverage-statistics)
- [API Endpoints](#api-endpoints)
  - [Addition](#addition)
  - [Subtraction](#subtraction)
  - [Multiplication](#multiplication)
  - [Division](#division)
- [Example Usage](#example-usage)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)
- [License](#license)

## Features

- Addition
- Subtraction
- Multiplication
- Division (with division by zero handling)

## Technologies

- **Java 17** - Programming language
- **Spring Boot 3.2.1** - Application framework
- **Gradle 8.5** - Build automation tool
- **JUnit 5** - Testing framework
- **JaCoCo** - Code coverage analysis

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 17** or higher
  - Verify installation: `java -version`
- **Gradle 8.5** (or use the included Gradle wrapper)
  - The project includes `gradlew` (Unix/Linux/macOS) and `gradlew.bat` (Windows)
- **Git** - For cloning the repository

**Note:** You don't need to install Gradle separately as this project includes the Gradle Wrapper (`gradlew`/`gradlew.bat`).

## Project Structure

```
AIAgent/
├── src/
│   ├── main/
│   │   ├── java/com/example/calculator/
│   │   │   ├── CalculatorApplication.java     # Main application class
│   │   │   ├── CalculatorController.java      # REST controller
│   │   │   ├── CalculatorService.java         # Business logic
│   │   │   ├── CalculatorResponse.java        # Response model
│   │   │   └── CodeCoverageAgent.java         # Code coverage analysis agent
│   │   └── resources/
│   │       └── application.properties         # Application configuration
│   └── test/
│       └── java/com/example/calculator/
│           ├── CalculatorApplicationTests.java
│           ├── CalculatorControllerTest.java
│           ├── CalculatorServiceTest.java
│           ├── CalculatorResponseTest.java
│           └── CodeCoverageAgentTest.java
├── build.gradle                               # Gradle build configuration
├── settings.gradle                            # Gradle settings
└── README.md                                  # This file
```

## Getting Started

### Building the Application

Build the project using the Gradle wrapper:

```bash
./gradlew build
```

On Windows, use:
```bash
gradlew.bat build
```

This will compile the code, run tests, and create the JAR file in `build/libs/`.

### Running the Application

Start the application using the Gradle wrapper:

```bash
./gradlew bootRun
```

On Windows, use:
```bash
gradlew.bat bootRun
```

The application will start on **port 8080**. You should see output indicating the server has started:
```
Started CalculatorApplication in X.XXX seconds
```

To verify the application is running, open your browser and navigate to:
```
http://localhost:8080/api/calculator/add?a=5&b=3
```

### Running Tests

Run all tests using the Gradle wrapper:

```bash
./gradlew test
```

On Windows, use:
```bash
gradlew.bat test
```

Test results will be available in `build/reports/tests/test/index.html`.

## Code Coverage

This project uses JaCoCo for code coverage analysis and includes a **CodeCoverageAgent** that automatically analyzes test coverage and provides recommendations for improvement.

### Running Tests with Coverage

```bash
./gradlew test jacocoTestReport
```

The coverage report will be generated at:
- HTML: `build/reports/jacoco/test/html/index.html`
- XML: `build/reports/jacoco/test/jacocoTestReport.xml`
- CSV: `build/reports/jacoco/test/jacocoTestReport.csv`

### Using the Code Coverage Agent

The CodeCoverageAgent analyzes JaCoCo coverage reports and provides actionable recommendations:

```bash
# Using the Gradle task (recommended)
./gradlew analyzeCoverage

# Or run directly with Java (after building)
java -cp build/classes/java/main com.example.calculator.CodeCoverageAgent

# Run with custom coverage report path and threshold
java -cp build/classes/java/main com.example.calculator.CodeCoverageAgent \
  build/reports/jacoco/test/jacocoTestReport.csv 0.90
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

The agent will:
- Parse the JaCoCo CSV coverage report
- Identify classes below the coverage threshold
- Generate specific recommendations for improving coverage
- Provide statistics on overall project coverage

Example output:
```
=== Code Coverage Analysis Report ===

Overall Coverage: 99.04%
Coverage Threshold: 80.00%

Classes below coverage threshold:

Class: CalculatorApplication - Coverage: 37.50%
  - Add tests for 1 untested method(s)
  - Cover 2 untested line(s)
  - Main method coverage is optional for application entry points

Summary:
- Total classes: 6
- Classes below threshold: 1
- Classes meeting threshold: 5
```

### Coverage Verification

To verify that coverage meets the minimum threshold (80%):

```bash
./gradlew jacocoTestCoverageVerification
```

### Current Coverage Statistics

- **Overall Coverage**: 99.04%
- **CalculatorController**: 100%
- **CalculatorService**: 100%
- **CalculatorResponse**: 100%
- **CodeCoverageAgent**: 99.56%
- **CalculatorApplication**: 37.50% (main method excluded)

## API Endpoints

All endpoints are under `/api/calculator` and accept two query parameters: `a` and `b`.

**Base URL:** `http://localhost:8080`

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

## Troubleshooting

### Common Issues

**Issue: `java: command not found`**
- **Solution**: Install JDK 17 or higher. Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/).

**Issue: `Port 8080 already in use`**
- **Solution**: Stop the process using port 8080 or change the port in `src/main/resources/application.properties`:
  ```properties
  server.port=8081
  ```

**Issue: Tests fail with "Cannot find JaCoCo report"**
- **Solution**: Run tests first to generate the report:
  ```bash
  ./gradlew test jacocoTestReport
  ```

**Issue: `gradlew: Permission denied` (Unix/Linux/macOS)**
- **Solution**: Make the Gradle wrapper executable:
  ```bash
  chmod +x gradlew
  ```

**Issue: Build fails with "Unsupported class file major version"**
- **Solution**: Ensure you're using JDK 17 or higher. Check with `java -version`.

### Getting Help

If you encounter any issues not covered here:
1. Check the [GitHub Issues](../../issues) for similar problems
2. Create a new issue with:
   - Description of the problem
   - Steps to reproduce
   - Your environment (OS, Java version, etc.)
   - Error messages or logs

## Contributing

Contributions are welcome! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes**
   - Follow the existing code style
   - Add tests for new functionality
   - Ensure all tests pass: `./gradlew test`
   - Maintain or improve code coverage
4. **Commit your changes**
   ```bash
   git commit -am 'Add some feature'
   ```
5. **Push to the branch**
   ```bash
   git push origin feature/your-feature-name
   ```
6. **Create a Pull Request**

For detailed contribution guidelines, please read [CONTRIBUTING.md](CONTRIBUTING.md).

### Code Quality Guidelines

- Maintain test coverage above 80%
- Follow Java naming conventions
- Write clear, descriptive commit messages
- Document public APIs with JavaDoc
- Run `./gradlew build` before submitting PRs

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

**Built with ❤️ using Spring Boot**
