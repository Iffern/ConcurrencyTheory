package task1;

public class Processor extends MyThread{

    public Processor(BufferMonitor monitor, char letter, int threadNumber) {
        super(monitor, "Processor "+letter, threadNumber);
    }

    @Override
    public void doOperation(int i) {
        monitor.addOperation(i, threadNumber);
    }
}
