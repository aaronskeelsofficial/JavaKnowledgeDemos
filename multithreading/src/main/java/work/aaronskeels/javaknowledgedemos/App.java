package work.aaronskeels.javaknowledgedemos;

import java.util.concurrent.CountDownLatch;

public class App 
{
    public static void main( String[] args )
    {
        // Concept 1: Threads - Allows concurrent multithreading.
        exemplifyThread();
        // Concept 2: Runnable - Allows unit testing of Thread workloads. Seems unnecessary for most cases.
        exemplifyRunnable();
        // Concept 3: Thread Sleeping - Does exactly what you'd expect. Throws workload balance to JVM management.
        exemplifySleep();
        // Concept 4: Thread Joining - Pause execution of threads until other threads have finished or time has passed.
        exemplifyJoin();
        // Concept 5: Data Safety - Most difficult part of multithreading, avoiding data corruption.
        exemplifySynchronized();
        // Concept 6a: This is an INCORRECT implementation of Wait/Notify which falls victim to classic multithreading order of operation blunders.
        //      Shame on DigitalOcean for posting this as an informational/educational blog post on "proper thread safety".
        //exemplifyWaitNotifyERROR();
        // Concept 6b: Wait/Notify - Analogous to RPC calls but tied specifically to data/objects.
        exemplifyWaitNotify();
        // Concept 7: Singleton - A class of which there is only ever one created instance, which for the most part feels like it could just be static.
        //      Unsure of any specific implementations which require this.
        exemplifySingleton();
    }

    /**
     * This method exemplifies the four primary means of creating a new thread.
     * Note: Threads do not return values. If this is the desired functionality, look into Callables
     * Note: Typically threads are created VIA Runnables, however after the research I've done I see
     *  no reason for this to be the case a majority of the case. It seems silly. Though it is standard
     *  so just acknowledge that for whatever reason.
     */
    public static void exemplifyThread() {
        class ExemplifyThread {
            public static void methodReference() {
                System.out.println("[exemplifyThread] 4: " + Thread.currentThread().threadId());
            }

            public static void run() {
                // Method 1: Custom class extending Thread
                class CustomThreadClass extends Thread {
                    @Override
                    public void run() {
                        System.out.println("[exemplifyThread] 1: " + Thread.currentThread().threadId());
                    }
                }
                CustomThreadClass t1 = new CustomThreadClass();
                System.out.println("[exemplifyThread] Calling Thread 1");
                t1.start();

                // Method 2: Anonymous class
                Thread t2 = new Thread() {
                    @Override
                    public void run() {
                        System.out.println("[exemplifyThread] 2: " + Thread.currentThread().threadId());
                    }
                };
                System.out.println("[exemplifyThread] Calling Thread 2");
                t2.start();

                // Method 3: Lambda function
                Thread t3 = new Thread(() -> System.out.println("[exemplifyThread] 3: " + Thread.currentThread().threadId()));
                System.out.println("[exemplifyThread] Calling Thread 3");
                t3.start();

                // Method 4: Method Reference
                Thread t4 = new Thread(ExemplifyThread::methodReference);
                System.out.println("[exemplifyThread] Calling Thread 4");
                t4.start();
            }
        }
        ExemplifyThread.run();
    }

