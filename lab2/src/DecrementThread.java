public class DecrementThread extends Thread{
    Counter counter;

    public DecrementThread(Counter counter){
        super();
        this.counter=counter;
    }

    @Override
    public void run() {
        super.run();
        for(int i=0;i<1000000;i++){
                counter.decrement();
        }

    }
}
