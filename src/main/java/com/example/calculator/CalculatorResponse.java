package com.example.calculator;

public class CalculatorResponse {
    private double operandA;
    private double operandB;
    private double result;
    private String operation;
    private String error;

    public CalculatorResponse(double operandA, double operandB, double result, String operation) {
        this.operandA = operandA;
        this.operandB = operandB;
        this.result = result;
        this.operation = operation;
    }

    public CalculatorResponse(double operandA, double operandB, double result, String operation, String error) {
        this.operandA = operandA;
        this.operandB = operandB;
        this.result = result;
        this.operation = operation;
        this.error = error;
    }

    public double getOperandA() {
        return operandA;
    }

    public void setOperandA(double operandA) {
        this.operandA = operandA;
    }

    public double getOperandB() {
        return operandB;
    }

    public void setOperandB(double operandB) {
        this.operandB = operandB;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
