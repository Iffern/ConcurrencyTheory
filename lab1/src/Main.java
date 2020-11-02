import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        task3();
    }

    public static void task1_2(boolean synchronize){
        Counter counter = new Counter();
        DecrementThread decrement = new DecrementThread(counter, synchronize);
        IncrementThread increment = new IncrementThread(counter, synchronize);
        decrement.start();
        increment.start();
        try{
            increment.join();
            decrement.join();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        System.out.println(counter.number);
    }

    public static void task3(){
        int numberOfThreads = 4;

        Buffer buffer = new Buffer();

        ArrayList<Thread> threadsPoll = new ArrayList<>();

        for(int i=0;i<numberOfThreads;i++){
            Consumer consumer = new Consumer(buffer, i);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            threadsPoll.add(consumerThread);
        }

        for(int i=0;i<numberOfThreads;i++){
            Producer producer = new Producer(buffer,i);
            Thread producerThread = new Thread(producer);
            producerThread.start();
            threadsPoll.add(producerThread);
        }

        for (Thread thread: threadsPoll) {
            try {
                thread.join();
            } catch (InterruptedException e){
                e.fillInStackTrace();
            }
        }
    }
}
