package com.example.customersupport.ethics;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.SupportResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Ethics validator ensuring bias, fairness, and transparency in AI responses.
 * Validates responses against ethical guidelines and bias detection.
 */
public class EthicsValidator {
    
    private static final String[] BIASED_TERMS = {
        "always", "never", "all", "none", "must", "should"
    };
    
    private static final String[] DISCRIMINATORY_KEYWORDS = {
        // Placeholder - in real systems, this would be more comprehensive
    };
    
    /**
     * Validates a response for ethical concerns.
     */
    public ValidationResult validate(SupportResponse response, CustomerQuery query) {
        List<String> issues = new ArrayList<>();
        
        // Check for bias
        if (containsBiasedLanguage(response.getResponseText())) {
            issues.add("Response contains potentially biased language");
        }
        
        // Check for discriminatory content
        if (containsDiscriminatoryContent(response.getResponseText())) {
            issues.add("Response may contain discriminatory content");
        }
        
        // Check for transparency
        if (!isTransparent(response)) {
            issues.add("Response lacks transparency about AI involvement");
        }
        
        // Check for fairness in escalation
        if (response.isRequiresEscalation() && response.getEscalationReason() == null) {
            issues.add("Escalation lacks clear reasoning");
        }
        
        boolean isValid = issues.isEmpty();
        return new ValidationResult(isValid, issues);
    }
    
    /**
     * Checks if response contains biased language.
     */
    private boolean containsBiasedLanguage(String text) {
        String lowerText = text.toLowerCase();
        long count = Arrays.stream(BIASED_TERMS)
            .filter(lowerText::contains)
            .count();
        
        // Threshold: more than 2 biased terms
        return count > 2;
    }
    
    /**
     * Checks if response contains discriminatory content.
     */
    private boolean containsDiscriminatoryContent(String text) {
        String lowerText = text.toLowerCase();
        return Arrays.stream(DISCRIMINATORY_KEYWORDS)
            .anyMatch(lowerText::contains);
    }
    
    /**
     * Checks if response is transparent about AI involvement.
     */
    private boolean isTransparent(SupportResponse response) {
        // For now, we assume responses are transparent if they have good confidence
        // In real systems, this would check for AI disclosure
        return response.getConfidenceLevel() > 0.3;
    }
    
    /**
     * Ensures fairness in query processing.
     */
    public boolean ensureFairness(CustomerQuery query) {
        // All queries should be treated equally regardless of customer
        // This is a placeholder for more sophisticated fairness checks
        return query.getCustomerId() != null && !query.getCustomerId().isEmpty();
    }
    
    /**
     * Result of ethics validation.
     */
    public static class ValidationResult {
        private final boolean isValid;
        private final List<String> issues;
        
        public ValidationResult(boolean isValid, List<String> issues) {
            this.isValid = isValid;
            this.issues = new ArrayList<>(issues);
        }
        
        public boolean isValid() { return isValid; }
        public List<String> getIssues() { return new ArrayList<>(issues); }
        
        @Override
        public String toString() {
            return "ValidationResult{isValid=" + isValid + ", issues=" + issues + "}";
        }
    }
}
