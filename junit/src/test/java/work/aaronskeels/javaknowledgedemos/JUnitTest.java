package work.aaronskeels.javaknowledgedemos;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JUnitTest {
    @Test
    public void testSampleString() {
        String str = "I am a test string!";
        assertEquals("I am a test string!", str);
    }
}
