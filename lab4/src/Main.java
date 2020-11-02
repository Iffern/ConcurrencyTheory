import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import task1.*;
import task2.RegularBuffer;
import task3.ImprovedBuffer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        task3();
    }

    public static void task1(){
        int N = 20;
        int numOfThreads = 7;
        BufferMonitor monitor = new BufferMonitor(N,numOfThreads);
        Producer producer = new Producer(monitor,0);
        Consumer consumer = new Consumer(monitor,6);

        ArrayList<MyThread> threads = new ArrayList<>();

        threads.add(producer);
        threads.add(consumer);

        int i = 1;
        for (char j ='A';j<'F';j++){
            Processor processor = new Processor(monitor,j,i);
            i++;
            threads.add(processor);
        }

        for(MyThread thread: threads){
            thread.start();
        }

        for(MyThread thread: threads){
            try{
                thread.join();
            }catch (InterruptedException e){e.printStackTrace();}
        }
    }

    public static void task2() throws IOException {
        int M=10000;
        int C=100;
        int P=100;
        RegularBuffer regularBuffer = new RegularBuffer(2*M);

        ArrayList<Thread> threads = new ArrayList<>();

        for(int i=0;i<C;i++){
            task2.Consumer consumer = new task2.Consumer(M, regularBuffer,i, C, M);
            threads.add(consumer);
        }

        for(int i=0;i<P;i++){
            task2.Producer producer = new task2.Producer(M, regularBuffer,i, P, M);
            threads.add(producer);
        }

        for(Thread thread: threads){
            thread.start();
        }

        for(Thread thread:threads){
            try{
                thread.join();
            }catch (InterruptedException e){e.printStackTrace();}
        }

       /* DefaultCategoryDataset datasetGet = new DefaultCategoryDataset();
        for(int i=0;i<M;i++){
            datasetGet.setValue( regularBuffer.timeGet.getTimeAtIndex(i), "Czas w nanosekundach", "P:"+(i+1));
        }

        JFreeChart charGet = ChartFactory.createBarChart("Czas uzyskania porcji o danym rozmiarze w nanosekundach",
                "Rozmiar porcji", "Czas w nanosekundach", datasetGet, PlotOrientation.VERTICAL,
                false, true, false);
        ChartUtilities.saveChartAsPNG(new File("get10000Regular.png"),charGet,1920,1080);

        DefaultCategoryDataset datasetPut = new DefaultCategoryDataset();
        for(int i=0;i<M;i++){
            datasetPut.setValue( regularBuffer.timePut.getTimeAtIndex(i), "Czas w nanosekundach", "P:"+(i+1));
        }

        JFreeChart charPut = ChartFactory.createBarChart("Czas włożenia porcji o danym rozmiarze w nanosekundach",
                "Rozmiar porcji", "Czas w nanosekundach", datasetPut, PlotOrientation.VERTICAL,
                false, true, false);
        ChartUtilities.saveChartAsPNG(new File("put10000Regular.png"),charPut,1920,1080);*/
    }

    public static void task3() throws IOException {
        int M=10000;
        int C=100;
        int P=100;
        ImprovedBuffer buffer = new ImprovedBuffer(2*M);

        ArrayList<Thread> threads = new ArrayList<>();

        for(int i=0;i<C;i++){
            task2.Consumer consumer = new task2.Consumer(M,buffer,i,C,M);
            threads.add(consumer);
        }

        for(int i=0;i<P;i++){
            task2.Producer producer = new task2.Producer(M,buffer,i,P,M);
            threads.add(producer);
        }

        for(Thread thread: threads){
            thread.start();
        }

        for(Thread thread:threads){
            try{
                thread.join();
            }catch (InterruptedException e){e.printStackTrace();}
        }

        /*DefaultCategoryDataset datasetGet = new DefaultCategoryDataset();
        for(int i=0;i<M;i++){
            datasetGet.setValue( buffer.timeGet.getTimeAtIndex(i), "Czas w nanosekundach", "P:"+(i+1));
        }

        JFreeChart charGet = ChartFactory.createBarChart("Czas uzyskania porcji o danym rozmiarze w nanosekundach",
                "Rozmiar porcji", "Czas w nanosekundach", datasetGet, PlotOrientation.VERTICAL,
                false, true, false);
        ChartUtilities.saveChartAsPNG(new File("get10000Improved.png"),charGet,1920,1080);

        DefaultCategoryDataset datasetPut = new DefaultCategoryDataset();
        for(int i=0;i<M;i++){
            datasetPut.setValue( buffer.timePut.getTimeAtIndex(i), "Czas w nanosekundach", "P:"+(i+1));
        }

        JFreeChart charPut = ChartFactory.createBarChart("Czas włożenia porcji o danym rozmiarze w nanosekundach",
                "Rozmiar porcji", "Czas w nanosekundach", datasetPut, PlotOrientation.VERTICAL,
                false, true, false);
        ChartUtilities.saveChartAsPNG(new File("put10000Improved.png"),charPut,1920,1080);*/
    }
}
