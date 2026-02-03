package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;

/**
 * Interface for perception mechanisms that interpret and understand user queries.
 * Implementations should extract intent, sentiment, and key information from queries.
 */
public interface PerceptionMechanism {
    
    /**
     * Analyzes the customer query to extract intent and sentiment.
     * @param query The customer query to analyze
     * @return PerceptionResult containing analyzed information
     */
    PerceptionResult analyze(CustomerQuery query);
    
    /**
     * Represents the result of perception analysis.
     */
    class PerceptionResult {
        private final String intent;
        private final String sentiment;
        private final double urgency;
        private final String[] keywords;
        
        public PerceptionResult(String intent, String sentiment, double urgency, String[] keywords) {
            this.intent = intent;
            this.sentiment = sentiment;
            this.urgency = urgency;
            this.keywords = keywords;
        }
        
        public String getIntent() { return intent; }
        public String getSentiment() { return sentiment; }
        public double getUrgency() { return urgency; }
        public String[] getKeywords() { return keywords; }
    }
}
