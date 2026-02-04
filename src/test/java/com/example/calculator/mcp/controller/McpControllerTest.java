package com.example.calculator.mcp.controller;

import com.example.calculator.mcp.model.McpError;
import com.example.calculator.mcp.model.McpRequest;
import com.example.calculator.mcp.model.McpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for McpController.
 * Tests MCP endpoints, JSON-RPC communication, and error handling.
 */
@SpringBootTest
@AutoConfigureMockMvc
class McpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Health endpoint should return server status")
    void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/mcp/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.server").value("Calculator MCP Server"))
                .andExpect(jsonPath("$.version").value("1.0.0"));
    }

    @Test
    @DisplayName("Initialize endpoint should return server information")
    void testInitialize() throws Exception {
        McpRequest request = new McpRequest();
        request.setId("1");
        request.setMethod("initialize");

        MvcResult result = mockMvc.perform(post("/mcp/initialize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertEquals("2.0", response.getJsonrpc(), "Should use JSON-RPC 2.0");
        assertEquals("1", response.getId(), "Response ID should match request ID");
        assertNotNull(response.getResult(), "Result should not be null");
        assertNull(response.getError(), "Error should be null");

        @SuppressWarnings("unchecked")
        Map<String, Object> result_map = (Map<String, Object>) response.getResult();
        assertEquals("2024-11-05", result_map.get("protocolVersion"), "Should return protocol version");
        assertNotNull(result_map.get("serverInfo"), "Should return server info");
        assertNotNull(result_map.get("capabilities"), "Should return capabilities");
    }

    @Test
    @DisplayName("List tools endpoint should return all available tools")
    void testListTools() throws Exception {
        McpRequest request = new McpRequest();
        request.setId("2");
        request.setMethod("tools/list");

        MvcResult result = mockMvc.perform(post("/mcp/tools/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertEquals("2.0", response.getJsonrpc(), "Should use JSON-RPC 2.0");
        assertEquals("2", response.getId(), "Response ID should match request ID");
        assertNotNull(response.getResult(), "Result should not be null");
        assertNull(response.getError(), "Error should be null");
    }

    @Test
    @DisplayName("Call tool endpoint should successfully invoke add tool")
    void testCallAddTool() throws Exception {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("a", 5.0);
        arguments.put("b", 3.0);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "add");
        params.put("arguments", arguments);

        McpRequest request = new McpRequest();
        request.setId("3");
        request.setMethod("tools/call");
        request.setParams(params);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertEquals("2.0", response.getJsonrpc(), "Should use JSON-RPC 2.0");
        assertEquals("3", response.getId(), "Response ID should match request ID");
        assertNotNull(response.getResult(), "Result should not be null");
        assertNull(response.getError(), "Error should be null");
    }

    @Test
    @DisplayName("Call tool endpoint should successfully invoke subtract tool")
    void testCallSubtractTool() throws Exception {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("a", 10.0);
        arguments.put("b", 4.0);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "subtract");
        params.put("arguments", arguments);

        McpRequest request = new McpRequest();
        request.setId("4");
        request.setMethod("tools/call");
        request.setParams(params);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertNull(response.getError(), "Error should be null");
    }

    @Test
    @DisplayName("Call tool endpoint should successfully invoke multiply tool")
    void testCallMultiplyTool() throws Exception {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("a", 6.0);
        arguments.put("b", 7.0);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "multiply");
        params.put("arguments", arguments);

        McpRequest request = new McpRequest();
        request.setId("5");
        request.setMethod("tools/call");
        request.setParams(params);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertNull(response.getError(), "Error should be null");
    }

    @Test
    @DisplayName("Call tool endpoint should successfully invoke divide tool")
    void testCallDivideTool() throws Exception {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("a", 20.0);
        arguments.put("b", 4.0);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "divide");
        params.put("arguments", arguments);

        McpRequest request = new McpRequest();
        request.setId("6");
        request.setMethod("tools/call");
        request.setParams(params);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertNull(response.getError(), "Error should be null");
    }

    @Test
    @DisplayName("Call tool endpoint should return error for division by zero")
    void testCallDivideByZero() throws Exception {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("a", 10.0);
        arguments.put("b", 0.0);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "divide");
        params.put("arguments", arguments);

        McpRequest request = new McpRequest();
        request.setId("7");
        request.setMethod("tools/call");
        request.setParams(params);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertEquals("7", response.getId(), "Response ID should match request ID");
        assertNull(response.getResult(), "Result should be null for error");
        assertNotNull(response.getError(), "Error should not be null");
        assertEquals(McpError.INVALID_PARAMS, response.getError().getCode(), "Should return invalid params error code");
        assertTrue(response.getError().getMessage().contains("divide by zero"), 
                "Error message should mention division by zero");
    }

    @Test
    @DisplayName("Call tool endpoint should return error for unknown tool")
    void testCallUnknownTool() throws Exception {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("a", 5.0);
        arguments.put("b", 3.0);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "unknown");
        params.put("arguments", arguments);

        McpRequest request = new McpRequest();
        request.setId("8");
        request.setMethod("tools/call");
        request.setParams(params);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertEquals("8", response.getId(), "Response ID should match request ID");
        assertNull(response.getResult(), "Result should be null for error");
        assertNotNull(response.getError(), "Error should not be null");
        assertEquals(McpError.METHOD_NOT_FOUND, response.getError().getCode(), "Should return method not found error code");
        assertTrue(response.getError().getMessage().contains("not found"), 
                "Error message should mention tool not found");
    }

    @Test
    @DisplayName("Call tool endpoint should return error for missing parameters")
    void testCallToolMissingParams() throws Exception {
        McpRequest request = new McpRequest();
        request.setId("9");
        request.setMethod("tools/call");
        request.setParams(null);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertEquals("9", response.getId(), "Response ID should match request ID");
        assertNull(response.getResult(), "Result should be null for error");
        assertNotNull(response.getError(), "Error should not be null");
        assertEquals(McpError.INVALID_PARAMS, response.getError().getCode(), "Should return invalid params error code");
    }

    @Test
    @DisplayName("Call tool endpoint should return error for missing tool name")
    void testCallToolMissingToolName() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("arguments", new HashMap<>());

        McpRequest request = new McpRequest();
        request.setId("10");
        request.setMethod("tools/call");
        request.setParams(params);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertEquals("10", response.getId(), "Response ID should match request ID");
        assertNull(response.getResult(), "Result should be null for error");
        assertNotNull(response.getError(), "Error should not be null");
        assertEquals(McpError.INVALID_PARAMS, response.getError().getCode(), "Should return invalid params error code");
        assertTrue(response.getError().getMessage().contains("tool name"), 
                "Error message should mention missing tool name");
    }

    @Test
    @DisplayName("Call tool endpoint should return error for missing required arguments")
    void testCallToolMissingArguments() throws Exception {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("a", 5.0);
        // Missing parameter 'b'

        Map<String, Object> params = new HashMap<>();
        params.put("name", "add");
        params.put("arguments", arguments);

        McpRequest request = new McpRequest();
        request.setId("11");
        request.setMethod("tools/call");
        request.setParams(params);

        MvcResult result = mockMvc.perform(post("/mcp/tools/call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        McpResponse response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                McpResponse.class
        );

        assertNotNull(response, "Response should not be null");
        assertEquals("11", response.getId(), "Response ID should match request ID");
        assertNull(response.getResult(), "Result should be null for error");
        assertNotNull(response.getError(), "Error should not be null");
        assertEquals(McpError.INVALID_PARAMS, response.getError().getCode(), "Should return invalid params error code");
        assertTrue(response.getError().getMessage().contains("Missing required parameter"), 
                "Error message should mention missing parameter");
    }

    @Test
    @DisplayName("Should handle requests without explicit ID")
    void testRequestWithoutId() throws Exception {
        mockMvc.perform(post("/mcp/initialize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jsonrpc").value("2.0"))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @DisplayName("Should handle empty request body for list tools")
    void testListToolsWithoutBody() throws Exception {
        mockMvc.perform(post("/mcp/tools/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jsonrpc").value("2.0"))
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @DisplayName("JSON-RPC response should always include version 2.0")
    void testJsonRpcVersion() throws Exception {
        McpRequest request = new McpRequest();
        request.setId("test-id");
        request.setMethod("initialize");

        mockMvc.perform(post("/mcp/initialize")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jsonrpc").value("2.0"));
    }
}
