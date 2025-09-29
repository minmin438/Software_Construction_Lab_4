package rules;

/**
 * RulesOf6005 represents the collaboration policy of 6.005 as described by the
 * general information on Stellar.
 */
public class RulesOf6005 {
     
    /**
     * Judge whether a given piece of code may be used in an assignment (problem
     * set or team project) or not, according to the 6.005 collaboration policy.
     */
    public static boolean mayUseCodeInAssignment(boolean writtenByYourself,
            boolean availableToOthers, boolean writtenAsCourseWork,
            boolean citingYourSource, boolean implementationRequired) {
        
        // Rule 1: Always allowed if you wrote it yourself
        if (writtenByYourself) {
            return true;
        }
        
        // Rule 2: Allowed if it was provided to everyone (course-provided resources)
        if (availableToOthers) {
            return true;
        }
        
        // Rule 3: Allowed if it’s your own coursework from 6.005
        if (writtenAsCourseWork) {
            return true;
        }
        
        // Rule 4: Allowed if you cite the source AND implementation is not required
        if (citingYourSource && !implementationRequired) {
            return true;
        }
        
        // Otherwise, not allowed
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Own code: " +
            mayUseCodeInAssignment(true, false, false, false, true));
    }
}
