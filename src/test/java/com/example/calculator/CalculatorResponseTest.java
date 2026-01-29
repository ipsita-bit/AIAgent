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
        
        assertEquals(5.0, response.operandA());
        assertEquals(3.0, response.operandB());
        assertEquals(8.0, response.result());
        assertEquals("addition", response.operation());
        assertNull(response.error());
    }

    @Test
    void testConstructorWithError() {
        CalculatorResponse response = new CalculatorResponse(10.0, 0.0, 0.0, "division", "Cannot divide by zero");
        
        assertEquals(10.0, response.operandA());
        assertEquals(0.0, response.operandB());
        assertEquals(0.0, response.result());
        assertEquals("division", response.operation());
        assertEquals("Cannot divide by zero", response.error());
    }

    @Test
    void testImmutabilityOfOperandA() {
        CalculatorResponse response1 = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        CalculatorResponse response2 = new CalculatorResponse(10.0, 3.0, 8.0, "addition");
        assertEquals(5.0, response1.operandA());
        assertEquals(10.0, response2.operandA());
    }

    @Test
    void testImmutabilityOfOperandB() {
        CalculatorResponse response1 = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        CalculatorResponse response2 = new CalculatorResponse(5.0, 7.0, 8.0, "addition");
        assertEquals(3.0, response1.operandB());
        assertEquals(7.0, response2.operandB());
    }

    @Test
    void testImmutabilityOfResult() {
        CalculatorResponse response1 = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        CalculatorResponse response2 = new CalculatorResponse(5.0, 3.0, 15.0, "addition");
        assertEquals(8.0, response1.result());
        assertEquals(15.0, response2.result());
    }

    @Test
    void testImmutabilityOfOperation() {
        CalculatorResponse response1 = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        CalculatorResponse response2 = new CalculatorResponse(5.0, 3.0, 8.0, "multiplication");
        assertEquals("addition", response1.operation());
        assertEquals("multiplication", response2.operation());
    }

    @Test
    void testImmutabilityOfError() {
        CalculatorResponse response1 = new CalculatorResponse(5.0, 3.0, 8.0, "addition");
        CalculatorResponse response2 = new CalculatorResponse(5.0, 3.0, 8.0, "addition", "Some error message");
        assertNull(response1.error());
        assertEquals("Some error message", response2.error());
    }

    @Test
    void testNegativeNumbers() {
        CalculatorResponse response = new CalculatorResponse(-5.0, -3.0, -8.0, "addition");
        
        assertEquals(-5.0, response.operandA());
        assertEquals(-3.0, response.operandB());
        assertEquals(-8.0, response.result());
    }

    @Test
    void testZeroValues() {
        CalculatorResponse response = new CalculatorResponse(0.0, 0.0, 0.0, "multiplication");
        
        assertEquals(0.0, response.operandA());
        assertEquals(0.0, response.operandB());
        assertEquals(0.0, response.result());
        assertEquals("multiplication", response.operation());
    }

    @Test
    void testLargeNumbers() {
        CalculatorResponse response = new CalculatorResponse(Double.MAX_VALUE, 1.0, Double.MAX_VALUE, "addition");
        
        assertEquals(Double.MAX_VALUE, response.operandA());
        assertEquals(1.0, response.operandB());
        assertEquals(Double.MAX_VALUE, response.result());
    }
}
