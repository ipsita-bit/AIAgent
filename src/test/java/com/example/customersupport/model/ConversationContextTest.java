package com.example.customersupport.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class ConversationContextTest {
    
    private ConversationContext context;
    private CustomerQuery query1;
    private CustomerQuery query2;
    private SupportResponse response1;
    private SupportResponse response2;
    
    @BeforeEach
    void setUp() {
        context = new ConversationContext("S001", "C001");
        query1 = new CustomerQuery("Q001", "C001", "First query");
        query2 = new CustomerQuery("Q002", "C001", "Second query");
        response1 = new SupportResponse("R001", "Q001", "First response");
        response2 = new SupportResponse("R002", "Q002", "Second response");
    }
    
    @Test
    void testConstructor() {
        assertNotNull(context);
        assertEquals("S001", context.getSessionId());
        assertEquals("C001", context.getCustomerId());
        assertEquals(0, context.getInteractionCount());
        assertTrue(context.getQueryHistory().isEmpty());
        assertTrue(context.getResponseHistory().isEmpty());
        assertTrue(context.getContextData().isEmpty());
    }
    
    @Test
    void testAddQuery() {
        context.addQuery(query1);
        assertEquals(1, context.getInteractionCount());
        assertEquals(1, context.getQueryHistory().size());
        
        context.addQuery(query2);
        assertEquals(2, context.getInteractionCount());
        assertEquals(2, context.getQueryHistory().size());
    }
    
    @Test
    void testAddResponse() {
        context.addResponse(response1);
        assertEquals(1, context.getResponseHistory().size());
        
        context.addResponse(response2);
        assertEquals(2, context.getResponseHistory().size());
    }
    
    @Test
    void testSetAndGetContextValue() {
        context.setContextValue("lastIntent", "HELP_REQUEST");
        assertEquals("HELP_REQUEST", context.getContextValue("lastIntent"));
        
        context.setContextValue("priority", 5);
        assertEquals(5, context.getContextValue("priority"));
    }
    
    @Test
    void testGetLastQuery() {
        assertNull(context.getLastQuery());
        
        context.addQuery(query1);
        assertEquals(query1, context.getLastQuery());
        
        context.addQuery(query2);
        assertEquals(query2, context.getLastQuery());
    }
    
    @Test
    void testGetLastResponse() {
        assertNull(context.getLastResponse());
        
        context.addResponse(response1);
        assertEquals(response1, context.getLastResponse());
        
        context.addResponse(response2);
        assertEquals(response2, context.getLastResponse());
    }
    
    @Test
    void testQueryHistoryImmutability() {
        context.addQuery(query1);
        List<CustomerQuery> history = context.getQueryHistory();
        
        // Modifying returned list should not affect internal state
        history.clear();
        assertEquals(1, context.getQueryHistory().size());
    }
    
    @Test
    void testResponseHistoryImmutability() {
        context.addResponse(response1);
        List<SupportResponse> history = context.getResponseHistory();
        
        // Modifying returned list should not affect internal state
        history.clear();
        assertEquals(1, context.getResponseHistory().size());
    }
    
    @Test
    void testContextDataImmutability() {
        context.setContextValue("key1", "value1");
        Map<String, Object> data = context.getContextData();
        
        // Modifying returned map should not affect internal state
        data.clear();
        assertNotNull(context.getContextValue("key1"));
    }
    
    @Test
    void testToString() {
        context.addQuery(query1);
        context.addResponse(response1);
        
        String str = context.toString();
        assertTrue(str.contains("S001"));
        assertTrue(str.contains("C001"));
        assertTrue(str.contains("interactionCount=1"));
    }
    
    @Test
    void testMultipleInteractions() {
        for (int i = 0; i < 5; i++) {
            CustomerQuery q = new CustomerQuery("Q" + i, "C001", "Query " + i);
            SupportResponse r = new SupportResponse("R" + i, "Q" + i, "Response " + i);
            context.addQuery(q);
            context.addResponse(r);
        }
        
        assertEquals(5, context.getInteractionCount());
        assertEquals(5, context.getQueryHistory().size());
        assertEquals(5, context.getResponseHistory().size());
    }
    
    @Test
    void testSetSessionId() {
        context.setSessionId("S002");
        assertEquals("S002", context.getSessionId());
    }
    
    @Test
    void testSetCustomerId() {
        context.setCustomerId("C002");
        assertEquals("C002", context.getCustomerId());
    }
}
