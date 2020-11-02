public class IncrementThread extends Thread{
    Counter counter;
    boolean synchronize;

    public IncrementThread(Counter counter, boolean synchronize) {
        super();
        this.counter=counter;
        this.synchronize=synchronize;
    }

    @Override
    public void run() {
        super.run();
        for(int i=0;i<100000000;i++) {
            if(synchronize) {
                synchronized (counter) {
                    counter.increment();
                }
            }
            else counter.increment();
        }
    }
}
