package com.example.customersupport.service;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.SupportResponse;
import com.example.customersupport.model.ConversationContext;
import com.example.customersupport.agent.AIParadigm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CustomerSupportServiceTest {
    
    private CustomerSupportService service;
    
    @BeforeEach
    void setUp() {
        service = new CustomerSupportService();
    }
    
    @Test
    void testDefaultConstructor() {
        assertNotNull(service);
        assertEquals(AIParadigm.HYBRID, service.getParadigm());
    }
    
    @Test
    void testConstructorWithParadigm() {
        CustomerSupportService ruleBasedService = new CustomerSupportService(AIParadigm.RULE_BASED);
        assertEquals(AIParadigm.RULE_BASED, ruleBasedService.getParadigm());
        
        CustomerSupportService learningService = new CustomerSupportService(AIParadigm.LEARNING_BASED);
        assertEquals(AIParadigm.LEARNING_BASED, learningService.getParadigm());
    }
    
    @Test
    void testHandleQuery() {
        CustomerQuery query = new CustomerQuery("Q001", "C001", "Test query");
        
        SupportResponse response = service.handleQuery(query);
        
        assertNotNull(response);
        assertNotNull(response.getResponseId());
        assertEquals("Q001", response.getQueryId());
        assertNotNull(response.getResponseText());
    }
    
    @Test
    void testContextCreation() {
        CustomerQuery query = new CustomerQuery("Q002", "C001", "First query");
        
        assertEquals(0, service.getActiveConversationCount());
        
        service.handleQuery(query);
        
        assertEquals(1, service.getActiveConversationCount());
        
        ConversationContext context = service.getContext("C001");
        assertNotNull(context);
        assertEquals("C001", context.getCustomerId());
    }
    
    @Test
    void testContextReuse() {
        CustomerQuery query1 = new CustomerQuery("Q003", "C001", "First query");
        CustomerQuery query2 = new CustomerQuery("Q004", "C001", "Second query");
        
        service.handleQuery(query1);
        service.handleQuery(query2);
        
        assertEquals(1, service.getActiveConversationCount());
        
        ConversationContext context = service.getContext("C001");
        assertEquals(2, context.getInteractionCount());
    }
    
    @Test
    void testMultipleCustomers() {
        CustomerQuery query1 = new CustomerQuery("Q005", "C001", "Customer 1 query");
        CustomerQuery query2 = new CustomerQuery("Q006", "C002", "Customer 2 query");
        CustomerQuery query3 = new CustomerQuery("Q007", "C003", "Customer 3 query");
        
        service.handleQuery(query1);
        service.handleQuery(query2);
        service.handleQuery(query3);
        
        assertEquals(3, service.getActiveConversationCount());
    }
    
    @Test
    void testGetContext() {
        CustomerQuery query = new CustomerQuery("Q008", "C001", "Test query");
        service.handleQuery(query);
        
        ConversationContext context = service.getContext("C001");
        assertNotNull(context);
        assertEquals("C001", context.getCustomerId());
        assertEquals(1, context.getInteractionCount());
    }
    
    @Test
    void testGetContextNonExistent() {
        ConversationContext context = service.getContext("C999");
        assertNull(context);
    }
    
    @Test
    void testClearContext() {
        CustomerQuery query = new CustomerQuery("Q009", "C001", "Test query");
        service.handleQuery(query);
        
        assertEquals(1, service.getActiveConversationCount());
        
        service.clearContext("C001");
        
        assertEquals(0, service.getActiveConversationCount());
        assertNull(service.getContext("C001"));
    }
    
    @Test
    void testClearContextNonExistent() {
        service.clearContext("C999"); // Should not throw exception
        assertEquals(0, service.getActiveConversationCount());
    }
    
    @Test
    void testConcurrentCustomers() {
        // Simulate multiple customers being served concurrently
        for (int i = 1; i <= 10; i++) {
            String customerId = "C" + String.format("%03d", i);
            CustomerQuery query = new CustomerQuery("Q" + i, customerId, "Query " + i);
            service.handleQuery(query);
        }
        
        assertEquals(10, service.getActiveConversationCount());
        
        // Verify each customer has their own context
        for (int i = 1; i <= 10; i++) {
            String customerId = "C" + String.format("%03d", i);
            ConversationContext context = service.getContext(customerId);
            assertNotNull(context);
            assertEquals(customerId, context.getCustomerId());
        }
    }
    
    @Test
    void testContextIsolation() {
        CustomerQuery query1 = new CustomerQuery("Q010", "C001", "Customer 1 query");
        CustomerQuery query2 = new CustomerQuery("Q011", "C002", "Customer 2 query");
        
        service.handleQuery(query1);
        service.handleQuery(query2);
        
        ConversationContext context1 = service.getContext("C001");
        ConversationContext context2 = service.getContext("C002");
        
        assertNotEquals(context1.getSessionId(), context2.getSessionId());
        assertEquals(1, context1.getInteractionCount());
        assertEquals(1, context2.getInteractionCount());
    }
    
    @Test
    void testMultipleQueriesSameCustomer() {
        String customerId = "C001";
        
        for (int i = 1; i <= 5; i++) {
            CustomerQuery query = new CustomerQuery("Q" + i, customerId, "Query " + i);
            service.handleQuery(query);
        }
        
        assertEquals(1, service.getActiveConversationCount());
        
        ConversationContext context = service.getContext(customerId);
        assertEquals(5, context.getInteractionCount());
    }
}
