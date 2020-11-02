package task3;

import task2.Buffer;

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
    public Double[] timesAvgGet;
    private Integer[] numOfSurveysGet;
    public Double[] timesAvgPut;
    private Integer[] numOfSurveysPut;

    public ImprovedBuffer(int M){
        this.buffer = new Integer[M];
        this.bufferLength = M;
        Arrays.fill(buffer,0);
        timesAvgGet = new Double[M/2];
        numOfSurveysGet = new Integer[M/2];
        timesAvgPut = new Double[M/2];
        numOfSurveysPut = new Integer[M/2];
        Arrays.fill(timesAvgGet, 0.0);
        Arrays.fill(numOfSurveysGet,0);
        Arrays.fill(timesAvgPut, 0.0);
        Arrays.fill(numOfSurveysPut,0);
    }

    public void put(int numOfPortions){
        Long startTime = System.nanoTime();
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
        Long endTime = System.nanoTime();
        timeLock.lock();
        numOfSurveysPut[numOfPortions-1]+=1;
        countNewAvgTime(numOfPortions,endTime-startTime, false);
        timeLock.unlock();
    }

    public void get(int numOfPortions){
        Long startTime = System.nanoTime();
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
        Long endTime = System.nanoTime();
        timeLock.lock();
        numOfSurveysGet[numOfPortions-1]+=1;
        countNewAvgTime(numOfPortions,endTime-startTime, true);
        timeLock.unlock();
    }

    private void printBuffer(){
        for(int i=0;i<bufferLength;i++){
            System.out.print(buffer[i]+"|");
        }
        System.out.println();
    }

    private void countNewAvgTime(int numOfPortion, long time, boolean isItGet){
        if(isItGet){
            double lastAvg = timesAvgGet[numOfPortion-1];
            if(numOfSurveysGet[numOfPortion-1]==1) timesAvgGet[numOfPortion-1] = (double) time;
            else{
                timesAvgGet[numOfPortion-1] =
                        ((numOfSurveysGet[numOfPortion-1]-1)*lastAvg + time)/numOfSurveysGet[numOfPortion-1];
            }
        }
        else{
            double lastAvg = timesAvgPut[numOfPortion-1];
            if(numOfSurveysPut[numOfPortion-1]==1) timesAvgPut[numOfPortion-1] = (double) time;
            else{
                timesAvgPut[numOfPortion-1] =
                        ((numOfSurveysPut[numOfPortion-1]-1)*lastAvg + time)/numOfSurveysPut[numOfPortion-1];
            }
        }
    }
}