    /**
     * This method exemplifies how to create a Thread via Runnable. Not sure why this is the standard
     * in a majority of cases of Thread creation when it's not normally necessary for basic things.
     * Note: Unrelated to multithreading, runnables can be called outside of the context of new Threads.
     */
    public static void exemplifyRunnable() {
        class CustomRunnableClass implements Runnable {
            @Override
            public void run() {
                System.out.println("[exemplifyRunnable] 1: " + Thread.currentThread().threadId());
            }
        }
        Thread t1 = new Thread(new CustomRunnableClass());
        System.out.println("[exemplifyRunnable] Calling Thread 1");
        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("[exemplifyRunnable] 2: " + Thread.currentThread().threadId());
            }
        });
        System.out.println("[exemplifyRunnable] Calling Thread 2");
        t2.start();
    }

    /**
     * Basic example of sleep and how it is impercise.
     * Note: OS specific implementation so precision can vary.
     */
    public static void exemplifySleep() {
        Thread t1 = new Thread(() -> {
            try {
                Long time1 = System.currentTimeMillis();
                Thread.sleep(2000);
                Long time2 = System.currentTimeMillis();
                System.out.println("[exemplifySleep] " + (time2-time1) + " millis passed waiting \"2000ms\": " + time2 + "->" + time1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
    }

    public static void exemplifyJoin() {
        class CustomRunnableClass implements Runnable {
            @Override
            public void run() {
                System.out.println("[exemplifyJoin] Thread started:::"+Thread.currentThread().getName());
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("[exemplifyJoin] Thread ended:::"+Thread.currentThread().getName());
            }
        }
        Thread t1 = new Thread(new CustomRunnableClass(), "t1");
        Thread t2 = new Thread(new CustomRunnableClass(), "t2");
        Thread t3 = new Thread(new CustomRunnableClass(), "t3");
        
        t1.start();
        
        // Start second thread after waiting for 2 seconds after t1 or if it's dead
        try {
            t1.join(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        t2.start();
        
        // Start t3 only when t1 is dead
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        t3.start();
        
        // Let all threads finish execution before finishing main thread
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("[exemplifyJoin] All threads are dead, exiting main thread");
    }

    /**
     * This method exemplifies a typical way to make multithreading allow for safe variable manipulation
     * via the "synchronized" keyword.
     * Note: When dealing with multithreading and data safety, LEAN INTO DATA PRIVACY. Every variable/field should be
     *  immediately assumed and treated private unless an explicit reason it must not be comes up.
     * Note: A typical approach is to "synchronized" a dummy private placeholder Object associated with the actual value
     *  we will be trying to synchronize. This is because we can NOT synchronize primitives, yet we don't want to lock
     *  the entire class by locking the owning class.
     * Note: I'd assume there are better approaches, but this seems workable.
     */
    public static void exemplifySynchronized() {
        class ProcessingThread implements Runnable {
            private int count;
            private Object mutex = new Object();
    
            @Override
            public void run() {
                for(int i = 0; i < 10; i++){
                    processSomething(i);

                    synchronized (mutex) {
                        count++;
                    };
                }
            }

            public int getCount() {
                return this.count;
            }

            private void processSomething(int i) {
                // Simulate processing some time-consuming workload
                try {
                    Thread.sleep(i * 100);
                } catch (InterruptedException e) {e.printStackTrace();}
            }
        }

        ProcessingThread pt = new ProcessingThread();
        Thread t1 = new Thread(pt);
        t1.start();
        Thread t2 = new Thread(pt);
        t2.start();
        // Wait for threads to finish processing
        try {
            t1.join();
            t2.join();
        } catch (Exception e) {e.printStackTrace();}
        // This value should be 20, but without synchronization could vary
        System.out.println("[exemplifySynchronized] Processing count = " + pt.getCount() + "/20");
    }

    /**
     * This class was intended to exemplify how to utilize wait/notify standards, but actually ran into a multithreading
     * bug itself. The error being ran into was sometimes the notifier would initialize and notify before the waiting
     * threads had entered the waiting state. The fix appears to utilize a CountDownLatch.
     * Note: Error came from the *DigitalOcean* link below. Aren't they supposed to be a reputable company?
     *  https://www.digitalocean.com/community/tutorials/java-thread-wait-notify-and-notifyall-example
     */
    public static void exemplifyWaitNotifyERROR() {

        class DataModifiedClass {
            private String msg;

            public DataModifiedClass(String message) {
                this.msg = message;
            }

            public void setMsg(String message) {
                this.msg = message;
            }
            
            public String getMsg() {
                return msg;
            }
        }

        class NotifierClass implements Runnable {
            private DataModifiedClass data;

            public NotifierClass(DataModifiedClass data) {
                this.data = data;
            }

            @Override
            public void run() {
                System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " started.");
                synchronized(data) {
                    try {
                        Thread.sleep(100);
                        data.setMsg("Message set by " + Thread.currentThread().getName());
                        System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " notifying.");
                        data.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        class WaiterClass implements Runnable {
            private DataModifiedClass data;

            public WaiterClass(DataModifiedClass data) {
                this.data = data;
            }

            @Override
            public void run() {
                System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " started.");
                synchronized(data) {
                    try {
                        System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " waiting.");
                        data.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " notified.");
                    System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " processing: " + data.getMsg());
                }
            }
        }

        DataModifiedClass data = new DataModifiedClass("Original Message");
        WaiterClass waiter1 = new WaiterClass(data);
        new Thread(waiter1, "Waiter1").start();
        WaiterClass waiter2 = new WaiterClass(data);
        new Thread(waiter2, "Waiter2").start();
        NotifierClass notifier = new NotifierClass(data);
        new Thread(notifier, "Notifier").start();
        System.out.println("[exemplifyWaitNotify] All Waiters/Notifiers Initialized");
    }

    /**
     * This is the fixed version of the Wait/Notify method. The issue with the other version is the notify was firing off
     * before the wait classes were initialized and listening. To address this, another layer of functional blocking/pausing
     * was utilized with a CountDownLatch where we know we need two waiters Threads to initialize first and each subtracts from
     * the latch. Whenever the notifier load is processed, it either will pause until the latch has been released if it begins
     * too early, or the latch will already have been released and the notifier load will proceed like normal.
     * Note: A stipulation of this approach is that we know exactly how much the latch "counter" will be (two waiter threads).
     *  This might for some reason not always be the case, and if it's not I'm uninformed on how to proceed.
     * Note: Also just in general this approach kinda feels gross. I'm sure there is a more concise or neat feeling approach
     *  with less layers/nesting of conditions.
     */
    public static void exemplifyWaitNotify() {

        class DataModifiedClass {
            private String msg;

            public DataModifiedClass(String message) {
                this.msg = message;
            }

            public void setMsg(String message) {
                this.msg = message;
            }
            
            public String getMsg() {
                return msg;
            }
        }

        class NotifierClass implements Runnable {
            private DataModifiedClass data;
            private CountDownLatch latch;

            public NotifierClass(DataModifiedClass data, CountDownLatch latch) {
                this.data = data;
                this.latch = latch;
            }

            @Override
            public void run() {
                System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " started.");
                try {
                    latch.await();
                    synchronized(data) {
                        Thread.sleep(100);
                        data.setMsg("Message set by " + Thread.currentThread().getName());
                        System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " notifying.");
                        data.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        class WaiterClass implements Runnable {
            private DataModifiedClass data;
            private CountDownLatch latch;

            public WaiterClass(DataModifiedClass data, CountDownLatch latch) {
                this.data = data;
                this.latch = latch;
            }

            @Override
            public void run() {
                System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " started.");
                synchronized(data) {
                    try {
                        System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " waiting.");
                        latch.countDown();
                        data.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " notified.");
                    System.out.println("[exemplifyWaitNotify] " + Thread.currentThread().getName() + " processing: " + data.getMsg());
                }
            }
        }

        DataModifiedClass data = new DataModifiedClass("Original Message");
        CountDownLatch latch = new CountDownLatch(2);
        WaiterClass waiter1 = new WaiterClass(data, latch);
        new Thread(waiter1, "Waiter1").start();
        WaiterClass waiter2 = new WaiterClass(data, latch);
        new Thread(waiter2, "Waiter2").start();
        NotifierClass notifier = new NotifierClass(data, latch);
        new Thread(notifier, "Notifier").start();
        System.out.println("[exemplifyWaitNotify] All Waiters/Notifiers Initialized");
    }

    /**
     * This is a sample implementation of a thread-safe singleton. There are a few other prominent ways to do so, but this is
     * believed to be the best balance of safety and performance. Other examples @:
     * https://www.digitalocean.com/community/tutorials/thread-safety-in-java-singleton-classes
     */
    @SuppressWarnings("unused") // Don't care
    public static void exemplifySingleton() {
        class CustomSingletonClass {
            private static CustomSingletonClass instance = null;
            private static Object mutex = new Object();

            private CustomSingletonClass() {
            }

            public static CustomSingletonClass getInstance() {
                CustomSingletonClass result = instance;
                if (result == null) {
                    synchronized (mutex) {
                        result = instance;
                        if (result == null)
                            instance = result = new CustomSingletonClass();
                    }
                }
                return result;
            }
        }
    }
}
