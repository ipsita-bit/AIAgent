package com.example.customersupport.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class SupportResponseTest {
    
    private SupportResponse response;
    
    @BeforeEach
    void setUp() {
        response = new SupportResponse("R001", "Q001", "Here's how to reset your password...");
    }
    
    @Test
    void testConstructor() {
        assertNotNull(response);
        assertEquals("R001", response.getResponseId());
        assertEquals("Q001", response.getQueryId());
        assertEquals("Here's how to reset your password...", response.getResponseText());
        assertEquals(1.0, response.getConfidenceLevel(), 0.001);
        assertFalse(response.isRequiresEscalation());
        assertNull(response.getEscalationReason());
        assertNotNull(response.getTimestamp());
    }
    
    @Test
    void testSetConfidenceLevel() {
        response.setConfidenceLevel(0.75);
        assertEquals(0.75, response.getConfidenceLevel(), 0.001);
        
        response.setConfidenceLevel(0.0);
        assertEquals(0.0, response.getConfidenceLevel(), 0.001);
        
        response.setConfidenceLevel(1.0);
        assertEquals(1.0, response.getConfidenceLevel(), 0.001);
    }
    
    @Test
    void testSetConfidenceLevelInvalidLow() {
        assertThrows(IllegalArgumentException.class, () -> {
            response.setConfidenceLevel(-0.1);
        });
    }
    
    @Test
    void testSetConfidenceLevelInvalidHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            response.setConfidenceLevel(1.1);
        });
    }
    
    @Test
    void testEscalation() {
        response.setRequiresEscalation(true);
        assertTrue(response.isRequiresEscalation());
        
        response.setEscalationReason("Complex technical issue");
        assertEquals("Complex technical issue", response.getEscalationReason());
    }
    
    @Test
    void testSetTimestamp() {
        LocalDateTime newTimestamp = LocalDateTime.now().plusMinutes(5);
        response.setTimestamp(newTimestamp);
        assertEquals(newTimestamp, response.getTimestamp());
    }
    
    @Test
    void testEquals() {
        SupportResponse response1 = new SupportResponse("R001", "Q001", "Response 1");
        SupportResponse response2 = new SupportResponse("R001", "Q002", "Response 2");
        SupportResponse response3 = new SupportResponse("R002", "Q001", "Response 1");
        
        assertEquals(response1, response2); // Same response ID
        assertNotEquals(response1, response3); // Different response ID
    }
    
    @Test
    void testHashCode() {
        SupportResponse response1 = new SupportResponse("R001", "Q001", "Response 1");
        SupportResponse response2 = new SupportResponse("R001", "Q002", "Response 2");
        
        assertEquals(response1.hashCode(), response2.hashCode());
    }
    
    @Test
    void testToString() {
        response.setConfidenceLevel(0.85);
        response.setRequiresEscalation(true);
        response.setEscalationReason("Test reason");
        
        String str = response.toString();
        assertTrue(str.contains("R001"));
        assertTrue(str.contains("Q001"));
        assertTrue(str.contains("0.85"));
        assertTrue(str.contains("true"));
        assertTrue(str.contains("Test reason"));
    }
    
    @Test
    void testMultipleConfidenceLevels() {
        double[] confidences = {0.0, 0.25, 0.5, 0.75, 1.0};
        for (double conf : confidences) {
            response.setConfidenceLevel(conf);
            assertEquals(conf, response.getConfidenceLevel(), 0.001);
        }
    }
    
    @Test
    void testSetResponseText() {
        response.setResponseText("Updated response text");
        assertEquals("Updated response text", response.getResponseText());
    }
}
