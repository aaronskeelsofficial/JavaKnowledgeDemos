package work.aaronskeels.javaknowledgedemos;

import org.junit.Rule;
import org.junit.Test;

public class ExampleRuleTest {
    // Apply the custom rule using the @Rule annotation
    @Rule
    public ExampleRule customTestRule = new ExampleRule();

    @Test
    public void testExample1() {
        System.out.println("[exemplifyTestRule] Executing testExample1");
        // Insert test logic here
    }

    @Test
    public void testExample2() {
        System.out.println("[exemplifyTestRule] Executing testExample2");
        // Insert test logic here
    }
}
