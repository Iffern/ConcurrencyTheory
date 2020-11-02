import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        task2();
    }

    public static void task1(){
        Counter counter = new Counter();
        DecrementThread decrementThread = new DecrementThread(counter);
        IncrementThread incrementThread = new IncrementThread(counter);

        decrementThread.start();
        incrementThread.start();

        try {
            decrementThread.join();
            incrementThread.join();
        }catch (InterruptedException e){e.fillInStackTrace();}

        System.out.println(counter.number);
    }

    public static void task2(){
        int numOfCarts=6;
        int numOfCustomers=13;

        Shop shop = new Shop(numOfCarts);
        LinkedList<Customer> customers = new LinkedList<>();
        for(int i=0;i<numOfCustomers;i++){
            Customer customer = new Customer(i,shop);
            customers.add(customer);
            customer.start();
        }

        for (Customer customer: customers) {
            try {
                customer.join();
            }catch (InterruptedException e) {e.fillInStackTrace();}
        }
    }
}
