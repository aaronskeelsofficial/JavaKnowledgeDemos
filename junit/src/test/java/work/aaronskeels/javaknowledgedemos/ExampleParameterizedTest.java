package work.aaronskeels.javaknowledgedemos;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ExampleParameterizedTest {
    // Declare variables for parameters
    private int input, multiplier;
    private String prefix, expectedOutput;

    // Constructor to initialize parameters
    public ExampleParameterizedTest(String prefix, int input, int multiplier, String expectedOutput) {
        this.prefix = prefix;
        this.input = input;
        this.multiplier = multiplier;
        this.expectedOutput = expectedOutput;
    }

    // Define the parameterized data using a static method
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"lorem", 1, 2, "lorem2"},
                {"ipsum", 2, 3, "ipsum6"},
                {"foo", 3, 6, "bar18"},
                {"dummy", 4, 8, "dummy32"}
        });
    }

    // Your actual test method
    @Test()
    public void testMultiply() {
        assertEquals(expectedOutput, prefix + (input*multiplier));
    }
}
