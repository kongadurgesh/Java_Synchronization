import basic.Counter;
import basic.CounterWithSynchronization;

public class App {
    public static void main(String[] args) throws InterruptedException {
        // Example for Multithreading issue by using a counter object
        Counter counter = new Counter();

        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < 2000; i++) {
                counter.increment();
            }
        });

        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < 2000; i++) {
                counter.increment();
            }
        });
        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
        System.out.println("expected count: 4000, actual count: " + counter.getCount()); // change in counts

        // Example to fix the resource access using synchronization on counter object
        // increment method

        CounterWithSynchronization counterWithSynchronization = new CounterWithSynchronization();

        Thread syncThread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 2000; i++) {
                    counterWithSynchronization.increment();
                }
            }
        });
        Thread syncThread2 = new Thread(() -> {
            for (int i = 0; i < 2000; i++) {
                counterWithSynchronization.increment();
            }
        });
        syncThread1.start();
        syncThread2.start();
        syncThread1.join();
        syncThread2.join();

        // Count Matches
        System.out.println("Expected Count: 4000, Actual Count: " + counterWithSynchronization.getCount());
    }
}
