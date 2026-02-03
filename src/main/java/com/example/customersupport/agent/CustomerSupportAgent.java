package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.SupportResponse;
import com.example.customersupport.model.ConversationContext;
import com.example.customersupport.ethics.EthicsValidator;
import java.util.UUID;

/**
 * Main AI customer support agent implementing a hybrid paradigm.
 * Combines rule-based perception with learning-based reasoning.
 */
public class CustomerSupportAgent {
    
    private final AIParadigm paradigm;
    private final PerceptionMechanism perceptionMechanism;
    private final ReasoningEngine reasoningEngine;
    private final PlanningEngine planningEngine;
    private final EthicsValidator ethicsValidator;
    
    public CustomerSupportAgent(AIParadigm paradigm) {
        this.paradigm = paradigm;
        this.perceptionMechanism = new RuleBasedPerception();
        this.reasoningEngine = new ReasoningEngine();
        this.planningEngine = new PlanningEngine();
        this.ethicsValidator = new EthicsValidator();
    }
    
    /**
     * Processes a customer query and generates an appropriate response.
     */
    public SupportResponse processQuery(CustomerQuery query, ConversationContext context) {
        // Validate query for fairness
        if (!ethicsValidator.ensureFairness(query)) {
            throw new IllegalArgumentException("Query does not meet fairness criteria");
        }
        
        // Perception: Understand the query
        PerceptionMechanism.PerceptionResult perception = perceptionMechanism.analyze(query);
        
        // Reasoning: Determine response strategy
        boolean shouldEscalate = reasoningEngine.shouldEscalate(query, perception, context);
        String responseStrategy = reasoningEngine.determineResponseStrategy(query, perception, context);
        double confidence = reasoningEngine.calculateConfidence(responseStrategy, perception);
        
        // Planning: Plan actions
        var actions = planningEngine.planActions(query, perception, context);
        
        // Generate response
        String responseId = UUID.randomUUID().toString();
        String responseText = generateResponseText(responseStrategy, perception, query);
        
        SupportResponse response = new SupportResponse(responseId, query.getQueryId(), responseText);
        response.setConfidenceLevel(confidence);
        response.setRequiresEscalation(shouldEscalate);
        
        if (shouldEscalate) {
            response.setEscalationReason(
                String.format("Urgency: %.2f, Intent: %s", perception.getUrgency(), perception.getIntent())
            );
        }
        
        // Validate ethics
        EthicsValidator.ValidationResult validation = ethicsValidator.validate(response, query);
        if (!validation.isValid()) {
            // Log validation issues but still return response with warning
            System.err.println("Ethics validation issues: " + validation.getIssues());
        }
        
        // Update context
        context.addQuery(query);
        context.addResponse(response);
        context.setContextValue("lastIntent", perception.getIntent());
        context.setContextValue("lastSentiment", perception.getSentiment());
        
        return response;
    }
    
    /**
     * Generates response text based on strategy and perception.
     */
    private String generateResponseText(
            String strategy,
            PerceptionMechanism.PerceptionResult perception,
            CustomerQuery query) {
        
        switch (strategy) {
            case "PROVIDE_GUIDANCE":
                return "I'd be happy to help you with that. Let me provide you with step-by-step guidance.";
            case "EXPLAIN_BILLING":
                return "I understand you have a billing inquiry. Let me explain the charges on your account.";
            case "TROUBLESHOOT":
                return "I see you're experiencing a technical issue. Let's work together to resolve this.";
            case "ESCALATE_TO_HUMAN":
                return "I understand your concern. Let me connect you with a specialist who can better assist you.";
            default:
                return "Thank you for contacting support. I'm here to help you with your inquiry.";
        }
    }
    
    public AIParadigm getParadigm() {
        return paradigm;
    }
    
    public PerceptionMechanism getPerceptionMechanism() {
        return perceptionMechanism;
    }
    
    public ReasoningEngine getReasoningEngine() {
        return reasoningEngine;
    }
    
    public PlanningEngine getPlanningEngine() {
        return planningEngine;
    }
    
    public EthicsValidator getEthicsValidator() {
        return ethicsValidator;
    }
}
