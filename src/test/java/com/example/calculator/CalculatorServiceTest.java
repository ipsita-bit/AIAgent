package com.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    void testAdd() {
        assertEquals(5.0, calculatorService.add(2.0, 3.0));
        assertEquals(0.0, calculatorService.add(-5.0, 5.0));
        assertEquals(-10.0, calculatorService.add(-5.0, -5.0));
    }

    @Test
    void testSubtract() {
        assertEquals(-1.0, calculatorService.subtract(2.0, 3.0));
        assertEquals(10.0, calculatorService.subtract(5.0, -5.0));
        assertEquals(0.0, calculatorService.subtract(5.0, 5.0));
    }

    @Test
    void testMultiply() {
        assertEquals(6.0, calculatorService.multiply(2.0, 3.0));
        assertEquals(-25.0, calculatorService.multiply(5.0, -5.0));
        assertEquals(0.0, calculatorService.multiply(5.0, 0.0));
    }

    @Test
    void testDivide() {
        assertEquals(2.0, calculatorService.divide(6.0, 3.0));
        assertEquals(-1.0, calculatorService.divide(5.0, -5.0));
        assertEquals(0.0, calculatorService.divide(0.0, 5.0));
    }

    @Test
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.divide(5.0, 0.0);
        });
        assertEquals("Cannot divide by zero", exception.getMessage());
    }
}
