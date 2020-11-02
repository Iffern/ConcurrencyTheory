package task1;

public class Consumer extends MyThread{

    public Consumer(BufferMonitor monitor, int threadNumber) {
        super(monitor, "Consumer",threadNumber);
    }

    @Override
    public void doOperation(int i) {
        monitor.consumerOperation(i, threadNumber);
    }
}
