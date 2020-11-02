package task2;

import java.util.Random;

public class PrinterThread extends Thread{
    PrinterMonitor monitor;

    public PrinterThread(PrinterMonitor monitor){
        this.monitor=monitor;
    }

    @Override
    public void run() {
        while(true) {
            int numberOfPrinter = -1;
            doStuff();
            try {
                numberOfPrinter = monitor.reservePrinter();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            print(numberOfPrinter);
            monitor.freePrinter(numberOfPrinter);
        }
    }

    private void print(int numberOfPrinter){
        System.out.println("I'm printing on printer number "+numberOfPrinter);
        doStuff();
        System.out.println("Finished printing on printer number "+numberOfPrinter);
    }
    
    private void doStuff(){
        Random rand = new Random();
        try {
            sleep(rand.nextInt(5000));
        }catch (InterruptedException e){e.printStackTrace();}
    }
}
