package work.aaronskeels.javaknowledgedemos;

import org.junit.Rule;
import org.junit.Test;

public class ExampleWatcherTest {
    // Apply the custom rule using the @Rule annotation
    @Rule
    public ExampleWatcher customTestWatcher = new ExampleWatcher();

    @Test
    public void testExample1() {
        System.out.println("[exemplifyTestWatcher] Executing testExample1");
        // Insert test logic here
    }

    @Test
    public void testExample2() {
        System.out.println("[exemplifyTestWatcher] Executing testExample2");
        // Insert test logic here
    }
}
