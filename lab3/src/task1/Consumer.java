package task1;

import task1.BoundedBuffer;

public class Consumer implements Runnable {
    private BoundedBuffer buffer;
    private int number;

    public Consumer(BoundedBuffer buffer, int number) {
        this.buffer = buffer;
        this.number = number;
    }

    public void run() {

        for(int i = 0;  i < 10;   i++) {
                try{
                    String message = (String) buffer.take();
                    System.out.println(message+" consumed by "+number);
                }catch (InterruptedException e){e.printStackTrace();}
        }

    }
}

