public class Producer implements Runnable {
    private Buffer buffer;
    private int number;

    public Producer(Buffer buffer, int number) {
        this.buffer = buffer;
        this.number = number;
    }

    public void run() {

        for(int i = 0;  i < 10;   i++) {
            synchronized (buffer) {
                buffer.put("message " + i + " produced by " + number);
            }
        }

    }
}