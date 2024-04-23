package interthread_communication;

public class NumberClass {
    private int number;
    private boolean isNumberSet = false;

    public synchronized void getNumber() {
        while (!isNumberSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isNumberSet = false;
        System.out.println("GET: " + number);
        notify();
    }

    public synchronized void setNumber(int number) {
        if (isNumberSet) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.number = number;
        isNumberSet = true;
        System.out.println("PUT: " + number);
        notify();
    }

    public boolean isNumberSet() {
        return isNumberSet;
    }

}
