public class Counter {
    public int number;
    private BinarySemaphore semaphore;

    public Counter(){
        number=0;
        semaphore=new BinarySemaphore();
    }

    public void increment(){
        semaphore.P();
        number = number + 1;
        semaphore.V();
    }

    public void decrement(){
        semaphore.P();
        number = number - 1;
        semaphore.V();
    }
}
