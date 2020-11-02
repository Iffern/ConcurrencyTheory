public class BinarySemaphore {
    private boolean binarySemaphore;

    public BinarySemaphore(){
        binarySemaphore=true;
    }

    public synchronized void P(){
        while(!binarySemaphore) {
            try{
                wait();
            }catch (InterruptedException e){e.fillInStackTrace();}
        }
        binarySemaphore=false;
    }

    public synchronized void V(){
        binarySemaphore=true;
        notifyAll();
    }
}
