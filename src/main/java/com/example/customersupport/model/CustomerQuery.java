package com.example.customersupport.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a customer query in the support system.
 * Contains the query text, metadata, and classification information.
 */
public class CustomerQuery {
    private String queryId;
    private String customerId;
    private String queryText;
    private LocalDateTime timestamp;
    private QueryPriority priority;
    private QueryCategory category;
    
    public CustomerQuery(String queryId, String customerId, String queryText) {
        this.queryId = queryId;
        this.customerId = customerId;
        this.queryText = queryText;
        this.timestamp = LocalDateTime.now();
        this.priority = QueryPriority.MEDIUM;
        this.category = QueryCategory.GENERAL;
    }
    
    public enum QueryPriority {
        LOW, MEDIUM, HIGH, URGENT
    }
    
    public enum QueryCategory {
        GENERAL, TECHNICAL, BILLING, ACCOUNT, PRODUCT
    }
    
    // Getters and Setters
    public String getQueryId() { return queryId; }
    public void setQueryId(String queryId) { this.queryId = queryId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public QueryPriority getPriority() { return priority; }
    public void setPriority(QueryPriority priority) { this.priority = priority; }
    
    public QueryCategory getCategory() { return category; }
    public void setCategory(QueryCategory category) { this.category = category; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerQuery that = (CustomerQuery) o;
        return Objects.equals(queryId, that.queryId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(queryId);
    }
    
    @Override
    public String toString() {
        return "CustomerQuery{" +
                "queryId='" + queryId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", queryText='" + queryText + '\'' +
                ", timestamp=" + timestamp +
                ", priority=" + priority +
                ", category=" + category +
                '}';
    }
}
