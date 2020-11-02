package task3;

import java.util.Random;

public class Customer extends Thread{
    int pairNumber;
    Waiter waiter;

    public Customer(int pairNumber, Waiter waiter){
        this.pairNumber = pairNumber;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        while(true){
            doStuff();
            waiter.reserveTable(pairNumber);
            System.out.println("Pair number "+pairNumber+" is eating now");
            doStuff();
            waiter.freeTable();
        }
    }

    private void doStuff(){
        Random rand = new Random();
        try {
            sleep(rand.nextInt((pairNumber+1)*1000));
        }catch (InterruptedException e){e.printStackTrace();}
    }
}
