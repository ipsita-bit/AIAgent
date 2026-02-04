package com.example.calculator.mcp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON-RPC 2.0 Response object for MCP protocol.
 * Represents a successful response from the MCP server.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class McpResponse {

    @JsonProperty("jsonrpc")
    private String jsonrpc = "2.0";

    @JsonProperty("id")
    private Object id;

    @JsonProperty("result")
    private Object result;

    @JsonProperty("error")
    private McpError error;

    public McpResponse() {
    }

    public McpResponse(Object id, Object result) {
        this.id = id;
        this.result = result;
    }

    public McpResponse(Object id, McpError error) {
        this.id = id;
        this.error = error;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public McpError getError() {
        return error;
    }

    public void setError(McpError error) {
        this.error = error;
    }
}
