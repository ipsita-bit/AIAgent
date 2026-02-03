package com.example.customersupport.service;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.SupportResponse;
import com.example.customersupport.model.ConversationContext;
import com.example.customersupport.agent.CustomerSupportAgent;
import com.example.customersupport.agent.AIParadigm;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service layer for customer support operations.
 * Manages agent instances and conversation contexts.
 */
public class CustomerSupportService {
    
    private final CustomerSupportAgent agent;
    private final Map<String, ConversationContext> activeContexts;
    
    public CustomerSupportService() {
        this(AIParadigm.HYBRID);
    }
    
    public CustomerSupportService(AIParadigm paradigm) {
        this.agent = new CustomerSupportAgent(paradigm);
        this.activeContexts = new ConcurrentHashMap<>();
    }
    
    /**
     * Handles a customer query and returns a response.
     */
    public SupportResponse handleQuery(CustomerQuery query) {
        // Get or create conversation context
        ConversationContext context = activeContexts.computeIfAbsent(
            query.getCustomerId(),
            customerId -> new ConversationContext(
                java.util.UUID.randomUUID().toString(),
                customerId
            )
        );
        
        // Process query through agent
        return agent.processQuery(query, context);
    }
    
    /**
     * Gets the conversation context for a customer.
     */
    public ConversationContext getContext(String customerId) {
        return activeContexts.get(customerId);
    }
    
    /**
     * Clears the conversation context for a customer.
     */
    public void clearContext(String customerId) {
        activeContexts.remove(customerId);
    }
    
    /**
     * Gets the number of active conversations.
     */
    public int getActiveConversationCount() {
        return activeContexts.size();
    }
    
    /**
     * Gets the AI paradigm being used.
     */
    public AIParadigm getParadigm() {
        return agent.getParadigm();
    }
}
