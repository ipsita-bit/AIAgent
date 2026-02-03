package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Default implementation of PerceptionMechanism using rule-based NLP techniques.
 * Analyzes queries for intent, sentiment, and urgency.
 */
public class RuleBasedPerception implements PerceptionMechanism {
    
    private static final String[] URGENT_KEYWORDS = {"urgent", "emergency", "immediately", "critical", "asap"};
    private static final String[] NEGATIVE_KEYWORDS = {"unhappy", "frustrated", "angry", "disappointed", "terrible"};
    private static final String[] POSITIVE_KEYWORDS = {"thank", "happy", "great", "excellent", "appreciate"};
    
    @Override
    public PerceptionResult analyze(CustomerQuery query) {
        String queryText = query.getQueryText().toLowerCase();
        
        String intent = detectIntent(queryText);
        String sentiment = detectSentiment(queryText);
        double urgency = calculateUrgency(queryText);
        String[] keywords = extractKeywords(queryText);
        
        return new PerceptionResult(intent, sentiment, urgency, keywords);
    }
    
    private String detectIntent(String queryText) {
        if (queryText.contains("refund") || queryText.contains("money back")) {
            return "REFUND_REQUEST";
        } else if (queryText.contains("how to") || queryText.contains("help with")) {
            return "HELP_REQUEST";
        } else if (queryText.contains("not working") || queryText.contains("broken")) {
            return "TECHNICAL_ISSUE";
        } else if (queryText.contains("billing") || queryText.contains("charge")) {
            return "BILLING_INQUIRY";
        }
        return "GENERAL_INQUIRY";
    }
    
    private String detectSentiment(String queryText) {
        long negativeCount = Arrays.stream(NEGATIVE_KEYWORDS)
            .filter(queryText::contains)
            .count();
        long positiveCount = Arrays.stream(POSITIVE_KEYWORDS)
            .filter(queryText::contains)
            .count();
        
        if (negativeCount > positiveCount) {
            return "NEGATIVE";
        } else if (positiveCount > negativeCount) {
            return "POSITIVE";
        }
        return "NEUTRAL";
    }
    
    private double calculateUrgency(String queryText) {
        long urgentKeywordCount = Arrays.stream(URGENT_KEYWORDS)
            .filter(queryText::contains)
            .count();
        
        boolean hasExclamation = queryText.contains("!");
        boolean hasAllCaps = Pattern.compile("[A-Z]{3,}").matcher(queryText).find();
        
        double urgency = 0.3; // base urgency
        urgency += urgentKeywordCount * 0.2;
        urgency += hasExclamation ? 0.15 : 0;
        urgency += hasAllCaps ? 0.15 : 0;
        
        return Math.min(urgency, 1.0);
    }
    
    private String[] extractKeywords(String queryText) {
        // Simple keyword extraction - remove common words
        String[] words = queryText.split("\\s+");
        return Arrays.stream(words)
            .filter(word -> word.length() > 3)
            .filter(word -> !isCommonWord(word))
            .limit(5)
            .toArray(String[]::new);
    }
    
    private boolean isCommonWord(String word) {
        String[] commonWords = {"the", "and", "for", "with", "that", "this", "from", "have"};
        return Arrays.asList(commonWords).contains(word.toLowerCase());
    }
}
