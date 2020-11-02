public class CountingSemaphore {
    private int counter;

    public CountingSemaphore(int maximum){
        this.counter=maximum;
    }

    public synchronized void P(){
        while(counter<=0) {
            try{
                wait();
            }catch (InterruptedException e){e.fillInStackTrace();}
        }
        counter-=1;
    }

    public synchronized void V(){
        counter+=1;
        notifyAll();
    }

    public int getCounter() {
        return counter;
    }
}
