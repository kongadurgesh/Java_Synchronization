package concurrency;

public class VoltaileCounter {
    private volatile int count;

    public int getCount() {
        return count;
    }

    public void increment() {
        count++;
    }

}
