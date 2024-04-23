package concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private AtomicInteger atomicInteger = new AtomicInteger();

    public int getAtomicInteger() {
        return atomicInteger.get();
    }

    public void increment() {
        atomicInteger.incrementAndGet();
    }
}
