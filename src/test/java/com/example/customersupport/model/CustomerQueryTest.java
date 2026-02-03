package com.example.customersupport.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class CustomerQueryTest {
    
    private CustomerQuery query;
    
    @BeforeEach
    void setUp() {
        query = new CustomerQuery("Q001", "C001", "How do I reset my password?");
    }
    
    @Test
    void testConstructor() {
        assertNotNull(query);
        assertEquals("Q001", query.getQueryId());
        assertEquals("C001", query.getCustomerId());
        assertEquals("How do I reset my password?", query.getQueryText());
        assertNotNull(query.getTimestamp());
        assertEquals(CustomerQuery.QueryPriority.MEDIUM, query.getPriority());
        assertEquals(CustomerQuery.QueryCategory.GENERAL, query.getCategory());
    }
    
    @Test
    void testSetters() {
        query.setPriority(CustomerQuery.QueryPriority.HIGH);
        assertEquals(CustomerQuery.QueryPriority.HIGH, query.getPriority());
        
        query.setCategory(CustomerQuery.QueryCategory.TECHNICAL);
        assertEquals(CustomerQuery.QueryCategory.TECHNICAL, query.getCategory());
        
        LocalDateTime newTimestamp = LocalDateTime.now().plusHours(1);
        query.setTimestamp(newTimestamp);
        assertEquals(newTimestamp, query.getTimestamp());
    }
    
    @Test
    void testQueryPriorityEnum() {
        assertEquals(4, CustomerQuery.QueryPriority.values().length);
        assertNotNull(CustomerQuery.QueryPriority.valueOf("LOW"));
        assertNotNull(CustomerQuery.QueryPriority.valueOf("MEDIUM"));
        assertNotNull(CustomerQuery.QueryPriority.valueOf("HIGH"));
        assertNotNull(CustomerQuery.QueryPriority.valueOf("URGENT"));
    }
    
    @Test
    void testQueryCategoryEnum() {
        assertEquals(5, CustomerQuery.QueryCategory.values().length);
        assertNotNull(CustomerQuery.QueryCategory.valueOf("GENERAL"));
        assertNotNull(CustomerQuery.QueryCategory.valueOf("TECHNICAL"));
        assertNotNull(CustomerQuery.QueryCategory.valueOf("BILLING"));
        assertNotNull(CustomerQuery.QueryCategory.valueOf("ACCOUNT"));
        assertNotNull(CustomerQuery.QueryCategory.valueOf("PRODUCT"));
    }
    
    @Test
    void testEquals() {
        CustomerQuery query1 = new CustomerQuery("Q001", "C001", "Test query");
        CustomerQuery query2 = new CustomerQuery("Q001", "C002", "Different query");
        CustomerQuery query3 = new CustomerQuery("Q002", "C001", "Test query");
        
        assertEquals(query1, query2); // Same query ID
        assertNotEquals(query1, query3); // Different query ID
    }
    
    @Test
    void testHashCode() {
        CustomerQuery query1 = new CustomerQuery("Q001", "C001", "Test query");
        CustomerQuery query2 = new CustomerQuery("Q001", "C002", "Different query");
        
        assertEquals(query1.hashCode(), query2.hashCode());
    }
    
    @Test
    void testToString() {
        String str = query.toString();
        assertTrue(str.contains("Q001"));
        assertTrue(str.contains("C001"));
        assertTrue(str.contains("How do I reset my password?"));
    }
    
    @Test
    void testAllPriorities() {
        query.setPriority(CustomerQuery.QueryPriority.LOW);
        assertEquals(CustomerQuery.QueryPriority.LOW, query.getPriority());
        
        query.setPriority(CustomerQuery.QueryPriority.URGENT);
        assertEquals(CustomerQuery.QueryPriority.URGENT, query.getPriority());
    }
    
    @Test
    void testAllCategories() {
        query.setCategory(CustomerQuery.QueryCategory.BILLING);
        assertEquals(CustomerQuery.QueryCategory.BILLING, query.getCategory());
        
        query.setCategory(CustomerQuery.QueryCategory.ACCOUNT);
        assertEquals(CustomerQuery.QueryCategory.ACCOUNT, query.getCategory());
        
        query.setCategory(CustomerQuery.QueryCategory.PRODUCT);
        assertEquals(CustomerQuery.QueryCategory.PRODUCT, query.getCategory());
    }
}
