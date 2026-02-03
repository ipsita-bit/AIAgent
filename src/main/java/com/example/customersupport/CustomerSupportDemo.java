package com.example.customersupport;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.SupportResponse;
import com.example.customersupport.service.CustomerSupportService;
import com.example.customersupport.agent.AIParadigm;

/**
 * Demo application showing the AI Customer Support Agent in action.
 */
public class CustomerSupportDemo {
    public static void main(String[] args) {
        // Initialize the service with hybrid AI paradigm
        CustomerSupportService service = new CustomerSupportService(AIParadigm.HYBRID);
        
        System.out.println("=== AI Customer Support Agent Demo ===\n");
        System.out.println("Using AI Paradigm: " + service.getParadigm().getDescription() + "\n");
        
        // Demo 1: Simple help request
        System.out.println("--- Demo 1: Help Request ---");
        CustomerQuery query1 = new CustomerQuery("Q001", "RETAIL001", 
            "How do I reset my password for Retail Giant account?");
        SupportResponse response1 = service.handleQuery(query1);
        System.out.println("Query: " + query1.getQueryText());
        System.out.println("Response: " + response1.getResponseText());
        System.out.println("Confidence: " + String.format("%.2f%%", response1.getConfidenceLevel() * 100));
        System.out.println("Requires Escalation: " + response1.isRequiresEscalation() + "\n");
        
        // Demo 2: Technical issue (should escalate)
        System.out.println("--- Demo 2: Technical Issue ---");
        CustomerQuery query2 = new CustomerQuery("Q002", "PHARMA001", 
            "The prescription refill system is not working");
        SupportResponse response2 = service.handleQuery(query2);
        System.out.println("Query: " + query2.getQueryText());
        System.out.println("Response: " + response2.getResponseText());
        System.out.println("Confidence: " + String.format("%.2f%%", response2.getConfidenceLevel() * 100));
        System.out.println("Requires Escalation: " + response2.isRequiresEscalation());
        if (response2.isRequiresEscalation()) {
            System.out.println("Escalation Reason: " + response2.getEscalationReason());
        }
        System.out.println();
        
        // Demo 3: Urgent query
        System.out.println("--- Demo 3: Urgent Query ---");
        CustomerQuery query3 = new CustomerQuery("Q003", "BANK001", 
            "URGENT! Suspicious activity on my Global Bank account!");
        SupportResponse response3 = service.handleQuery(query3);
        System.out.println("Query: " + query3.getQueryText());
        System.out.println("Response: " + response3.getResponseText());
        System.out.println("Confidence: " + String.format("%.2f%%", response3.getConfidenceLevel() * 100));
        System.out.println("Requires Escalation: " + response3.isRequiresEscalation());
        if (response3.isRequiresEscalation()) {
            System.out.println("Escalation Reason: " + response3.getEscalationReason());
        }
        System.out.println();
        
        // Show active conversations
        System.out.println("--- System Statistics ---");
        System.out.println("Active Conversations: " + service.getActiveConversationCount());
        System.out.println("\nDemo completed successfully!");
    }
}
