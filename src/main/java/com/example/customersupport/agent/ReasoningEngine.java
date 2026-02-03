package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.SupportResponse;
import com.example.customersupport.model.ConversationContext;

/**
 * Reasoning engine that determines the best response or escalation strategy.
 * Uses perception results and context to make informed decisions.
 */
public class ReasoningEngine {
    
    private static final double ESCALATION_THRESHOLD = 0.7;
    private static final double LOW_CONFIDENCE_THRESHOLD = 0.5;
    
    /**
     * Determines if a query should be escalated based on various factors.
     */
    public boolean shouldEscalate(
            CustomerQuery query,
            PerceptionMechanism.PerceptionResult perception,
            ConversationContext context) {
        
        // Escalate if urgency is high
        if (perception.getUrgency() >= ESCALATION_THRESHOLD) {
            return true;
        }
        
        // Escalate if sentiment is very negative and it's not the first interaction
        if ("NEGATIVE".equals(perception.getSentiment()) && context.getInteractionCount() > 2) {
            return true;
        }
        
        // Escalate for specific intents
        if ("REFUND_REQUEST".equals(perception.getIntent()) || 
            "TECHNICAL_ISSUE".equals(perception.getIntent())) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Determines the appropriate response strategy based on the query and context.
     */
    public String determineResponseStrategy(
            CustomerQuery query,
            PerceptionMechanism.PerceptionResult perception,
            ConversationContext context) {
        
        String intent = perception.getIntent();
        
        switch (intent) {
            case "HELP_REQUEST":
                return "PROVIDE_GUIDANCE";
            case "BILLING_INQUIRY":
                return "EXPLAIN_BILLING";
            case "TECHNICAL_ISSUE":
                return "TROUBLESHOOT";
            case "REFUND_REQUEST":
                return "ESCALATE_TO_HUMAN";
            default:
                return "GENERAL_ASSISTANCE";
        }
    }
    
    /**
     * Calculates confidence level for a proposed response.
     */
    public double calculateConfidence(
            String responseStrategy,
            PerceptionMechanism.PerceptionResult perception) {
        
        double baseConfidence = 0.8;
        
        // Reduce confidence for complex or urgent queries
        if (perception.getUrgency() > 0.7) {
            baseConfidence -= 0.2;
        }
        
        // Reduce confidence for negative sentiment
        if ("NEGATIVE".equals(perception.getSentiment())) {
            baseConfidence -= 0.15;
        }
        
        // Increase confidence for simple queries
        if ("GENERAL_INQUIRY".equals(perception.getIntent())) {
            baseConfidence += 0.1;
        }
        
        return Math.max(0.0, Math.min(1.0, baseConfidence));
    }
}
