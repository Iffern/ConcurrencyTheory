public class Consumer implements Runnable {
    private Buffer buffer;
    private int number;

    public Consumer(Buffer buffer, int number) {
        this.buffer = buffer;
        this.number = number;
    }

    public void run() {

        for(int i = 0;  i < 10;   i++) {
            synchronized (buffer) {
                String message = buffer.take();
                System.out.println(message+" consumed by "+number);
            }
        }

    }
}
