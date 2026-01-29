package com.example.calculator;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (Math.abs(b) < 1e-10) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
}
