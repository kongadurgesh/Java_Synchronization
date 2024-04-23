import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import basic.Counter;
import basic.CounterWithSynchronization;
import concurrency.AtomicCounter;
import concurrency.VoltaileCounter;
import interthread_communication.Consumer;
import interthread_communication.NumberClass;
import interthread_communication.Producer;

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
        System.out.println("expected count: 4000, actual count: " +
                counter.getCount()); // change in counts

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
        System.out.println("Expected Count: 4000, Actual Count: " +
                counterWithSynchronization.getCount());

        // Example for InterThread Commnication using Producer Consumer Problem -wait()
        // & notify()
        // NumberClass numberClass = new NumberClass();
        // new Producer(numberClass);
        // new Consumer(numberClass);

        // Example for thread locking - alternative to synchronisation
        Counter counter2 = new Counter();
        Lock lock = new ReentrantLock();

        Thread lockThread1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    lock.lock();
                    counter2.increment();
                    lock.unlock();
                }
            }
        });
        Thread lockThread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                counter2.increment();
                lock.unlock();
            }
        });

        lockThread1.start();
        lockThread2.start();
        lockThread1.join();
        lockThread2.join();

        // Count matches when a lock is used
        System.out.println("Expected Count 20000, actual count: " +
                counter2.getCount());

        // Example for await() and signal()
        Counter counter3 = new Counter();
        Condition condition = lock.newCondition();

        Thread awaitThread = new Thread(new Runnable() {
            public void run() {
                lock.lock();
                System.out.println("Waiting");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
                System.out.println("Woken Up");
                for (int i = 0; i < 10000; i++) {
                    lock.lock();
                    counter3.increment();
                    lock.unlock();
                }
            }
        });
        Thread signalThread = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            System.out.println("Press the enter key..");
            Scanner scanner = new Scanner(System.in);
            try {
                scanner.nextLine();
            } finally {
                scanner.close();
            }
            System.out.println("Got the Enter Key..");
            condition.signal();
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                counter3.increment();
                lock.unlock();
            }
            lock.unlock();
        });

        // awaitThread.start();
        // signalThread.start();
        // awaitThread.join();
        // signalThread.join();

        System.out.println("Expected Count 20000, actual count: " +
                counter3.getCount());

        // Example for Thread Safety using Atomic Integer

        AtomicCounter atomicCounter = new AtomicCounter();

        Thread atomicThread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                atomicCounter.increment();
            }
        });

        Thread atomicThread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                atomicCounter.increment();
            }
        });
        atomicThread1.start();
        atomicThread2.start();

        atomicThread1.join();
        atomicThread2.join();
        // Count matches even without Synchronised - works only for Integer
        System.out.println("Expected count: 20000, Actual Count: " +
                atomicCounter.getAtomicInteger());

        // Example for synchronisation using volatile keyword

        VoltaileCounter volatileCounter = new VoltaileCounter();

        Thread volatileThread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                volatileCounter.increment();
            }
        });

        Thread volatileThread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                volatileCounter.increment();
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        volatileThread1.start();
        volatileThread2.start();

        volatileThread1.join();
        volatileThread2.join();
        // Count matches with sleep - works only for Integer
        System.out.println("Expected count: 200, Actual Count: " + volatileCounter.getCount());
    }
}
