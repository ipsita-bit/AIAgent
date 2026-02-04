package com.example.calculator.mcp.service;

import com.example.calculator.CalculatorService;
import com.example.calculator.mcp.model.McpError;
import com.example.calculator.mcp.model.McpTool;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * MCP Tool Service implementing calculator operations as MCP tools.
 * Provides tool discovery and invocation following the Model Context Protocol specification.
 */
@Service
public class McpToolService {

    private final CalculatorService calculatorService;
    private final ObjectMapper objectMapper;
    private final List<McpTool> tools;

    public McpToolService(CalculatorService calculatorService, ObjectMapper objectMapper) {
        this.calculatorService = calculatorService;
        this.objectMapper = objectMapper;
        this.tools = initializeTools();
    }

    /**
     * Initialize the list of available MCP tools.
     *
     * @return List of MCP tool definitions
     */
    private List<McpTool> initializeTools() {
        List<McpTool> toolList = new ArrayList<>();

        // Add tool
        toolList.add(createTool(
                "add",
                "Add two numbers together and return the sum",
                Arrays.asList("a", "b"),
                "First number", "Second number"
        ));

        // Subtract tool
        toolList.add(createTool(
                "subtract",
                "Subtract the second number from the first number",
                Arrays.asList("a", "b"),
                "Number to subtract from", "Number to subtract"
        ));

        // Multiply tool
        toolList.add(createTool(
                "multiply",
                "Multiply two numbers together and return the product",
                Arrays.asList("a", "b"),
                "First number", "Second number"
        ));

        // Divide tool
        toolList.add(createTool(
                "divide",
                "Divide the first number by the second number. Returns an error if dividing by zero.",
                Arrays.asList("a", "b"),
                "Dividend (number to be divided)", "Divisor (number to divide by)"
        ));

        return toolList;
    }

    /**
     * Helper method to create an MCP tool definition.
     */
    private McpTool createTool(String name, String description, List<String> paramNames,
                               String... paramDescriptions) {
        Map<String, Object> properties = new LinkedHashMap<>();
        for (int i = 0; i < paramNames.size(); i++) {
            Map<String, String> paramSchema = new HashMap<>();
            paramSchema.put("type", "number");
            paramSchema.put("description", i < paramDescriptions.length ? paramDescriptions[i] : paramNames.get(i));
            properties.put(paramNames.get(i), paramSchema);
        }

        McpTool.InputSchema inputSchema = new McpTool.InputSchema(properties, paramNames);
        return new McpTool(name, description, inputSchema);
    }

    /**
     * Get all available tools.
     *
     * @return List of available MCP tools
     */
    public List<McpTool> getTools() {
        return Collections.unmodifiableList(tools);
    }

    /**
     * Invoke a tool with the given parameters.
     *
     * @param toolName   Name of the tool to invoke
     * @param parameters Parameters for the tool invocation
     * @return Result of the tool invocation
     * @throws IllegalArgumentException if the tool is not found or parameters are invalid
     */
    public Object invokeTool(String toolName, Map<String, Object> parameters) {
        if (toolName == null) {
            throw new IllegalArgumentException("Tool name cannot be null");
        }

        // Extract parameters
        double a = getDoubleParameter(parameters, "a");
        double b = getDoubleParameter(parameters, "b");

        // Invoke the appropriate tool
        return switch (toolName) {
            case "add" -> calculatorService.add(a, b);
            case "subtract" -> calculatorService.subtract(a, b);
            case "multiply" -> calculatorService.multiply(a, b);
            case "divide" -> calculatorService.divide(a, b);
            default -> throw new IllegalArgumentException("Unknown tool: " + toolName);
        };
    }

    /**
     * Extract a double parameter from the parameters map.
     */
    private double getDoubleParameter(Map<String, Object> parameters, String paramName) {
        if (parameters == null || !parameters.containsKey(paramName)) {
            throw new IllegalArgumentException("Missing required parameter: " + paramName);
        }

        Object value = parameters.get(paramName);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        } else if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format for parameter: " + paramName);
            }
        } else {
            throw new IllegalArgumentException("Invalid parameter type for: " + paramName);
        }
    }

    /**
     * Check if a tool exists.
     *
     * @param toolName Name of the tool
     * @return true if the tool exists, false otherwise
     */
    public boolean toolExists(String toolName) {
        if (toolName == null) {
            return false;
        }
        return tools.stream().anyMatch(tool -> tool.getName().equals(toolName));
    }
}
