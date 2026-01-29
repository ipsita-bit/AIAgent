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

    public WeightResult addWeight(double kilograms, double grams) {
        double totalGrams = (kilograms * 1000) + grams;
        double resultKilograms = Math.floor(totalGrams / 1000);
        double resultGrams = totalGrams % 1000;
        return new WeightResult(resultKilograms, resultGrams);
    }

    public static class WeightResult {
        private final double kilograms;
        private final double grams;

        public WeightResult(double kilograms, double grams) {
            this.kilograms = kilograms;
            this.grams = grams;
        }

        public double getKilograms() {
            return kilograms;
        }

        public double getGrams() {
            return grams;
        }
    }
}
