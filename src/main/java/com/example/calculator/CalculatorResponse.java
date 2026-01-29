package com.example.calculator;

public record CalculatorResponse(
    double operandA,
    double operandB,
    double result,
    String operation,
    String error
) {
    public CalculatorResponse(double operandA, double operandB, double result, String operation) {
        this(operandA, operandB, result, operation, null);
    }
}
