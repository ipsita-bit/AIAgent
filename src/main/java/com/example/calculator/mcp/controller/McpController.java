package com.example.calculator.mcp.controller;

import com.example.calculator.mcp.model.*;
import com.example.calculator.mcp.service.McpToolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MCP Server Controller implementing Model Context Protocol endpoints.
 * Provides JSON-RPC 2.0 interface for AI models to discover and invoke calculator tools.
 */
@RestController
@RequestMapping("/mcp")
public class McpController {

    private final McpToolService mcpToolService;
    private final ObjectMapper objectMapper;

    public McpController(McpToolService mcpToolService, ObjectMapper objectMapper) {
        this.mcpToolService = mcpToolService;
        this.objectMapper = objectMapper;
    }

    /**
     * List all available tools.
     * MCP method: tools/list
     *
     * @return Response containing list of available tools
     */
    @PostMapping("/tools/list")
    public ResponseEntity<McpResponse> listTools(@RequestBody(required = false) McpRequest request) {
        try {
            List<McpTool> tools = mcpToolService.getTools();
            Map<String, Object> result = new HashMap<>();
            result.put("tools", tools);

            Object requestId = (request != null && request.getId() != null) ? request.getId() : "1";
            return ResponseEntity.ok(new McpResponse(requestId, result));
        } catch (Exception e) {
            Object requestId = (request != null && request.getId() != null) ? request.getId() : "1";
            McpError error = new McpError(McpError.INTERNAL_ERROR, "Failed to list tools: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new McpResponse(requestId, error));
        }
    }

    /**
     * Invoke a specific tool.
     * MCP method: tools/call
     *
     * @param request JSON-RPC request containing tool name and parameters
     * @return Response containing tool execution result
     */
    @PostMapping("/tools/call")
    public ResponseEntity<McpResponse> callTool(@RequestBody McpRequest request) {
        try {
            // Validate request
            if (request.getParams() == null) {
                McpError error = new McpError(McpError.INVALID_PARAMS, "Missing parameters");
                return ResponseEntity.badRequest().body(new McpResponse(request.getId(), error));
            }

            // Extract tool name and arguments
            Map<String, Object> params = objectMapper.convertValue(request.getParams(), Map.class);
            String toolName = (String) params.get("name");
            Map<String, Object> arguments = (Map<String, Object>) params.get("arguments");

            if (toolName == null || toolName.isEmpty()) {
                McpError error = new McpError(McpError.INVALID_PARAMS, "Missing tool name");
                return ResponseEntity.badRequest().body(new McpResponse(request.getId(), error));
            }

            // Check if tool exists
            if (!mcpToolService.toolExists(toolName)) {
                McpError error = new McpError(McpError.METHOD_NOT_FOUND, "Tool not found: " + toolName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new McpResponse(request.getId(), error));
            }

            // Invoke the tool
            Object result = mcpToolService.invokeTool(toolName, arguments != null ? arguments : new HashMap<>());

            Map<String, Object> responseContent = new HashMap<>();
            responseContent.put("content", List.of(Map.of(
                    "type", "text",
                    "text", String.valueOf(result)
            )));

            return ResponseEntity.ok(new McpResponse(request.getId(), responseContent));

        } catch (IllegalArgumentException e) {
            McpError error = new McpError(McpError.INVALID_PARAMS, e.getMessage());
            return ResponseEntity.badRequest().body(new McpResponse(request.getId(), error));
        } catch (Exception e) {
            McpError error = new McpError(McpError.INTERNAL_ERROR, "Tool invocation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new McpResponse(request.getId(), error));
        }
    }

    /**
     * Get server information.
     * MCP method: initialize
     *
     * @return Server information including protocol version and capabilities
     */
    @PostMapping("/initialize")
    public ResponseEntity<McpResponse> initialize(@RequestBody(required = false) McpRequest request) {
        try {
            Map<String, Object> serverInfo = new HashMap<>();
            serverInfo.put("protocolVersion", "2024-11-05");
            serverInfo.put("serverInfo", Map.of(
                    "name", "Calculator MCP Server",
                    "version", "1.0.0"
            ));
            serverInfo.put("capabilities", Map.of(
                    "tools", Map.of()
            ));

            Object requestId = (request != null && request.getId() != null) ? request.getId() : "1";
            return ResponseEntity.ok(new McpResponse(requestId, serverInfo));
        } catch (Exception e) {
            Object requestId = (request != null && request.getId() != null) ? request.getId() : "1";
            McpError error = new McpError(McpError.INTERNAL_ERROR, "Initialization failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new McpResponse(requestId, error));
        }
    }

    /**
     * Health check endpoint for the MCP server.
     *
     * @return Server status
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("server", "Calculator MCP Server");
        status.put("version", "1.0.0");
        return ResponseEntity.ok(status);
    }
}
