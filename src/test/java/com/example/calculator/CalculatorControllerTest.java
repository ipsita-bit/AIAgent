package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAdd() throws Exception {
        mockMvc.perform(get("/api/calculator/add")
                .param("a", "5")
                .param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operandA").value(5.0))
                .andExpect(jsonPath("$.operandB").value(3.0))
                .andExpect(jsonPath("$.result").value(8.0))
                .andExpect(jsonPath("$.operation").value("addition"));
    }

    @Test
    void testSubtract() throws Exception {
        mockMvc.perform(get("/api/calculator/subtract")
                .param("a", "10")
                .param("b", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operandA").value(10.0))
                .andExpect(jsonPath("$.operandB").value(4.0))
                .andExpect(jsonPath("$.result").value(6.0))
                .andExpect(jsonPath("$.operation").value("subtraction"));
    }

    @Test
    void testMultiply() throws Exception {
        mockMvc.perform(get("/api/calculator/multiply")
                .param("a", "6")
                .param("b", "7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operandA").value(6.0))
                .andExpect(jsonPath("$.operandB").value(7.0))
                .andExpect(jsonPath("$.result").value(42.0))
                .andExpect(jsonPath("$.operation").value("multiplication"));
    }

    @Test
    void testDivide() throws Exception {
        mockMvc.perform(get("/api/calculator/divide")
                .param("a", "20")
                .param("b", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operandA").value(20.0))
                .andExpect(jsonPath("$.operandB").value(4.0))
                .andExpect(jsonPath("$.result").value(5.0))
                .andExpect(jsonPath("$.operation").value("division"));
    }

    @Test
    void testDivideByZero() throws Exception {
        mockMvc.perform(get("/api/calculator/divide")
                .param("a", "10")
                .param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Cannot divide by zero"));
    }

    @Test
    void testAddWeight() throws Exception {
        mockMvc.perform(get("/api/calculator/addWeight")
                .param("kg", "2")
                .param("grams", "500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kilograms").value(2.0))
                .andExpect(jsonPath("$.grams").value(500.0))
                .andExpect(jsonPath("$.totalKilograms").value(2.0))
                .andExpect(jsonPath("$.totalGrams").value(500.0))
                .andExpect(jsonPath("$.operation").value("weight addition"));
    }

    @Test
    void testAddWeightWithOverflow() throws Exception {
        mockMvc.perform(get("/api/calculator/addWeight")
                .param("kg", "1")
                .param("grams", "1500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kilograms").value(1.0))
                .andExpect(jsonPath("$.grams").value(1500.0))
                .andExpect(jsonPath("$.totalKilograms").value(2.0))
                .andExpect(jsonPath("$.totalGrams").value(500.0))
                .andExpect(jsonPath("$.operation").value("weight addition"));
    }

    @Test
    void testAddWeightZero() throws Exception {
        mockMvc.perform(get("/api/calculator/addWeight")
                .param("kg", "0")
                .param("grams", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.kilograms").value(0.0))
                .andExpect(jsonPath("$.grams").value(0.0))
                .andExpect(jsonPath("$.totalKilograms").value(0.0))
                .andExpect(jsonPath("$.totalGrams").value(0.0))
                .andExpect(jsonPath("$.operation").value("weight addition"));
    }
}
