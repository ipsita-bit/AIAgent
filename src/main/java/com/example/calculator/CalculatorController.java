package com.example.calculator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

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

    @GetMapping("/addWeight")
    public ResponseEntity<WeightResponse> addWeight(@RequestParam double kg, @RequestParam double grams) {
        CalculatorService.WeightResult result = calculatorService.addWeight(kg, grams);
        return ResponseEntity.ok(new WeightResponse(kg, grams, result.getKilograms(), result.getGrams(), "weight addition"));
    }
}
