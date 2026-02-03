package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.SupportResponse;
import com.example.customersupport.model.ConversationContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CustomerSupportAgentTest {
    
    private CustomerSupportAgent agent;
    private ConversationContext context;
    
    @BeforeEach
    void setUp() {
        agent = new CustomerSupportAgent(AIParadigm.HYBRID);
        context = new ConversationContext("S001", "C001");
    }
    
    @Test
    void testConstructor() {
        assertNotNull(agent);
        assertEquals(AIParadigm.HYBRID, agent.getParadigm());
        assertNotNull(agent.getPerceptionMechanism());
        assertNotNull(agent.getReasoningEngine());
        assertNotNull(agent.getPlanningEngine());
        assertNotNull(agent.getEthicsValidator());
    }
    
    @Test
    void testConstructorWithRuleBased() {
        CustomerSupportAgent ruleBasedAgent = new CustomerSupportAgent(AIParadigm.RULE_BASED);
        assertEquals(AIParadigm.RULE_BASED, ruleBasedAgent.getParadigm());
    }
    
    @Test
    void testConstructorWithLearningBased() {
        CustomerSupportAgent learningAgent = new CustomerSupportAgent(AIParadigm.LEARNING_BASED);
        assertEquals(AIParadigm.LEARNING_BASED, learningAgent.getParadigm());
    }
    
    @Test
    void testProcessSimpleQuery() {
        CustomerQuery query = new CustomerQuery("Q001", "C001", "What are your business hours?");
        
        SupportResponse response = agent.processQuery(query, context);
        
        assertNotNull(response);
        assertNotNull(response.getResponseId());
        assertEquals("Q001", response.getQueryId());
        assertNotNull(response.getResponseText());
        assertTrue(response.getConfidenceLevel() >= 0.0 && response.getConfidenceLevel() <= 1.0);
    }
    
    @Test
    void testProcessHelpRequest() {
        CustomerQuery query = new CustomerQuery("Q002", "C001", "How to reset my password?");
        
        SupportResponse response = agent.processQuery(query, context);
        
        assertNotNull(response);
        assertTrue(response.getResponseText().contains("help") || 
                   response.getResponseText().contains("guidance"));
    }
    
    @Test
    void testProcessTechnicalIssue() {
        CustomerQuery query = new CustomerQuery("Q003", "C001", "My application is not working");
        
        SupportResponse response = agent.processQuery(query, context);
        
        assertNotNull(response);
        assertTrue(response.isRequiresEscalation(), "Technical issues should be escalated");
        assertNotNull(response.getEscalationReason());
    }
    
    @Test
    void testProcessRefundRequest() {
        CustomerQuery query = new CustomerQuery("Q004", "C001", "I want a refund");
        
        SupportResponse response = agent.processQuery(query, context);
        
        assertNotNull(response);
        assertTrue(response.isRequiresEscalation(), "Refund requests should be escalated");
        assertTrue(response.getResponseText().contains("specialist") || 
                   response.getResponseText().contains("connect"));
    }
    
    @Test
    void testProcessUrgentQuery() {
        CustomerQuery query = new CustomerQuery("Q005", "C001", "URGENT! Need help immediately!");
        
        SupportResponse response = agent.processQuery(query, context);
        
        assertNotNull(response);
        assertTrue(response.isRequiresEscalation(), "Urgent queries should be escalated");
    }
    
    @Test
    void testContextUpdated() {
        CustomerQuery query = new CustomerQuery("Q006", "C001", "Test query");
        
        assertEquals(0, context.getInteractionCount());
        
        agent.processQuery(query, context);
        
        assertEquals(1, context.getInteractionCount());
        assertNotNull(context.getLastQuery());
        assertNotNull(context.getLastResponse());
        assertNotNull(context.getContextValue("lastIntent"));
        assertNotNull(context.getContextValue("lastSentiment"));
    }
    
    @Test
    void testMultipleQueries() {
        CustomerQuery query1 = new CustomerQuery("Q007", "C001", "First query");
        CustomerQuery query2 = new CustomerQuery("Q008", "C001", "Second query");
        
        agent.processQuery(query1, context);
        agent.processQuery(query2, context);
        
        assertEquals(2, context.getInteractionCount());
        assertEquals(2, context.getQueryHistory().size());
        assertEquals(2, context.getResponseHistory().size());
    }
    
    @Test
    void testInvalidQueryThrowsException() {
        CustomerQuery invalidQuery = new CustomerQuery("Q009", null, "Test");
        
        assertThrows(IllegalArgumentException.class, () -> {
            agent.processQuery(invalidQuery, context);
        });
    }
    
    @Test
    void testResponseConfidenceLevels() {
        // Simple query should have higher confidence
        CustomerQuery simpleQuery = new CustomerQuery("Q010", "C001", "What are your hours?");
        SupportResponse simpleResponse = agent.processQuery(simpleQuery, context);
        
        // Complex urgent query should have lower confidence
        context = new ConversationContext("S002", "C002");
        CustomerQuery urgentQuery = new CustomerQuery("Q011", "C002", "URGENT! Critical issue!");
        SupportResponse urgentResponse = agent.processQuery(urgentQuery, context);
        
        assertTrue(simpleResponse.getConfidenceLevel() >= urgentResponse.getConfidenceLevel(),
                  "Simple queries should have higher or equal confidence than urgent ones");
    }
    
    @Test
    void testBillingInquiry() {
        CustomerQuery query = new CustomerQuery("Q012", "C001", "Why was I charged?");
        
        SupportResponse response = agent.processQuery(query, context);
        
        assertNotNull(response);
        assertTrue(response.getResponseText().contains("billing") || 
                   response.getResponseText().contains("charges"));
    }
}
