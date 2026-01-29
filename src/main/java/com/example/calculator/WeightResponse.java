package com.example.calculator;

public class WeightResponse {
    private double kilograms;
    private double grams;
    private double totalKilograms;
    private double totalGrams;
    private String operation;
    private String error;

    public WeightResponse(double kilograms, double grams, double totalKilograms, double totalGrams, String operation) {
        this.kilograms = kilograms;
        this.grams = grams;
        this.totalKilograms = totalKilograms;
        this.totalGrams = totalGrams;
        this.operation = operation;
    }

    public WeightResponse(double kilograms, double grams, double totalKilograms, double totalGrams, String operation, String error) {
        this.kilograms = kilograms;
        this.grams = grams;
        this.totalKilograms = totalKilograms;
        this.totalGrams = totalGrams;
        this.operation = operation;
        this.error = error;
    }

    public double getKilograms() {
        return kilograms;
    }

    public void setKilograms(double kilograms) {
        this.kilograms = kilograms;
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }

    public double getTotalKilograms() {
        return totalKilograms;
    }

    public void setTotalKilograms(double totalKilograms) {
        this.totalKilograms = totalKilograms;
    }

    public double getTotalGrams() {
        return totalGrams;
    }

    public void setTotalGrams(double totalGrams) {
        this.totalGrams = totalGrams;
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
