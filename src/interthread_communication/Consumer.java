package interthread_communication;

public class Consumer implements Runnable {
    private NumberClass number = new NumberClass();

    public Consumer(NumberClass number) {
        this.number = number;
        Thread consumerThread = new Thread(this, "Consumer");
        consumerThread.start();
    }

    public void run() {
        while (true) {
            number.getNumber();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
