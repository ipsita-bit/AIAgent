package com.example.calculator.mcp.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MCP model classes.
 * Tests JSON serialization, deserialization, and model behavior.
 */
class McpModelTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("McpRequest should serialize to JSON correctly")
    void testMcpRequestSerialization() throws Exception {
        McpRequest request = new McpRequest();
        request.setId("test-1");
        request.setMethod("test-method");
        
        Map<String, Object> params = new HashMap<>();
        params.put("key", "value");
        request.setParams(params);

        String json = objectMapper.writeValueAsString(request);

        assertTrue(json.contains("\"jsonrpc\":\"2.0\""), "Should include JSON-RPC version");
        assertTrue(json.contains("\"id\":\"test-1\""), "Should include request ID");
        assertTrue(json.contains("\"method\":\"test-method\""), "Should include method");
        assertTrue(json.contains("\"params\""), "Should include params");
    }

    @Test
    @DisplayName("McpRequest should deserialize from JSON correctly")
    void testMcpRequestDeserialization() throws Exception {
        String json = "{\"jsonrpc\":\"2.0\",\"id\":\"test-2\",\"method\":\"test-method\",\"params\":{\"a\":5}}";

        McpRequest request = objectMapper.readValue(json, McpRequest.class);

        assertNotNull(request, "Request should not be null");
        assertEquals("2.0", request.getJsonrpc(), "Should have correct JSON-RPC version");
        assertEquals("test-2", request.getId(), "Should have correct ID");
        assertEquals("test-method", request.getMethod(), "Should have correct method");
        assertNotNull(request.getParams(), "Params should not be null");
    }

    @Test
    @DisplayName("McpResponse should serialize success response correctly")
    void testMcpResponseSuccessSerialization() throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("answer", 42);

        McpResponse response = new McpResponse("test-id", result);

        String json = objectMapper.writeValueAsString(response);

        assertTrue(json.contains("\"jsonrpc\":\"2.0\""), "Should include JSON-RPC version");
        assertTrue(json.contains("\"id\":\"test-id\""), "Should include response ID");
        assertTrue(json.contains("\"result\""), "Should include result");
        assertFalse(json.contains("\"error\""), "Should not include error field");
    }

    @Test
    @DisplayName("McpResponse should serialize error response correctly")
    void testMcpResponseErrorSerialization() throws Exception {
        McpError error = new McpError(-32600, "Invalid request");
        McpResponse response = new McpResponse("test-id", error);

        String json = objectMapper.writeValueAsString(response);

        assertTrue(json.contains("\"jsonrpc\":\"2.0\""), "Should include JSON-RPC version");
        assertTrue(json.contains("\"id\":\"test-id\""), "Should include response ID");
        assertTrue(json.contains("\"error\""), "Should include error");
        assertFalse(json.contains("\"result\""), "Should not include result field");
    }

    @Test
    @DisplayName("McpResponse should deserialize from JSON correctly")
    void testMcpResponseDeserialization() throws Exception {
        String json = "{\"jsonrpc\":\"2.0\",\"id\":\"test-3\",\"result\":{\"value\":123}}";

        McpResponse response = objectMapper.readValue(json, McpResponse.class);

        assertNotNull(response, "Response should not be null");
        assertEquals("2.0", response.getJsonrpc(), "Should have correct JSON-RPC version");
        assertEquals("test-3", response.getId(), "Should have correct ID");
        assertNotNull(response.getResult(), "Result should not be null");
        assertNull(response.getError(), "Error should be null");
    }

    @Test
    @DisplayName("McpError should have correct standard error codes")
    void testMcpErrorStandardCodes() {
        assertEquals(-32700, McpError.PARSE_ERROR, "Parse error code should be -32700");
        assertEquals(-32600, McpError.INVALID_REQUEST, "Invalid request code should be -32600");
        assertEquals(-32601, McpError.METHOD_NOT_FOUND, "Method not found code should be -32601");
        assertEquals(-32602, McpError.INVALID_PARAMS, "Invalid params code should be -32602");
        assertEquals(-32603, McpError.INTERNAL_ERROR, "Internal error code should be -32603");
    }

    @Test
    @DisplayName("McpError should serialize correctly")
    void testMcpErrorSerialization() throws Exception {
        McpError error = new McpError(-32602, "Invalid parameters", "Additional data");

        String json = objectMapper.writeValueAsString(error);

        assertTrue(json.contains("\"code\":-32602"), "Should include error code");
        assertTrue(json.contains("\"message\":\"Invalid parameters\""), "Should include error message");
        assertTrue(json.contains("\"data\":\"Additional data\""), "Should include error data");
    }

    @Test
    @DisplayName("McpError should deserialize from JSON correctly")
    void testMcpErrorDeserialization() throws Exception {
        String json = "{\"code\":-32601,\"message\":\"Method not found\"}";

        McpError error = objectMapper.readValue(json, McpError.class);

        assertNotNull(error, "Error should not be null");
        assertEquals(-32601, error.getCode(), "Should have correct error code");
        assertEquals("Method not found", error.getMessage(), "Should have correct error message");
    }

    @Test
    @DisplayName("McpTool should serialize correctly")
    void testMcpToolSerialization() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("a", Map.of("type", "number", "description", "First number"));
        properties.put("b", Map.of("type", "number", "description", "Second number"));

        McpTool.InputSchema inputSchema = new McpTool.InputSchema(properties, java.util.Arrays.asList("a", "b"));
        McpTool tool = new McpTool("add", "Add two numbers", inputSchema);

        String json = objectMapper.writeValueAsString(tool);

        assertTrue(json.contains("\"name\":\"add\""), "Should include tool name");
        assertTrue(json.contains("\"description\":\"Add two numbers\""), "Should include description");
        assertTrue(json.contains("\"inputSchema\""), "Should include input schema");
        assertTrue(json.contains("\"properties\""), "Should include properties");
        assertTrue(json.contains("\"required\""), "Should include required fields");
    }

    @Test
    @DisplayName("McpTool should deserialize from JSON correctly")
    void testMcpToolDeserialization() throws Exception {
        String json = "{\"name\":\"multiply\",\"description\":\"Multiply two numbers\"," +
                      "\"inputSchema\":{\"type\":\"object\",\"properties\":{\"a\":{\"type\":\"number\"}," +
                      "\"b\":{\"type\":\"number\"}},\"required\":[\"a\",\"b\"]}}";

        McpTool tool = objectMapper.readValue(json, McpTool.class);

        assertNotNull(tool, "Tool should not be null");
        assertEquals("multiply", tool.getName(), "Should have correct name");
        assertEquals("Multiply two numbers", tool.getDescription(), "Should have correct description");
        assertNotNull(tool.getInputSchema(), "Input schema should not be null");
        assertEquals("object", tool.getInputSchema().getType(), "Input schema type should be object");
    }

    @Test
    @DisplayName("McpRequest should handle integer ID")
    void testMcpRequestWithIntegerId() {
        McpRequest request = new McpRequest();
        request.setId(123);

        assertEquals(123, request.getId(), "Should handle integer ID");
    }

    @Test
    @DisplayName("McpRequest should handle string ID")
    void testMcpRequestWithStringId() {
        McpRequest request = new McpRequest();
        request.setId("string-id");

        assertEquals("string-id", request.getId(), "Should handle string ID");
    }

    @Test
    @DisplayName("McpResponse should handle null result")
    void testMcpResponseWithNullResult() {
        McpResponse response = new McpResponse();
        response.setId("test");
        response.setResult(null);

        assertNull(response.getResult(), "Result should be null");
        assertNull(response.getError(), "Error should be null");
    }

    @Test
    @DisplayName("McpError should handle null data")
    void testMcpErrorWithNullData() {
        McpError error = new McpError(500, "Test error");

        assertEquals(500, error.getCode(), "Should have correct code");
        assertEquals("Test error", error.getMessage(), "Should have correct message");
        assertNull(error.getData(), "Data should be null");
    }

    @Test
    @DisplayName("McpTool InputSchema should default to object type")
    void testInputSchemaDefaultType() {
        McpTool.InputSchema schema = new McpTool.InputSchema();

        assertEquals("object", schema.getType(), "Default type should be 'object'");
    }
}
