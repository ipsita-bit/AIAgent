package com.example.customersupport.ethics;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.SupportResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class EthicsValidatorTest {
    
    private EthicsValidator validator;
    
    @BeforeEach
    void setUp() {
        validator = new EthicsValidator();
    }
    
    @Test
    void testValidateGoodResponse() {
        CustomerQuery query = new CustomerQuery("Q001", "C001", "Test query");
        SupportResponse response = new SupportResponse("R001", "Q001", "Here is a helpful response");
        response.setConfidenceLevel(0.8);
        
        EthicsValidator.ValidationResult result = validator.validate(response, query);
        assertTrue(result.isValid());
        assertTrue(result.getIssues().isEmpty());
    }
    
    @Test
    void testValidateBiasedLanguage() {
        CustomerQuery query = new CustomerQuery("Q002", "C001", "Test query");
        SupportResponse response = new SupportResponse(
            "R002", "Q002", 
            "You should always do this and never do that and all users must follow this"
        );
        response.setConfidenceLevel(0.8);
        
        EthicsValidator.ValidationResult result = validator.validate(response, query);
        assertFalse(result.isValid());
        assertTrue(result.getIssues().stream()
            .anyMatch(issue -> issue.contains("biased")));
    }
    
    @Test
    void testValidateEscalationWithoutReason() {
        CustomerQuery query = new CustomerQuery("Q003", "C001", "Test query");
        SupportResponse response = new SupportResponse("R003", "Q003", "Escalating...");
        response.setRequiresEscalation(true);
        response.setConfidenceLevel(0.8);
        // No escalation reason set
        
        EthicsValidator.ValidationResult result = validator.validate(response, query);
        assertFalse(result.isValid());
        assertTrue(result.getIssues().stream()
            .anyMatch(issue -> issue.contains("Escalation")));
    }
    
    @Test
    void testValidateEscalationWithReason() {
        CustomerQuery query = new CustomerQuery("Q004", "C001", "Test query");
        SupportResponse response = new SupportResponse("R004", "Q004", "Escalating...");
        response.setRequiresEscalation(true);
        response.setEscalationReason("Complex technical issue");
        response.setConfidenceLevel(0.8);
        
        EthicsValidator.ValidationResult result = validator.validate(response, query);
        assertTrue(result.isValid());
    }
    
    @Test
    void testValidateLowConfidence() {
        CustomerQuery query = new CustomerQuery("Q005", "C001", "Test query");
        SupportResponse response = new SupportResponse("R005", "Q005", "Response text");
        response.setConfidenceLevel(0.2);
        
        EthicsValidator.ValidationResult result = validator.validate(response, query);
        assertFalse(result.isValid());
        assertTrue(result.getIssues().stream()
            .anyMatch(issue -> issue.contains("transparency")));
    }
    
    @Test
    void testEnsureFairnessValidQuery() {
        CustomerQuery query = new CustomerQuery("Q006", "C001", "Test query");
        assertTrue(validator.ensureFairness(query));
    }
    
    @Test
    void testEnsureFairnessNullCustomerId() {
        CustomerQuery query = new CustomerQuery("Q007", null, "Test query");
        assertFalse(validator.ensureFairness(query));
    }
    
    @Test
    void testEnsureFairnessEmptyCustomerId() {
        CustomerQuery query = new CustomerQuery("Q008", "", "Test query");
        assertFalse(validator.ensureFairness(query));
    }
    
    @Test
    void testValidationResultGetters() {
        EthicsValidator.ValidationResult result = new EthicsValidator.ValidationResult(
            false, 
            java.util.Arrays.asList("Issue 1", "Issue 2")
        );
        
        assertFalse(result.isValid());
        assertEquals(2, result.getIssues().size());
        assertTrue(result.getIssues().contains("Issue 1"));
        assertTrue(result.getIssues().contains("Issue 2"));
    }
    
    @Test
    void testValidationResultImmutability() {
        java.util.List<String> issues = new java.util.ArrayList<>();
        issues.add("Issue 1");
        
        EthicsValidator.ValidationResult result = new EthicsValidator.ValidationResult(false, issues);
        
        // Modify original list
        issues.clear();
        
        // Result should still have the issue
        assertEquals(1, result.getIssues().size());
    }
    
    @Test
    void testValidationResultToString() {
        EthicsValidator.ValidationResult result = new EthicsValidator.ValidationResult(
            true, 
            java.util.Collections.emptyList()
        );
        
        String str = result.toString();
        assertTrue(str.contains("true"));
    }
    
    @Test
    void testMultipleIssues() {
        CustomerQuery query = new CustomerQuery("Q009", "C001", "Test query");
        SupportResponse response = new SupportResponse(
            "R009", "Q009",
            "You must always do this and never do that and all users should follow"
        );
        response.setConfidenceLevel(0.2);
        response.setRequiresEscalation(true);
        
        EthicsValidator.ValidationResult result = validator.validate(response, query);
        assertFalse(result.isValid());
        assertTrue(result.getIssues().size() > 1);
    }
}
