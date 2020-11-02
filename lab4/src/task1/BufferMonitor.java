package task1;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferMonitor {
    private Integer[] buffer;
    public int N;
    private int numOfThreads;
    private Lock lock = new ReentrantLock();
    private Condition[] conditions;
    private Integer[] canChange;

    public BufferMonitor(int N, int numOfThreads){
        this.N=N;
        this.numOfThreads = numOfThreads;
        buffer = new Integer[N];
        Arrays.fill(buffer,-1);
        conditions = new Condition[numOfThreads];
        Arrays.fill(conditions, lock.newCondition());
        canChange = new Integer[N];
        Arrays.fill(canChange, 0);
    }

    public void addOperation(int i, int threadNumber){
        lock.lock();
        while(canChange[i]!=threadNumber){
            try {
                conditions[threadNumber].await();
            } catch (InterruptedException e) {e.printStackTrace();}
        }
        buffer[i]+=1;
        printBuffer();
        canChange[i]=(canChange[i]+1)%numOfThreads;
        conditions[(threadNumber+1)%numOfThreads].signal();
        lock.unlock();
    }

    public void consumerOperation(int i, int threadNumber){
        lock.lock();
        while(canChange[i]!=threadNumber){
            try {
                conditions[threadNumber].await();
            } catch (InterruptedException e) {e.printStackTrace();}
        }
        buffer[i]=-1;
        printBuffer();
        canChange[i] = (canChange[i]+1)%numOfThreads;
        conditions[(threadNumber+1)%numOfThreads].signal();
        lock.unlock();
    }

    private void printBuffer(){
        for(int i=0;i<N;i++){
            System.out.print(buffer[i]+"|");
        }
        System.out.println();
    }
}
