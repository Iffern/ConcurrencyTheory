package task2;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
    private final Lock lock = new ReentrantLock();
    private final Condition noPrinters = lock.newCondition();
    private Boolean[] printers;
    private int availablePrinters;

    public PrinterMonitor(int numberOfPrinters){
        this.printers = new Boolean[numberOfPrinters];
        Arrays.fill(this.printers, true);
        this.availablePrinters = numberOfPrinters;
    }

    public int reservePrinter() throws InterruptedException{
        lock.lock();
        try {
            while (availablePrinters <= 0) {
                noPrinters.await();
            }
            int number = getPrinterNumber();
            printers[number] = false;
            availablePrinters-=1;
            return number;
        }
        finally {
            lock.unlock();
        }
    }

    public void freePrinter(int printerNumber){
        lock.lock();
        printers[printerNumber] = true;
        availablePrinters++;
        noPrinters.signal();
        lock.unlock();
    }

    private int getPrinterNumber(){
        for(int i=0;i<printers.length;i++){
            if (printers[i]) return i;
        }
        return -1;
    }
}
