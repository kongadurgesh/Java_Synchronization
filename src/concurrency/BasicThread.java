package concurrency;

public class BasicThread implements Runnable {

    private String message;

    public BasicThread(String name) {
        this.message = name;
    }

    @Override
    public void run() {
        String completeMessage = Thread.currentThread().getName() + ": " + message;
        System.out.println(completeMessage);
    }

}
