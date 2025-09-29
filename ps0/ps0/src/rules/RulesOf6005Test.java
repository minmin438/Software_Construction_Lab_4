package rules;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * JUnit tests for RulesOf6005.
 */
public class RulesOf6005Test {
    
    @Test
    public void testMayUseCodeInAssignment() {
        // Case 1: Publicly available code → allowed
        assertTrue("Expected true: publicly-available code",
                RulesOf6005.mayUseCodeInAssignment(false, true, false, false, false));
        
        // Case 2: Self-written code → always allowed
        assertTrue("Expected true: self-written code",
                RulesOf6005.mayUseCodeInAssignment(true, false, false, false, true));
        
        // Case 3: Own coursework → allowed
        assertTrue("Expected true: own coursework",
                RulesOf6005.mayUseCodeInAssignment(false, false, true, false, true));
        
        // Case 4: External cited code when implementation not required → allowed
        assertTrue("Expected true: cited external code (not required)",
                RulesOf6005.mayUseCodeInAssignment(false, false, false, true, false));
        
        // Case 5: External code without citation → not allowed
        assertFalse("Expected false: external code without citation",
                RulesOf6005.mayUseCodeInAssignment(false, false, false, false, false));
        
        // Case 6: Cited external code but implementation required → NOT allowed
        assertFalse("Expected false: cited external code when implementation required",
                RulesOf6005.mayUseCodeInAssignment(false, false, false, true, true));
        
        // Case 7: Self-written AND cited (redundant but should still be allowed)
        assertTrue("Expected true: self-written and cited",
                RulesOf6005.mayUseCodeInAssignment(true, false, false, true, true));
    }
}
