package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.ConversationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Planning engine that prioritizes tasks and maintains conversation flow.
 * Manages task queue and ensures optimal response ordering.
 */
public class PlanningEngine {
    
    private final PriorityQueue<Task> taskQueue;
    
    public PlanningEngine() {
        this.taskQueue = new PriorityQueue<>(Comparator.comparingInt(Task::getPriority).reversed());
    }
    
    /**
     * Plans the next actions based on query and context.
     */
    public List<String> planActions(
            CustomerQuery query,
            PerceptionMechanism.PerceptionResult perception,
            ConversationContext context) {
        
        List<String> actions = new ArrayList<>();
        
        // Always start with acknowledging the query
        actions.add("ACKNOWLEDGE_QUERY");
        
        // Add intent-specific actions
        switch (perception.getIntent()) {
            case "HELP_REQUEST":
                actions.add("SEARCH_KNOWLEDGE_BASE");
                actions.add("PROVIDE_STEP_BY_STEP_GUIDE");
                break;
            case "TECHNICAL_ISSUE":
                actions.add("GATHER_SYSTEM_INFO");
                actions.add("RUN_DIAGNOSTICS");
                actions.add("SUGGEST_SOLUTIONS");
                break;
            case "BILLING_INQUIRY":
                actions.add("RETRIEVE_BILLING_INFO");
                actions.add("EXPLAIN_CHARGES");
                break;
            case "REFUND_REQUEST":
                actions.add("VERIFY_ELIGIBILITY");
                actions.add("ESCALATE_TO_SPECIALIST");
                break;
            default:
                actions.add("PROVIDE_GENERAL_INFO");
        }
        
        // Add context maintenance action
        actions.add("UPDATE_CONTEXT");
        
        return actions;
    }
    
    /**
     * Prioritizes multiple queries when handling concurrent requests.
     */
    public void addTask(String taskId, int priority, String description) {
        taskQueue.offer(new Task(taskId, priority, description));
    }
    
    /**
     * Gets the next highest priority task.
     */
    public Task getNextTask() {
        return taskQueue.poll();
    }
    
    /**
     * Checks if there are pending tasks.
     */
    public boolean hasPendingTasks() {
        return !taskQueue.isEmpty();
    }
    
    /**
     * Gets the number of pending tasks.
     */
    public int getPendingTaskCount() {
        return taskQueue.size();
    }
    
    /**
     * Represents a task in the planning queue.
     */
    public static class Task {
        private final String taskId;
        private final int priority;
        private final String description;
        
        public Task(String taskId, int priority, String description) {
            this.taskId = taskId;
            this.priority = priority;
            this.description = description;
        }
        
        public String getTaskId() { return taskId; }
        public int getPriority() { return priority; }
        public String getDescription() { return description; }
        
        @Override
        public String toString() {
            return "Task{taskId='" + taskId + "', priority=" + priority + ", description='" + description + "'}";
        }
    }
}
