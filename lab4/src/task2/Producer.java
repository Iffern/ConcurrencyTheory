package task2;

import java.util.Random;

public class Producer extends Thread{
    private final int maxBound;
    private Buffer buffer;
    private int number;
    private int numOfProducers;
    private int M;

    public Producer(int maxBound, Buffer regularBuffer, int number, int numOfProducers, int M){
        this.maxBound=maxBound;
        this.buffer = regularBuffer;
        this.number=number;
        this.numOfProducers = numOfProducers;
        this.M = M;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for(int j=0;j<2;j++) {
            for (int i = 0; i < M / numOfProducers; i++) {
                //int numOfPortions = rand.nextInt(maxBound)+1;
                int numOfPortions = (number * M / numOfProducers) + i + 1;
                doStuff();
                //System.out.println("Producer "+number+" want to put " + numOfPortions + " portions to buffer");
                buffer.put(numOfPortions);
                //System.out.println("Producer "+number+" put " + numOfPortions + " portions to buffer");
            }
        }
    }

    private void doStuff(){
        try {
            Random rand = new Random();
            sleep(rand.nextInt(3000));
        }catch (InterruptedException e) {e.printStackTrace();}
    }
}
