# Calculator Spring Boot Application

A RESTful calculator service built with Spring Boot and Gradle, featuring a Model Context Protocol (MCP) Server for AI model integration.

## Features

- Addition, Subtraction, Multiplication, Division (with division by zero handling)
- **Model Context Protocol (MCP) Server** - Enables AI models to discover and invoke calculator operations
- RESTful API endpoints
- Comprehensive test coverage (99%+)
- Code coverage analysis with JaCoCo

## Technologies

- Java 17
- Spring Boot 3.2.1
- Gradle 8.5
- JUnit 5 for testing
- JaCoCo for code coverage analysis
- Model Context Protocol (JSON-RPC 2.0)

## Project Structure

```
calculator/
├── src/
│   ├── main/
│   │   ├── java/com/example/calculator/
│   │   │   ├── CalculatorApplication.java     # Main application class
│   │   │   ├── CalculatorController.java      # REST controller
│   │   │   ├── CalculatorService.java         # Business logic
│   │   │   ├── CalculatorResponse.java        # Response model
│   │   │   ├── CodeCoverageAgent.java         # Code coverage analysis agent
│   │   │   └── mcp/                           # MCP Server components
│   │   │       ├── controller/
│   │   │       │   └── McpController.java     # MCP endpoints
│   │   │       ├── service/
│   │   │       │   └── McpToolService.java    # Tool implementation
│   │   │       └── model/
│   │   │           ├── McpRequest.java        # JSON-RPC request
│   │   │           ├── McpResponse.java       # JSON-RPC response
│   │   │           ├── McpError.java          # Error handling
│   │   │           └── McpTool.java           # Tool definitions
│   │   └── resources/
│   │       └── application.properties         # Application configuration
│   └── test/
│       └── java/com/example/calculator/
│           ├── CalculatorApplicationTests.java
│           ├── CalculatorControllerTest.java
│           ├── CalculatorServiceTest.java
│           ├── CalculatorResponseTest.java
│           ├── CodeCoverageAgentTest.java
│           └── mcp/                           # MCP tests (53 tests)
│               ├── controller/
│               │   └── McpControllerTest.java
│               ├── service/
│               │   └── McpToolServiceTest.java
│               └── model/
│                   └── McpModelTest.java
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

# Or run directly with Java
java -cp build/classes/java/main com.example.calculator.CodeCoverageAgent

# Run with custom coverage report path and threshold
java -cp build/classes/java/main com.example.calculator.CodeCoverageAgent \
  build/reports/jacoco/test/jacocoTestReport.csv 0.90
```

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

---

## Model Context Protocol (MCP) Server

The application includes a **Model Context Protocol (MCP) Server** that allows AI models like Claude, ChatGPT, and others to discover and invoke calculator operations through a standardized JSON-RPC 2.0 interface.

### What is MCP?

Model Context Protocol (MCP) is an open, standardized protocol that connects AI applications to external tools, data sources, and services. This MCP server exposes calculator operations as "tools" that AI models can discover, understand, and invoke.

### MCP Endpoints

The MCP server is available at the `/mcp` base path:

- `POST /mcp/initialize` - Get server information and capabilities
- `POST /mcp/tools/list` - List all available calculator tools
- `POST /mcp/tools/call` - Invoke a specific calculator tool
- `GET /mcp/health` - Health check endpoint

### Available MCP Tools

The following calculator tools are available to AI models:

1. **add** - Add two numbers together
2. **subtract** - Subtract the second number from the first
3. **multiply** - Multiply two numbers together
4. **divide** - Divide the first number by the second (with zero-division handling)

### MCP Usage Examples

#### Initialize the MCP Server

```bash
curl -X POST http://localhost:8080/mcp/initialize \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "id": "1",
    "method": "initialize"
  }'
```

Response:
```json
{
  "jsonrpc": "2.0",
  "id": "1",
  "result": {
    "protocolVersion": "2024-11-05",
    "serverInfo": {
      "name": "Calculator MCP Server",
      "version": "1.0.0"
    },
    "capabilities": {
      "tools": {}
    }
  }
}
```

#### List Available Tools

```bash
curl -X POST http://localhost:8080/mcp/tools/list \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "id": "2",
    "method": "tools/list"
  }'
```

Response:
```json
{
  "jsonrpc": "2.0",
  "id": "2",
  "result": {
    "tools": [
      {
        "name": "add",
        "description": "Add two numbers together and return the sum",
        "inputSchema": {
          "type": "object",
          "properties": {
            "a": {
              "type": "number",
              "description": "First number"
            },
            "b": {
              "type": "number",
              "description": "Second number"
            }
          },
          "required": ["a", "b"]
        }
      },
      {
        "name": "subtract",
        "description": "Subtract the second number from the first number",
        "inputSchema": {
          "type": "object",
          "properties": {
            "a": {
              "type": "number",
              "description": "Number to subtract from"
            },
            "b": {
              "type": "number",
              "description": "Number to subtract"
            }
          },
          "required": ["a", "b"]
        }
      }
      // ... multiply and divide tools
    ]
  }
}
```

#### Call a Tool (Add Example)

```bash
curl -X POST http://localhost:8080/mcp/tools/call \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "id": "3",
    "method": "tools/call",
    "params": {
      "name": "add",
      "arguments": {
        "a": 15,
        "b": 27
      }
    }
  }'
```

Response:
```json
{
  "jsonrpc": "2.0",
  "id": "3",
  "result": {
    "content": [
      {
        "type": "text",
        "text": "42.0"
      }
    ]
  }
}
```

#### Error Handling Example (Division by Zero)

```bash
curl -X POST http://localhost:8080/mcp/tools/call \
  -H "Content-Type: application/json" \
  -d '{
    "jsonrpc": "2.0",
    "id": "4",
    "method": "tools/call",
    "params": {
      "name": "divide",
      "arguments": {
        "a": 10,
        "b": 0
      }
    }
  }'
```

Response (HTTP 400):
```json
{
  "jsonrpc": "2.0",
  "id": "4",
  "error": {
    "code": -32602,
    "message": "Cannot divide by zero"
  }
}
```

### Health Check

```bash
curl http://localhost:8080/mcp/health
```

Response:
```json
{
  "status": "UP",
  "server": "Calculator MCP Server",
  "version": "1.0.0"
}
```

### Integrating with AI Models

To integrate this MCP server with AI models:

1. **Start the server**: `./gradlew bootRun`
2. **Configure your AI client** to connect to `http://localhost:8080/mcp`
3. **AI models can now**:
   - Discover available calculator tools via `tools/list`
   - Invoke tools via `tools/call` with appropriate parameters
   - Receive structured responses in JSON-RPC 2.0 format

### MCP Error Codes

The server uses standard JSON-RPC 2.0 error codes:

- `-32700` - Parse error
- `-32600` - Invalid request
- `-32601` - Method not found (tool not found)
- `-32602` - Invalid params (missing or invalid parameters)
- `-32603` - Internal error

### Testing

The MCP server includes comprehensive test coverage:

- **53 MCP-specific tests**
  - 29 unit tests for tool service
  - 20 integration tests for controller endpoints
  - 4 model serialization tests
- **88% code coverage** for MCP components

Run MCP tests:
```bash
./gradlew test --tests "com.example.calculator.mcp.*"
```
