package com.example.calculator.mcp.service;

import com.example.calculator.CalculatorService;
import com.example.calculator.mcp.model.McpTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for McpToolService.
 * Tests tool discovery, invocation, parameter validation, and error handling.
 */
class McpToolServiceTest {

    private McpToolService mcpToolService;
    private CalculatorService calculatorService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
        objectMapper = new ObjectMapper();
        mcpToolService = new McpToolService(calculatorService, objectMapper);
    }

    @Test
    @DisplayName("Should return list of all available tools")
    void testGetTools() {
        List<McpTool> tools = mcpToolService.getTools();

        assertNotNull(tools, "Tools list should not be null");
        assertEquals(4, tools.size(), "Should have 4 calculator tools");

        // Verify tool names
        List<String> toolNames = tools.stream()
                .map(McpTool::getName)
                .toList();
        assertTrue(toolNames.contains("add"), "Should contain add tool");
        assertTrue(toolNames.contains("subtract"), "Should contain subtract tool");
        assertTrue(toolNames.contains("multiply"), "Should contain multiply tool");
        assertTrue(toolNames.contains("divide"), "Should contain divide tool");
    }

    @Test
    @DisplayName("Should return immutable tools list")
    void testGetToolsReturnsImmutableList() {
        List<McpTool> tools = mcpToolService.getTools();

        assertThrows(UnsupportedOperationException.class, () -> {
            tools.add(new McpTool("test", "test", null));
        }, "Tools list should be immutable");
    }

    @Test
    @DisplayName("Should have proper tool descriptions")
    void testToolDescriptions() {
        List<McpTool> tools = mcpToolService.getTools();

        McpTool addTool = tools.stream()
                .filter(t -> "add".equals(t.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(addTool, "Add tool should exist");
        assertNotNull(addTool.getDescription(), "Add tool should have description");
        assertTrue(addTool.getDescription().contains("Add"), "Description should mention addition");
        assertNotNull(addTool.getInputSchema(), "Add tool should have input schema");
    }

    @Test
    @DisplayName("Should have proper input schema for all tools")
    void testToolInputSchemas() {
        List<McpTool> tools = mcpToolService.getTools();

        for (McpTool tool : tools) {
            assertNotNull(tool.getInputSchema(), "Tool " + tool.getName() + " should have input schema");
            assertNotNull(tool.getInputSchema().getProperties(), "Tool " + tool.getName() + " should have properties");
            assertNotNull(tool.getInputSchema().getRequired(), "Tool " + tool.getName() + " should have required fields");
            assertEquals(2, tool.getInputSchema().getRequired().size(), "Tool " + tool.getName() + " should require 2 parameters");
            assertTrue(tool.getInputSchema().getRequired().contains("a"), "Tool should require parameter 'a'");
            assertTrue(tool.getInputSchema().getRequired().contains("b"), "Tool should require parameter 'b'");
        }
    }

    @Test
    @DisplayName("Should successfully invoke add tool")
    void testInvokeAddTool() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 5.0);
        params.put("b", 3.0);

        Object result = mcpToolService.invokeTool("add", params);

        assertNotNull(result, "Result should not be null");
        assertEquals(8.0, (Double) result, 0.0001, "Should correctly add 5.0 + 3.0");
    }

    @Test
    @DisplayName("Should successfully invoke subtract tool")
    void testInvokeSubtractTool() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 10.0);
        params.put("b", 4.0);

        Object result = mcpToolService.invokeTool("subtract", params);

        assertNotNull(result, "Result should not be null");
        assertEquals(6.0, (Double) result, 0.0001, "Should correctly subtract 10.0 - 4.0");
    }

    @Test
    @DisplayName("Should successfully invoke multiply tool")
    void testInvokeMultiplyTool() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 6.0);
        params.put("b", 7.0);

        Object result = mcpToolService.invokeTool("multiply", params);

        assertNotNull(result, "Result should not be null");
        assertEquals(42.0, (Double) result, 0.0001, "Should correctly multiply 6.0 * 7.0");
    }

    @Test
    @DisplayName("Should successfully invoke divide tool")
    void testInvokeDivideTool() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 20.0);
        params.put("b", 4.0);

        Object result = mcpToolService.invokeTool("divide", params);

        assertNotNull(result, "Result should not be null");
        assertEquals(5.0, (Double) result, 0.0001, "Should correctly divide 20.0 / 4.0");
    }

    @Test
    @DisplayName("Should handle integer parameters")
    void testInvokeWithIntegerParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 10);
        params.put("b", 2);

        Object result = mcpToolService.invokeTool("add", params);

        assertNotNull(result, "Result should not be null");
        assertEquals(12.0, (Double) result, 0.0001, "Should correctly handle integer parameters");
    }

    @Test
    @DisplayName("Should handle string number parameters")
    void testInvokeWithStringParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", "15.5");
        params.put("b", "2.5");

        Object result = mcpToolService.invokeTool("add", params);

        assertNotNull(result, "Result should not be null");
        assertEquals(18.0, (Double) result, 0.0001, "Should correctly parse string numbers");
    }

    @Test
    @DisplayName("Should handle negative numbers")
    void testInvokeWithNegativeNumbers() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", -5.0);
        params.put("b", 3.0);

        Object result = mcpToolService.invokeTool("add", params);

        assertNotNull(result, "Result should not be null");
        assertEquals(-2.0, (Double) result, 0.0001, "Should correctly handle negative numbers");
    }

    @Test
    @DisplayName("Should handle decimal numbers")
    void testInvokeWithDecimalNumbers() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 3.14);
        params.put("b", 2.86);

        Object result = mcpToolService.invokeTool("add", params);

        assertNotNull(result, "Result should not be null");
        assertEquals(6.0, (Double) result, 0.0001, "Should correctly handle decimal numbers");
    }

    @Test
    @DisplayName("Should throw exception for division by zero")
    void testDivideByZero() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 10.0);
        params.put("b", 0.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mcpToolService.invokeTool("divide", params);
        }, "Should throw exception for division by zero");

        assertTrue(exception.getMessage().contains("divide by zero"), 
                "Exception message should mention division by zero");
    }

    @Test
    @DisplayName("Should throw exception for unknown tool")
    void testInvokeUnknownTool() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 5.0);
        params.put("b", 3.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mcpToolService.invokeTool("unknown", params);
        }, "Should throw exception for unknown tool");

        assertTrue(exception.getMessage().contains("Unknown tool"), 
                "Exception message should mention unknown tool");
    }

    @Test
    @DisplayName("Should throw exception for null tool name")
    void testInvokeNullToolName() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 5.0);
        params.put("b", 3.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mcpToolService.invokeTool(null, params);
        }, "Should throw exception for null tool name");

        assertTrue(exception.getMessage().contains("cannot be null"), 
                "Exception message should mention null tool name");
    }

    @Test
    @DisplayName("Should throw exception for missing parameter 'a'")
    void testInvokeMissingParameterA() {
        Map<String, Object> params = new HashMap<>();
        params.put("b", 3.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mcpToolService.invokeTool("add", params);
        }, "Should throw exception for missing parameter 'a'");

        assertTrue(exception.getMessage().contains("Missing required parameter: a"), 
                "Exception message should mention missing parameter 'a'");
    }

    @Test
    @DisplayName("Should throw exception for missing parameter 'b'")
    void testInvokeMissingParameterB() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 5.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mcpToolService.invokeTool("add", params);
        }, "Should throw exception for missing parameter 'b'");

        assertTrue(exception.getMessage().contains("Missing required parameter: b"), 
                "Exception message should mention missing parameter 'b'");
    }

    @Test
    @DisplayName("Should throw exception for null parameters map")
    void testInvokeNullParameters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mcpToolService.invokeTool("add", null);
        }, "Should throw exception for null parameters");

        assertTrue(exception.getMessage().contains("Missing required parameter"), 
                "Exception message should mention missing parameters");
    }

    @Test
    @DisplayName("Should throw exception for invalid string parameter")
    void testInvokeInvalidStringParameter() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", "not-a-number");
        params.put("b", 3.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mcpToolService.invokeTool("add", params);
        }, "Should throw exception for invalid string parameter");

        assertTrue(exception.getMessage().contains("Invalid number format"), 
                "Exception message should mention invalid number format");
    }

    @Test
    @DisplayName("Should throw exception for invalid parameter type")
    void testInvokeInvalidParameterType() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", new Object());
        params.put("b", 3.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mcpToolService.invokeTool("add", params);
        }, "Should throw exception for invalid parameter type");

        assertTrue(exception.getMessage().contains("Invalid parameter type"), 
                "Exception message should mention invalid parameter type");
    }

    @Test
    @DisplayName("Should correctly identify existing tools")
    void testToolExists() {
        assertTrue(mcpToolService.toolExists("add"), "add tool should exist");
        assertTrue(mcpToolService.toolExists("subtract"), "subtract tool should exist");
        assertTrue(mcpToolService.toolExists("multiply"), "multiply tool should exist");
        assertTrue(mcpToolService.toolExists("divide"), "divide tool should exist");
    }

    @Test
    @DisplayName("Should correctly identify non-existing tools")
    void testToolDoesNotExist() {
        assertFalse(mcpToolService.toolExists("unknown"), "unknown tool should not exist");
        assertFalse(mcpToolService.toolExists("power"), "power tool should not exist");
        assertFalse(mcpToolService.toolExists(""), "empty tool name should not exist");
    }

    @Test
    @DisplayName("Should handle null in toolExists")
    void testToolExistsWithNull() {
        assertFalse(mcpToolService.toolExists(null), "null tool should not exist");
    }
}
