import task1.BoundedBuffer;
import task1.Consumer;
import task1.Producer;
import task2.PrinterMonitor;
import task2.PrinterThread;
import task3.Customer;
import task3.Waiter;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        task3();
    }

    public static void task1(){
        int numberOfThreads = 4;

        BoundedBuffer buffer = new BoundedBuffer();

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

    public static void task2(){
        int numberOfPrinters = 5;
        int numberOfThreads = 12;

        ArrayList<PrinterThread> threads = new ArrayList<>();

        PrinterMonitor monitor = new PrinterMonitor(numberOfPrinters);

        for(int i=0;i<numberOfThreads;i++){
            PrinterThread thread = new PrinterThread(monitor);
            thread.start();
            threads.add(thread);
        }

        for(PrinterThread thread: threads){
            try{
                thread.join();
            }catch(InterruptedException e){e.printStackTrace();}
        }
    }

    public static void task3(){
        int numberOfPairs = 6;

        ArrayList<Customer> customers = new ArrayList<>();

        Waiter waiter = new Waiter(numberOfPairs);

        for(int i=0; i<numberOfPairs*2;i++){
            Customer customer = new Customer(i%numberOfPairs,waiter);
            customer.start();
            customers.add(customer);
        }

        for(Customer customer: customers){
            try {
                customer.join();
            }catch (InterruptedException e){e.printStackTrace();}
        }
    }
}
