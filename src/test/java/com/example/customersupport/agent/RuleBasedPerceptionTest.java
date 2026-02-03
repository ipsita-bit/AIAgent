package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class RuleBasedPerceptionTest {
    
    private RuleBasedPerception perception;
    
    @BeforeEach
    void setUp() {
        perception = new RuleBasedPerception();
    }
    
    @Test
    void testAnalyzeHelpRequest() {
        CustomerQuery query = new CustomerQuery("Q001", "C001", "How to reset my password?");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertNotNull(result);
        assertEquals("HELP_REQUEST", result.getIntent());
        assertNotNull(result.getSentiment());
        assertTrue(result.getUrgency() >= 0.0);
        assertNotNull(result.getKeywords());
    }
    
    @Test
    void testAnalyzeTechnicalIssue() {
        CustomerQuery query = new CustomerQuery("Q002", "C001", "My application is not working properly");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertEquals("TECHNICAL_ISSUE", result.getIntent());
    }
    
    @Test
    void testAnalyzeRefundRequest() {
        CustomerQuery query = new CustomerQuery("Q003", "C001", "I want a refund for my purchase");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertEquals("REFUND_REQUEST", result.getIntent());
    }
    
    @Test
    void testAnalyzeBillingInquiry() {
        CustomerQuery query = new CustomerQuery("Q004", "C001", "Why was I charged twice?");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertEquals("BILLING_INQUIRY", result.getIntent());
    }
    
    @Test
    void testAnalyzeGeneralInquiry() {
        CustomerQuery query = new CustomerQuery("Q005", "C001", "What are your business hours?");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertEquals("GENERAL_INQUIRY", result.getIntent());
    }
    
    @Test
    void testNegativeSentiment() {
        CustomerQuery query = new CustomerQuery("Q006", "C001", "I am very unhappy and frustrated with the service");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertEquals("NEGATIVE", result.getSentiment());
    }
    
    @Test
    void testPositiveSentiment() {
        CustomerQuery query = new CustomerQuery("Q007", "C001", "Thank you for the excellent support!");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertEquals("POSITIVE", result.getSentiment());
    }
    
    @Test
    void testNeutralSentiment() {
        CustomerQuery query = new CustomerQuery("Q008", "C001", "What is the status of my order?");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertEquals("NEUTRAL", result.getSentiment());
    }
    
    @Test
    void testHighUrgency() {
        CustomerQuery query = new CustomerQuery("Q009", "C001", "URGENT! Need help immediately!");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertTrue(result.getUrgency() > 0.5, "Urgency should be high for urgent keywords");
    }
    
    @Test
    void testLowUrgency() {
        CustomerQuery query = new CustomerQuery("Q010", "C001", "What are your business hours?");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertTrue(result.getUrgency() <= 0.5, "Urgency should be low for normal queries");
    }
    
    @Test
    void testKeywordExtraction() {
        CustomerQuery query = new CustomerQuery("Q011", "C001", "password reset authentication problem");
        PerceptionMechanism.PerceptionResult result = perception.analyze(query);
        
        assertTrue(result.getKeywords().length > 0);
    }
    
    @Test
    void testPerceptionResultGetters() {
        PerceptionMechanism.PerceptionResult result = new PerceptionMechanism.PerceptionResult(
            "TEST_INTENT", "POSITIVE", 0.75, new String[]{"test", "keyword"}
        );
        
        assertEquals("TEST_INTENT", result.getIntent());
        assertEquals("POSITIVE", result.getSentiment());
        assertEquals(0.75, result.getUrgency(), 0.001);
        assertArrayEquals(new String[]{"test", "keyword"}, result.getKeywords());
    }
}
