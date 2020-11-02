public class IncrementThread extends Thread{
    Counter counter;

    public IncrementThread(Counter counter) {
        super();
        this.counter=counter;
    }

    @Override
    public void run() {
        super.run();
        for(int i=0;i<1000000;i++) {
                counter.increment();
        }
    }
}
