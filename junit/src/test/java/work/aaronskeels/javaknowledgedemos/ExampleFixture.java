package work.aaronskeels.javaknowledgedemos;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExampleFixture {
    @Before // - This is ran once before EVERY test
    public void setUp() {
        System.out.println("[exemplifyFixture] @Before");
    }
    @BeforeClass // - This is ran once for the entire class
    public static void setUpClass() {
        System.out.println("[exemplifyFixture] @BeforeClass");
    }
    @Test
    public void testOne() {
        int i = 1;
        assertEquals(i, 1);
    }
    // @Test
    // public void testTwo() {
    //     String str = "I am another test string!";
    //     assertEquals("I am a test string!", str);
    // }
    @After // - This is ran once after EVERY test
    public void tearDown() {
        System.out.println("[exemplifyFixture] @After");
    }
    @AfterClass // - This is ran once for the entire class
    public static void tearDownClass() {
        System.out.println("[exemplifyFixture] @AfterClass");
    }
}
