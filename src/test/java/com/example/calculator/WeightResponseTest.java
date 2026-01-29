package com.example.calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeightResponseTest {

    @Test
    void testWeightResponseConstructorWithoutError() {
        WeightResponse response = new WeightResponse(2.0, 500.0, 2.0, 500.0, "weight addition");
        
        assertEquals(2.0, response.getKilograms());
        assertEquals(500.0, response.getGrams());
        assertEquals(2.0, response.getTotalKilograms());
        assertEquals(500.0, response.getTotalGrams());
        assertEquals("weight addition", response.getOperation());
        assertNull(response.getError());
    }

    @Test
    void testWeightResponseConstructorWithError() {
        WeightResponse response = new WeightResponse(2.0, 500.0, 0.0, 0.0, "weight addition", "Invalid input");
        
        assertEquals(2.0, response.getKilograms());
        assertEquals(500.0, response.getGrams());
        assertEquals(0.0, response.getTotalKilograms());
        assertEquals(0.0, response.getTotalGrams());
        assertEquals("weight addition", response.getOperation());
        assertEquals("Invalid input", response.getError());
    }

    @Test
    void testSetters() {
        WeightResponse response = new WeightResponse(0.0, 0.0, 0.0, 0.0, "test");
        
        response.setKilograms(3.0);
        response.setGrams(750.0);
        response.setTotalKilograms(3.0);
        response.setTotalGrams(750.0);
        response.setOperation("weight operation");
        response.setError("test error");
        
        assertEquals(3.0, response.getKilograms());
        assertEquals(750.0, response.getGrams());
        assertEquals(3.0, response.getTotalKilograms());
        assertEquals(750.0, response.getTotalGrams());
        assertEquals("weight operation", response.getOperation());
        assertEquals("test error", response.getError());
    }
}
