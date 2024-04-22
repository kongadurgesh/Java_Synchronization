package basic;

public class CounterWithSynchronization {
    private int count;

    public int getCount() {
        return count;
    }

    public synchronized void increment() {
        count++;
    }
}
