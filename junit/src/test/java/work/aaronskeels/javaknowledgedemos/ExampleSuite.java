package work.aaronskeels.javaknowledgedemos;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class) // This is required to run as a suite
@Suite.SuiteClasses({JUnitTest.class, JUnitTwoTest.class}) // This is also required to run as a suite
public class ExampleSuite {
    // You can include more config here, or not.
}
