package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.ConversationContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class ReasoningEngineTest {
    
    private ReasoningEngine engine;
    private ConversationContext context;
    
    @BeforeEach
    void setUp() {
        engine = new ReasoningEngine();
        context = new ConversationContext("S001", "C001");
    }
    
    @Test
    void testShouldEscalateHighUrgency() {
        CustomerQuery query = new CustomerQuery("Q001", "C001", "Urgent issue");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEUTRAL", 0.8, new String[]{});
        
        boolean shouldEscalate = engine.shouldEscalate(query, perception, context);
        assertTrue(shouldEscalate, "Should escalate for high urgency");
    }
    
    @Test
    void testShouldEscalateNegativeSentiment() {
        CustomerQuery query = new CustomerQuery("Q002", "C001", "I'm frustrated");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEGATIVE", 0.3, new String[]{});
        
        // Add multiple interactions
        context.addQuery(new CustomerQuery("Q000", "C001", "First query"));
        context.addQuery(new CustomerQuery("Q001", "C001", "Second query"));
        context.addQuery(query);
        
        boolean shouldEscalate = engine.shouldEscalate(query, perception, context);
        assertTrue(shouldEscalate, "Should escalate for negative sentiment with multiple interactions");
    }
    
    @Test
    void testShouldEscalateRefundRequest() {
        CustomerQuery query = new CustomerQuery("Q003", "C001", "I want a refund");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("REFUND_REQUEST", "NEUTRAL", 0.5, new String[]{});
        
        boolean shouldEscalate = engine.shouldEscalate(query, perception, context);
        assertTrue(shouldEscalate, "Should escalate refund requests");
    }
    
    @Test
    void testShouldEscalateTechnicalIssue() {
        CustomerQuery query = new CustomerQuery("Q004", "C001", "Technical problem");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("TECHNICAL_ISSUE", "NEUTRAL", 0.3, new String[]{});
        
        boolean shouldEscalate = engine.shouldEscalate(query, perception, context);
        assertTrue(shouldEscalate, "Should escalate technical issues");
    }
    
    @Test
    void testShouldNotEscalateSimpleQuery() {
        CustomerQuery query = new CustomerQuery("Q005", "C001", "What are your hours?");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEUTRAL", 0.3, new String[]{});
        
        boolean shouldEscalate = engine.shouldEscalate(query, perception, context);
        assertFalse(shouldEscalate, "Should not escalate simple queries");
    }
    
    @Test
    void testDetermineResponseStrategyHelpRequest() {
        CustomerQuery query = new CustomerQuery("Q006", "C001", "How to...?");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("HELP_REQUEST", "NEUTRAL", 0.3, new String[]{});
        
        String strategy = engine.determineResponseStrategy(query, perception, context);
        assertEquals("PROVIDE_GUIDANCE", strategy);
    }
    
    @Test
    void testDetermineResponseStrategyBilling() {
        CustomerQuery query = new CustomerQuery("Q007", "C001", "Billing question");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("BILLING_INQUIRY", "NEUTRAL", 0.3, new String[]{});
        
        String strategy = engine.determineResponseStrategy(query, perception, context);
        assertEquals("EXPLAIN_BILLING", strategy);
    }
    
    @Test
    void testDetermineResponseStrategyTechnical() {
        CustomerQuery query = new CustomerQuery("Q008", "C001", "Technical issue");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("TECHNICAL_ISSUE", "NEUTRAL", 0.3, new String[]{});
        
        String strategy = engine.determineResponseStrategy(query, perception, context);
        assertEquals("TROUBLESHOOT", strategy);
    }
    
    @Test
    void testDetermineResponseStrategyRefund() {
        CustomerQuery query = new CustomerQuery("Q009", "C001", "Refund request");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("REFUND_REQUEST", "NEUTRAL", 0.3, new String[]{});
        
        String strategy = engine.determineResponseStrategy(query, perception, context);
        assertEquals("ESCALATE_TO_HUMAN", strategy);
    }
    
    @Test
    void testDetermineResponseStrategyGeneral() {
        CustomerQuery query = new CustomerQuery("Q010", "C001", "General question");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEUTRAL", 0.3, new String[]{});
        
        String strategy = engine.determineResponseStrategy(query, perception, context);
        assertEquals("GENERAL_ASSISTANCE", strategy);
    }
    
    @Test
    void testCalculateConfidenceHighUrgency() {
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEUTRAL", 0.8, new String[]{});
        
        double confidence = engine.calculateConfidence("GENERAL_ASSISTANCE", perception);
        assertTrue(confidence < 0.8, "Confidence should be reduced for high urgency");
    }
    
    @Test
    void testCalculateConfidenceNegativeSentiment() {
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEGATIVE", 0.3, new String[]{});
        
        double confidence = engine.calculateConfidence("GENERAL_ASSISTANCE", perception);
        assertTrue(confidence < 0.8, "Confidence should be reduced for negative sentiment");
    }
    
    @Test
    void testCalculateConfidenceSimpleQuery() {
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEUTRAL", 0.3, new String[]{});
        
        double confidence = engine.calculateConfidence("GENERAL_ASSISTANCE", perception);
        assertTrue(confidence >= 0.8, "Confidence should be high for simple queries");
    }
    
    @Test
    void testCalculateConfidenceRange() {
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEGATIVE", 0.9, new String[]{});
        
        double confidence = engine.calculateConfidence("GENERAL_ASSISTANCE", perception);
        assertTrue(confidence >= 0.0 && confidence <= 1.0, "Confidence should be in range [0, 1]");
    }
}
