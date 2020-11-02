package task1;

import java.util.Random;

public abstract class MyThread extends Thread{
    BufferMonitor monitor;
    String threadName;
    int threadNumber;

    public MyThread(BufferMonitor monitor,String threadName, int threadNumber){
        this.monitor = monitor;
        this.threadName = threadName;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        int i=0;
        while(true){
            doStuff();
            doOperation(i);
            System.out.println("Thread "+threadName+" did operation");
            i= (i+1)%monitor.N;
        }
    }

    private void doStuff(){
        try {
            Random rand = new Random();
            sleep(rand.nextInt((threadNumber+1)*1000));
        }catch (InterruptedException e) {e.printStackTrace();}
    }

    public abstract void doOperation(int i);
}
