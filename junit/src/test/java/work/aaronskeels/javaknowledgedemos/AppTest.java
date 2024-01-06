package work.aaronskeels.javaknowledgedemos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import junit.framework.TestResult;
import junit.framework.TestSuite;

public class AppTest 
{
    public static void main(String[] args) {
        /*
         * Below there is a basic method explaining what a test may look like.
         * IMPORTANT: Maven defaults expect test classes to have a name ending in "...Test.java" and to have @Test annotations. This isn't
         *      important for running specifically targeted tests within code, but is important for running automated tests through GitHub
         *      and things of that nature.
         * To run it under the framework of JUnit properly, we can't just call the method though.
         * You must direct JUnit to the class (which will be this one) and it will run all tests contained within
         *      (technically the only method designated as a test in the first scope layer is exemplifyBasics)
         * Note: If you have numerous classes with the same name within your project, include the full package path here.
         * Note: If you utilize the JUnitCore.main method, the Java process will be exited after tests are ran.
         *      To continue running other code, utilize JUnitCore.runClasses instead.
         * Note: To ignore tests temporarily for debug purposes, put @Ignore("Reason") above @Test annotations
         * Note: Timeouts can be used via @Test(timeout)
         * Note: Simple expectation cases can be made via @Test()
         */
        // exemplifyBasics();
        //JUnitCore.main("work.aaronskeels.javaknowledgedemos.AppTest");
        Result result = JUnitCore.runClasses(AppTest.class);
        System.out.println("[exemplifyBasics] " + result.wasSuccessful());

        /*
         * Tests are created as their own classes external to the main() as standard in industry
         */
        exemplifyJUnitClass();

        /*
         * Below are fixture, TestRule. and TestWatcher. Although slightly similar, they have different intended purposes.
         *      - Fixtures are all-encompassing, with a scope that can be viewed as wrapping the entirety of a test.
         *      - TestRules and TestWatchers are smaller scale and applied on individual methods contained within a test.
         *      - TestRule: Used to modify setup/teardown or modify test execution flow
         *      - TestWatcher: Used to easily click into test-related events such as starting, finishing, succeeding, failing, etc.
         * Note: Rules are class-wide and apply to all test methods within a test class.
         * Note: Build up and combine multiple rules by having multiple @Rule annotations
         * Note: Some annotated methods need to be static, others need not be static. Ensure you output "Failure"s so JUnit will tell you.
         * Note: JUnit does NOT like non-public classes defined nested within other classes.
         */
        exemplifyFixture();
        exemplifyTestRule();
        exemplifyTestWatcher();

        /*
         * Suites are really nothing functionally unique. They are conceptually a way to bundle different test classes together.
         * Functionally there is little if any difference from JUnitCore.runClasses.
         */
        exemplifySuite();

        /*
         * There are MANY tutorials online for this, but their approach is the bad way. Do NOT listen and use @ParameterizedTest and @ValueSource.
         * That is such a janky and lackluster approach. Use the one outlined in this class which isn't covered practically anywhere online.
         * Note: Parameterized tests require @RunWith(Parameterized.class) header and @Parameterized.Parameters method which returns a Collection of sets of params
         */
        exemplifyParameterizedTest();
    }

    @Test
    public void exemplifyBasics() {
        /*
         * Assume is a logic gate which if flagged as a fail will skip this test entirely, not returning success nor failure.
         */
        assumeTrue(true);

        String str = "I am a test string!";
        assertEquals("I am a test string!", str);
        /*
         * Assert Options:
         * assert(Not)Equals
         * assertArrayEquals
         * assertFalse
         * assertTrue
         * assert(Not)Null
         * assert(Not)Same
         * Assert.assertThat -> MatcherAssert.assertThat
         * assertThrows (used for expected errors)
         */
    }

    public static void exemplifyJUnitClass() {
        Result result = JUnitCore.runClasses(JUnitTest.class);
        outputResult(result, "exemplifyJUnitClass");
    }

    public static void exemplifyFixture() {
        // JUnit does NOT like non-public classes defined nested within another class.
        /*
        class ExampleTest {
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
                String str = "I am a test string!";
                assertEquals("I am a test string!", str);
            }
            @Test
            public void testTwo() {
                String str = "I am another test string!";
                assertEquals("I am a test string!", str);
            }
            @After // - This is ran once after EVERY test
            public void tearDown() {
                System.out.println("[exemplifyFixture] @After");
            }
            @AfterClass // - This is ran once for the entire class
            public static void tearDownClass() {
                System.out.println("[exemplifyFixture] @AfterClass");
            }
        }
        Result result = JUnitCore.runClasses(ExampleTest.class);
        */
        Result result = JUnitCore.runClasses(ExampleFixture.class);
        outputResult(result, "[exemplifyFixture]");
    }

    public static void exemplifyTestRule() {
        Result result = JUnitCore.runClasses(ExampleRuleTest.class);
        outputResult(result, "[exemplifyTestRule]");
    }

    public static void exemplifyTestWatcher() {
        Result result = JUnitCore.runClasses(ExampleWatcherTest.class);
        outputResult(result, "[exemplifyTestWatcher]");
    }

    public static void exemplifySuite() {
        Result result = JUnitCore.runClasses(ExampleSuite.class);
        outputResult(result, "[exemplifySuite] 1:");

        // Outdated though possible approach below
        TestSuite suite = new TestSuite(JUnitTest.class, JUnitTwoTest.class);
        TestResult result2 = new TestResult();
        suite.run(result2);
    }

    public static void exemplifyParameterizedTest() {
        Result result = JUnitCore.runClasses(ExampleParameterizedTest.class);
        outputResult(result, "[exemplifyParameterizedTest] ");
    }

    public static void outputResult(Result result, String prefix) {
        System.out.println(prefix + " Number of tests run: " + result.getRunCount());
        System.out.println(prefix + " Number of tests failed: " + result.getFailureCount());
        for (Failure failure : result.getFailures()) {
            System.out.println(prefix + " Failure: " + failure.toString());
        }
        System.out.println(prefix + " " + result.wasSuccessful());
    }
}
