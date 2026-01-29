package com.example.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping("/add")
    public ResponseEntity<CalculatorResponse> add(@RequestParam double a, @RequestParam double b) {
        double result = calculatorService.add(a, b);
        return ResponseEntity.ok(new CalculatorResponse(a, b, result, "addition"));
    }

    @GetMapping("/subtract")
    public ResponseEntity<CalculatorResponse> subtract(@RequestParam double a, @RequestParam double b) {
        double result = calculatorService.subtract(a, b);
        return ResponseEntity.ok(new CalculatorResponse(a, b, result, "subtraction"));
    }

    @GetMapping("/multiply")
    public ResponseEntity<CalculatorResponse> multiply(@RequestParam double a, @RequestParam double b) {
        double result = calculatorService.multiply(a, b);
        return ResponseEntity.ok(new CalculatorResponse(a, b, result, "multiplication"));
    }

    @GetMapping("/divide")
    public ResponseEntity<CalculatorResponse> divide(@RequestParam double a, @RequestParam double b) {
        try {
            double result = calculatorService.divide(a, b);
            return ResponseEntity.ok(new CalculatorResponse(a, b, result, "division"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CalculatorResponse(a, b, 0, "division", e.getMessage()));
        }
    }

    static class CalculatorResponse {
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
}
