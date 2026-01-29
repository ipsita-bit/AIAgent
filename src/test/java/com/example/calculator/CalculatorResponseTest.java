package com.example.calculator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for CalculatorResponse to improve code coverage.
 * This test class was suggested by the CodeCoverageAgent.
 */
class CalculatorResponseTest {

    @Test
    void testConstructorWithoutError() {
        CalculatorResponse response = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        
        assertEquals(5.0, response.getOperandA());
        assertEquals(3.0, response.getOperandB());
        assertEquals(8.0, response.getResult());
        assertEquals("addition", response.getOperation());
        assertNull(response.getError());
    }

    @Test
    void testConstructorWithError() {
        CalculatorResponse response = new CalculatorResponse(10.0, 0.0, 0.0, "division", "Cannot divide by zero");
        
        assertEquals(10.0, response.getOperandA());
        assertEquals(0.0, response.getOperandB());
        assertEquals(0.0, response.getResult());
        assertEquals("division", response.getOperation());
        assertEquals("Cannot divide by zero", response.getError());
    }

    @Test
    void testSetOperandA() {
        CalculatorResponse response = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        response.setOperandA(10.0);
        assertEquals(10.0, response.getOperandA());
    }

    @Test
    void testSetOperandB() {
        CalculatorResponse response = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        response.setOperandB(7.0);
        assertEquals(7.0, response.getOperandB());
    }

    @Test
    void testSetResult() {
        CalculatorResponse response = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        response.setResult(15.0);
        assertEquals(15.0, response.getResult());
    }

    @Test
    void testSetOperation() {
        CalculatorResponse response = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        response.setOperation("multiplication");
        assertEquals("multiplication", response.getOperation());
    }

    @Test
    void testSetError() {
        CalculatorResponse response = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        response.setError("Some error message");
        assertEquals("Some error message", response.getError());
    }

    @Test
    void testNegativeNumbers() {
        CalculatorResponse response = new CalculatorResponse(-5.0, -3.0, -8.0, "addition");
        
        assertEquals(-5.0, response.getOperandA());
        assertEquals(-3.0, response.getOperandB());
        assertEquals(-8.0, response.getResult());
    }

    @Test
    void testZeroValues() {
        CalculatorResponse response = new CalculatorResponse(0.0, 0.0, 0.0, "multiplication");
        
        assertEquals(0.0, response.getOperandA());
        assertEquals(0.0, response.getOperandB());
        assertEquals(0.0, response.getResult());
        assertEquals("multiplication", response.getOperation());
    }

    @Test
    void testLargeNumbers() {
        CalculatorResponse response = new CalculatorResponse(Double.MAX_VALUE, 1.0, Double.MAX_VALUE, "addition");
        
        assertEquals(Double.MAX_VALUE, response.getOperandA());
        assertEquals(1.0, response.getOperandB());
        assertEquals(Double.MAX_VALUE, response.getResult());
    }
}
