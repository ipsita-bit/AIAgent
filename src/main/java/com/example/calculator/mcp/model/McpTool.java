package com.example.calculator.mcp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * MCP Tool definition following Model Context Protocol specification.
 * Describes a tool that can be invoked by AI models.
 */
public class McpTool {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("inputSchema")
    private InputSchema inputSchema;

    public McpTool() {
    }

    public McpTool(String name, String description, InputSchema inputSchema) {
        this.name = name;
        this.description = description;
        this.inputSchema = inputSchema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public InputSchema getInputSchema() {
        return inputSchema;
    }

    public void setInputSchema(InputSchema inputSchema) {
        this.inputSchema = inputSchema;
    }

    /**
     * JSON Schema for tool input parameters.
     */
    public static class InputSchema {
        @JsonProperty("type")
        private String type = "object";

        @JsonProperty("properties")
        private Object properties;

        @JsonProperty("required")
        private List<String> required;

        public InputSchema() {
        }

        public InputSchema(Object properties, List<String> required) {
            this.properties = properties;
            this.required = required;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getProperties() {
            return properties;
        }

        public void setProperties(Object properties) {
            this.properties = properties;
        }

        public List<String> getRequired() {
            return required;
        }

        public void setRequired(List<String> required) {
            this.required = required;
        }
    }
}
