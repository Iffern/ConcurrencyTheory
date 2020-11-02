package task2;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RegularBuffer implements Buffer{
    private Integer[] buffer;
    private Lock lock = new ReentrantLock();
    private Condition producerCond = lock.newCondition();
    private Condition consumerCond = lock.newCondition();
    private int sumOfPortions = 0;
    private int prod_idx = 0;
    private int cons_idx = 0;
    private final int bufferLength;
    //attributes to measure time
    private Lock timeLock = new ReentrantLock();
    public TimeMeasure timeGet;
    public TimeMeasure timePut;

    public RegularBuffer(int M){
        this.buffer = new Integer[M];
        this.bufferLength = M;
        Arrays.fill(buffer,0);
        this.timeGet = new TimeMeasure(M/2);
        this.timePut = new TimeMeasure(M/2);
    }

    public void put(int numOfPortions){
        /*Long startTime = System.nanoTime();*/
        lock.lock();
        while((bufferLength-sumOfPortions)<numOfPortions){
            try{
                producerCond.await();
            } catch(InterruptedException e){e.printStackTrace();}
        }
        for(int i=0;i<numOfPortions;i++){
            buffer[(prod_idx+i)%bufferLength] = 1;
        }
        //printBuffer();
        prod_idx=(prod_idx+numOfPortions)%bufferLength;
        sumOfPortions += numOfPortions;
        consumerCond.signalAll();
        lock.unlock();
        /*Long endTime = System.nanoTime();
        timeLock.lock();
        timePut.modifyNumberOfSurveys(numOfPortions-1,1);
        timePut.countNewAvgTime(numOfPortions,endTime-startTime);
        timeLock.unlock();*/
    }

    public void get(int numOfPortions){
        /*Long startTime = System.nanoTime();*/
        lock.lock();
        while(sumOfPortions<numOfPortions){
            try{
                consumerCond.await(15, TimeUnit.SECONDS);
            }catch (InterruptedException e){e.printStackTrace();}
        }
        for(int i=0;i<numOfPortions;i++){
            buffer[(cons_idx+i)%bufferLength] = 0;
        }
        //printBuffer();
        cons_idx=(cons_idx+numOfPortions)%bufferLength;
        sumOfPortions -= numOfPortions;
        producerCond.signalAll();
        lock.unlock();
        /*Long endTime = System.nanoTime();
        timeLock.lock();
        timeGet.modifyNumberOfSurveys(numOfPortions-1,-1);
        timeGet.countNewAvgTime(numOfPortions,endTime-startTime);
        timeLock.unlock();*/
    }

    private void printBuffer(){
        for(int i=0;i<bufferLength;i++){
            System.out.print(buffer[i]+"|");
        }
        System.out.println();
    }
}
