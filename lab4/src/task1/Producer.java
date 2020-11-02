package task1;

public class Producer extends MyThread{
    public Producer(BufferMonitor monitor, int threadNumber) {
        super(monitor, "Producer", threadNumber);
    }

    @Override
    public void doOperation(int i) {
        monitor.addOperation(i, threadNumber);
    }
}
