package com.example.customersupport.agent;

import com.example.customersupport.model.CustomerQuery;
import com.example.customersupport.model.ConversationContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class PlanningEngineTest {
    
    private PlanningEngine engine;
    private ConversationContext context;
    
    @BeforeEach
    void setUp() {
        engine = new PlanningEngine();
        context = new ConversationContext("S001", "C001");
    }
    
    @Test
    void testPlanActionsHelpRequest() {
        CustomerQuery query = new CustomerQuery("Q001", "C001", "How to...?");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("HELP_REQUEST", "NEUTRAL", 0.3, new String[]{});
        
        List<String> actions = engine.planActions(query, perception, context);
        
        assertNotNull(actions);
        assertTrue(actions.contains("ACKNOWLEDGE_QUERY"));
        assertTrue(actions.contains("SEARCH_KNOWLEDGE_BASE"));
        assertTrue(actions.contains("PROVIDE_STEP_BY_STEP_GUIDE"));
        assertTrue(actions.contains("UPDATE_CONTEXT"));
    }
    
    @Test
    void testPlanActionsTechnicalIssue() {
        CustomerQuery query = new CustomerQuery("Q002", "C001", "Technical problem");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("TECHNICAL_ISSUE", "NEUTRAL", 0.5, new String[]{});
        
        List<String> actions = engine.planActions(query, perception, context);
        
        assertTrue(actions.contains("GATHER_SYSTEM_INFO"));
        assertTrue(actions.contains("RUN_DIAGNOSTICS"));
        assertTrue(actions.contains("SUGGEST_SOLUTIONS"));
    }
    
    @Test
    void testPlanActionsBillingInquiry() {
        CustomerQuery query = new CustomerQuery("Q003", "C001", "Billing question");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("BILLING_INQUIRY", "NEUTRAL", 0.3, new String[]{});
        
        List<String> actions = engine.planActions(query, perception, context);
        
        assertTrue(actions.contains("RETRIEVE_BILLING_INFO"));
        assertTrue(actions.contains("EXPLAIN_CHARGES"));
    }
    
    @Test
    void testPlanActionsRefundRequest() {
        CustomerQuery query = new CustomerQuery("Q004", "C001", "Refund request");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("REFUND_REQUEST", "NEUTRAL", 0.5, new String[]{});
        
        List<String> actions = engine.planActions(query, perception, context);
        
        assertTrue(actions.contains("VERIFY_ELIGIBILITY"));
        assertTrue(actions.contains("ESCALATE_TO_SPECIALIST"));
    }
    
    @Test
    void testPlanActionsGeneralInquiry() {
        CustomerQuery query = new CustomerQuery("Q005", "C001", "General question");
        PerceptionMechanism.PerceptionResult perception = 
            new PerceptionMechanism.PerceptionResult("GENERAL_INQUIRY", "NEUTRAL", 0.3, new String[]{});
        
        List<String> actions = engine.planActions(query, perception, context);
        
        assertTrue(actions.contains("PROVIDE_GENERAL_INFO"));
    }
    
    @Test
    void testAddTask() {
        engine.addTask("T001", 5, "High priority task");
        assertTrue(engine.hasPendingTasks());
        assertEquals(1, engine.getPendingTaskCount());
    }
    
    @Test
    void testGetNextTask() {
        engine.addTask("T001", 3, "Low priority");
        engine.addTask("T002", 7, "High priority");
        engine.addTask("T003", 5, "Medium priority");
        
        PlanningEngine.Task task1 = engine.getNextTask();
        assertEquals("T002", task1.getTaskId()); // Highest priority first
        assertEquals(7, task1.getPriority());
        
        PlanningEngine.Task task2 = engine.getNextTask();
        assertEquals("T003", task2.getTaskId());
        
        PlanningEngine.Task task3 = engine.getNextTask();
        assertEquals("T001", task3.getTaskId());
    }
    
    @Test
    void testHasPendingTasks() {
        assertFalse(engine.hasPendingTasks());
        
        engine.addTask("T001", 5, "Task");
        assertTrue(engine.hasPendingTasks());
        
        engine.getNextTask();
        assertFalse(engine.hasPendingTasks());
    }
    
    @Test
    void testGetPendingTaskCount() {
        assertEquals(0, engine.getPendingTaskCount());
        
        engine.addTask("T001", 5, "Task 1");
        assertEquals(1, engine.getPendingTaskCount());
        
        engine.addTask("T002", 3, "Task 2");
        assertEquals(2, engine.getPendingTaskCount());
        
        engine.getNextTask();
        assertEquals(1, engine.getPendingTaskCount());
    }
    
    @Test
    void testTaskGetters() {
        PlanningEngine.Task task = new PlanningEngine.Task("T001", 5, "Test task");
        
        assertEquals("T001", task.getTaskId());
        assertEquals(5, task.getPriority());
        assertEquals("Test task", task.getDescription());
    }
    
    @Test
    void testTaskToString() {
        PlanningEngine.Task task = new PlanningEngine.Task("T001", 5, "Test task");
        String str = task.toString();
        
        assertTrue(str.contains("T001"));
        assertTrue(str.contains("5"));
        assertTrue(str.contains("Test task"));
    }
    
    @Test
    void testGetNextTaskWhenEmpty() {
        assertNull(engine.getNextTask());
    }
}
