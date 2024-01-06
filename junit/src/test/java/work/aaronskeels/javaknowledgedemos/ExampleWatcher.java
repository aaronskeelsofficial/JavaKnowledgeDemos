package work.aaronskeels.javaknowledgedemos;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ExampleWatcher extends TestWatcher {
    @Override
    protected void starting(Description description) {
        System.out.println("[exemplifyTestWatcher] starting");
    };
    @Override
    protected void finished(Description description) {
        System.out.println("[exemplifyTestWatcher] finished");
    };
    @Override
    protected void succeeded(Description description) {
        System.out.println("[exemplifyTestWatcher] succeeded");
    };
    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println("[exemplifyTestWatcher] failed");
    };
}
