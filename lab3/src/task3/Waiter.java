package task3;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private final Lock lock = new ReentrantLock();
    private final Condition waitForPair = lock.newCondition();
    private final Condition waitForTable = lock.newCondition();

    private final Boolean[] waitingForPartner;
    private int peopleAtTable = 0;

    public Waiter(int numOfPairs){
        this.waitingForPartner = new Boolean[numOfPairs];
        Arrays.fill(this.waitingForPartner,false);
    }

    public void reserveTable(int pairNumber){
        lock.lock();
        try{
        if(!waitingForPartner[pairNumber]){
            waitingForPartner[pairNumber]=true;
            try {
                while(waitingForPartner[pairNumber]){
                    waitForPair.await();
                }
            }catch (InterruptedException e){e.printStackTrace();}
        }
        else{
            try{
                while(peopleAtTable>0){
                    waitForTable.await();
                }
            }catch (InterruptedException e){e.printStackTrace();}
            waitingForPartner[pairNumber] = false;
            waitForPair.signalAll();
            peopleAtTable +=2;
        }
        }finally {
            lock.unlock();
        }
    }

    public void freeTable(){
        lock.lock();
        try {
            peopleAtTable -= 1;
            if (peopleAtTable == 0) waitForTable.signal();
        }finally {
            lock.unlock();
        }
    }

}
