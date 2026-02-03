package com.example.customersupport.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a response from the customer support agent.
 * Contains the response text, confidence level, and escalation information.
 */
public class SupportResponse {
    private String responseId;
    private String queryId;
    private String responseText;
    private double confidenceLevel;
    private boolean requiresEscalation;
    private String escalationReason;
    private LocalDateTime timestamp;
    
    public SupportResponse(String responseId, String queryId, String responseText) {
        this.responseId = responseId;
        this.queryId = queryId;
        this.responseText = responseText;
        this.confidenceLevel = 1.0;
        this.requiresEscalation = false;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getResponseId() { return responseId; }
    public void setResponseId(String responseId) { this.responseId = responseId; }
    
    public String getQueryId() { return queryId; }
    public void setQueryId(String queryId) { this.queryId = queryId; }
    
    public String getResponseText() { return responseText; }
    public void setResponseText(String responseText) { this.responseText = responseText; }
    
    public double getConfidenceLevel() { return confidenceLevel; }
    public void setConfidenceLevel(double confidenceLevel) { 
        if (confidenceLevel < 0.0 || confidenceLevel > 1.0) {
            throw new IllegalArgumentException("Confidence level must be between 0.0 and 1.0");
        }
        this.confidenceLevel = confidenceLevel; 
    }
    
    public boolean isRequiresEscalation() { return requiresEscalation; }
    public void setRequiresEscalation(boolean requiresEscalation) { 
        this.requiresEscalation = requiresEscalation; 
    }
    
    public String getEscalationReason() { return escalationReason; }
    public void setEscalationReason(String escalationReason) { 
        this.escalationReason = escalationReason; 
    }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupportResponse that = (SupportResponse) o;
        return Objects.equals(responseId, that.responseId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(responseId);
    }
    
    @Override
    public String toString() {
        return "SupportResponse{" +
                "responseId='" + responseId + '\'' +
                ", queryId='" + queryId + '\'' +
                ", responseText='" + responseText + '\'' +
                ", confidenceLevel=" + confidenceLevel +
                ", requiresEscalation=" + requiresEscalation +
                ", escalationReason='" + escalationReason + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
