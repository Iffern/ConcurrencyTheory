package task3;

import task2.Buffer;
import task2.TimeMeasure;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ImprovedBuffer implements Buffer {
    private Integer[] buffer;
    private Lock lock = new ReentrantLock();
    private Condition producerCond = lock.newCondition();
    private Condition consumerCond = lock.newCondition();
    private Condition firstProducer = lock.newCondition();
    private Condition firstConsumer = lock.newCondition();
    private boolean isFirstProducerWaiting = false;
    private boolean isFirstConsumerWaiting = false;
    private int sumOfPortions = 0;
    private int prod_idx = 0;
    private int cons_idx = 0;
    private final int bufferLength;
    //attributes to measure time
    private Lock timeLock = new ReentrantLock();
    public TimeMeasure timeGet;
    public TimeMeasure timePut;

    public ImprovedBuffer(int M){
        this.buffer = new Integer[M];
        this.bufferLength = M;
        Arrays.fill(buffer,0);
        this.timeGet = new TimeMeasure(M/2);
        this.timePut = new TimeMeasure(M/2);
    }

    public void put(int numOfPortions){
        /*Long startTime = System.nanoTime();*/
        lock.lock();
        while(isFirstProducerWaiting){
            try{
                producerCond.await();
            } catch (InterruptedException e){e.printStackTrace();}
        }
        while((bufferLength-sumOfPortions)<numOfPortions){
            isFirstProducerWaiting = true;
            try{
                firstProducer.await();
            } catch(InterruptedException e){e.printStackTrace();}
        }
        for(int i=0;i<numOfPortions;i++){
            buffer[(prod_idx+i)%bufferLength] = 1;
        }
        //printBuffer();
        prod_idx=(prod_idx+numOfPortions)%bufferLength;
        sumOfPortions += numOfPortions;
        isFirstProducerWaiting = false;
        producerCond.signalAll();
        firstConsumer.signal();
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
        while(isFirstConsumerWaiting){
            try{
                consumerCond.await();
            }catch (InterruptedException e){e.printStackTrace();}
        }
        while(sumOfPortions<numOfPortions){
            isFirstConsumerWaiting = true;
            try{
                firstConsumer.await();
            }catch (InterruptedException e){e.printStackTrace();}
        }
        for(int i=0;i<numOfPortions;i++){
            buffer[(cons_idx+i)%bufferLength] = 0;
        }
        //printBuffer();
        cons_idx=(cons_idx+numOfPortions)%bufferLength;
        sumOfPortions -= numOfPortions;
        isFirstConsumerWaiting = false;
        consumerCond.signalAll();
        firstProducer.signal();
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
