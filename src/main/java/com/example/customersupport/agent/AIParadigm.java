package com.example.customersupport.agent;

/**
 * Defines the AI paradigm for the customer support agent.
 * 
 * RULE_BASED: Uses predefined rules and decision trees
 * LEARNING_BASED: Uses machine learning models for decision making
 * HYBRID: Combines both rule-based and learning-based approaches
 */
public enum AIParadigm {
    RULE_BASED("Uses predefined rules and patterns"),
    LEARNING_BASED("Uses machine learning for adaptive responses"),
    HYBRID("Combines rule-based and learning-based approaches");
    
    private final String description;
    
    AIParadigm(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
