package interthread_communication;

public class Producer implements Runnable {
    NumberClass number = new NumberClass();

    public Producer(NumberClass number) {
        this.number = number;
        Thread producerThread = new Thread(this, "Producer");
        producerThread.start();
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            number.setNumber(i++);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

}
