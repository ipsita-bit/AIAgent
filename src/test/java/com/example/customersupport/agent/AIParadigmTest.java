package com.example.customersupport.agent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AIParadigmTest {
    
    @Test
    void testEnumValues() {
        AIParadigm[] paradigms = AIParadigm.values();
        assertEquals(3, paradigms.length);
    }
    
    @Test
    void testRuleBased() {
        AIParadigm paradigm = AIParadigm.RULE_BASED;
        assertEquals("RULE_BASED", paradigm.name());
        assertNotNull(paradigm.getDescription());
        assertTrue(paradigm.getDescription().contains("rule"));
    }
    
    @Test
    void testLearningBased() {
        AIParadigm paradigm = AIParadigm.LEARNING_BASED;
        assertEquals("LEARNING_BASED", paradigm.name());
        assertNotNull(paradigm.getDescription());
        assertTrue(paradigm.getDescription().contains("learning"));
    }
    
    @Test
    void testHybrid() {
        AIParadigm paradigm = AIParadigm.HYBRID;
        assertEquals("HYBRID", paradigm.name());
        assertNotNull(paradigm.getDescription());
        assertTrue(paradigm.getDescription().contains("Combine"));
    }
    
    @Test
    void testValueOf() {
        assertEquals(AIParadigm.RULE_BASED, AIParadigm.valueOf("RULE_BASED"));
        assertEquals(AIParadigm.LEARNING_BASED, AIParadigm.valueOf("LEARNING_BASED"));
        assertEquals(AIParadigm.HYBRID, AIParadigm.valueOf("HYBRID"));
    }
    
    @Test
    void testDescriptions() {
        for (AIParadigm paradigm : AIParadigm.values()) {
            assertNotNull(paradigm.getDescription());
            assertFalse(paradigm.getDescription().isEmpty());
        }
    }
}
