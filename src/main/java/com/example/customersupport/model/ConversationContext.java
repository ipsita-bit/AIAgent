package com.example.customersupport.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains conversation context for a customer support session.
 * Tracks conversation history, customer preferences, and session state.
 */
public class ConversationContext {
    private String sessionId;
    private String customerId;
    private List<CustomerQuery> queryHistory;
    private List<SupportResponse> responseHistory;
    private Map<String, Object> contextData;
    private int interactionCount;
    
    public ConversationContext(String sessionId, String customerId) {
        this.sessionId = sessionId;
        this.customerId = customerId;
        this.queryHistory = new ArrayList<>();
        this.responseHistory = new ArrayList<>();
        this.contextData = new HashMap<>();
        this.interactionCount = 0;
    }
    
    public void addQuery(CustomerQuery query) {
        this.queryHistory.add(query);
        this.interactionCount++;
    }
    
    public void addResponse(SupportResponse response) {
        this.responseHistory.add(response);
    }
    
    public void setContextValue(String key, Object value) {
        this.contextData.put(key, value);
    }
    
    public Object getContextValue(String key) {
        return this.contextData.get(key);
    }
    
    public CustomerQuery getLastQuery() {
        if (queryHistory.isEmpty()) {
            return null;
        }
        return queryHistory.get(queryHistory.size() - 1);
    }
    
    public SupportResponse getLastResponse() {
        if (responseHistory.isEmpty()) {
            return null;
        }
        return responseHistory.get(responseHistory.size() - 1);
    }
    
    // Getters and Setters
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public List<CustomerQuery> getQueryHistory() { return new ArrayList<>(queryHistory); }
    
    public List<SupportResponse> getResponseHistory() { return new ArrayList<>(responseHistory); }
    
    public Map<String, Object> getContextData() { return new HashMap<>(contextData); }
    
    public int getInteractionCount() { return interactionCount; }
    
    @Override
    public String toString() {
        return "ConversationContext{" +
                "sessionId='" + sessionId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", interactionCount=" + interactionCount +
                ", queryHistorySize=" + queryHistory.size() +
                ", responseHistorySize=" + responseHistory.size() +
                '}';
    }
}
