package task2;

import java.util.Random;

public class Consumer extends Thread{
    private final int maxBound;
    private Buffer buffer;
    private final int number;
    private final int numOfConsumers;
    private final int M;

    public Consumer(int maxBound, Buffer buffer, int number, int numOfConsumers, int M){
        this.maxBound=maxBound;
        this.buffer = buffer;
        this.number=number;
        this.numOfConsumers = numOfConsumers;
        this.M = M;
    }

    @Override
    public void run() {
        Random rand = new Random();
            while(true) {
                int numOfPortions = rand.nextInt(maxBound)+1;
                //int numOfPortions = M - (number * M / numOfConsumers) - i;
                doStuff();
                //System.out.println("Consumer "+number+" want to get " + numOfPortions + " portions from buffer");
                buffer.get(numOfPortions);
                //System.out.println("Consumer "+number+" got " + numOfPortions + " portions from buffer");
        }
    }

    private void doStuff(){
        try {
            Random rand = new Random();
            sleep(rand.nextInt(3000));
        }catch (InterruptedException e) {e.printStackTrace();}
    }
}
