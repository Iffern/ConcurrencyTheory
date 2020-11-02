package task1;

import task1.BoundedBuffer;

public class Producer implements Runnable {
    private BoundedBuffer buffer;
    private int number;

    public Producer(BoundedBuffer buffer, int number) {
        this.buffer = buffer;
        this.number = number;
    }

    public void run() {

        for(int i = 0;  i < 10;   i++) {
            try {
                buffer.put("message " + i + " produced by " + number);
            }catch(InterruptedException e){e.printStackTrace();}
        }

    }
}
