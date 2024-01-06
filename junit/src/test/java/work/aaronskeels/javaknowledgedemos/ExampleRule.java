package work.aaronskeels.javaknowledgedemos;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ExampleRule implements TestRule {
    // Override the apply method to return a Statement that wraps the test method
    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                // Code to execute before the test method
                System.out.println("[exemplifyTestRule] Before test method: " + description.getMethodName());

                try {
                    // Execute the test method
                    base.evaluate();
                } catch (Throwable t) {
                    // Code to handle problems during test method execution
                    System.out.println("[exemplifyTestRule] Exception during test method: " + t.getMessage());
                    throw t;
                } finally {
                    // Code to execute after the test method
                    System.out.println("[exemplifyTestRule] After test method: " + description.getMethodName());
                }
            }
        };
    }
}
