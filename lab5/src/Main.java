import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        timeMeasurement();
    }

    public static void timeMeasurement() {
        int[] numOfThreads = {1, 4, 8, 16};
        int[] numOfTasks = {1, 10};
        int[] numOfIterations = {500, 1000, 10000};

        File summary = new File("summary.txt");
        try {
            if(summary.createNewFile()) System.out.println("File created");
        }catch (IOException e){e.printStackTrace();}

        FileWriter writer = null;
        try {
            writer = new FileWriter("summary.txt");

            writer.write("Number of threads | Number of tasks | Number of iterations " +
                    "| Average time | Standard derivation\n");
            writer.write("--------------------------------------------------------------" +
                    "---------------------------------\n");
        }catch(IOException e){e.printStackTrace();}

        for (int iteration : numOfIterations) {
            for (int threadNumber : numOfThreads) {
                for (int taskMultiplicity : numOfTasks) {
                    int taskNum = threadNumber * taskMultiplicity;
                    writeToFile(writer, threadNumber, iteration, taskNum);
                }
                int taskNum = Mandelbrot.HEIGHT*Mandelbrot.WIDTH;
                writeToFile(writer, threadNumber, iteration, taskNum);
            }
        }

        try {
            writer.close();
        } catch(IOException e){e.printStackTrace();}
    }

    private static void writeToFile(FileWriter writer, int threadNumber, int iteration, int taskNum) {
        ArrayList<Long> times = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Mandelbrot mandelbrot = new Mandelbrot(iteration);
            long startTime = System.nanoTime();
            mandelbrot.paintMandelbrot(threadNumber, taskNum);
            long endTime = System.nanoTime();
            times.add(endTime-startTime);
        }
        System.out.printf("%18d|%17d|%22d|%20d|%20f\n", threadNumber, taskNum, iteration,
                calculateAvg(times), calculateSD(times));
        try {
            writer.write(String.format("%18d|%17d|%22d|%14d|%20.2f\n", threadNumber, taskNum, iteration,
                    calculateAvg(times), calculateSD(times)));
        }catch (IOException e){e.printStackTrace();}
    }


    private static long calculateAvg(ArrayList<Long> times){
        long sum = 0;
        for(long time:times){
            sum += time;
        }

        return sum/times.size();
    }

    private static double calculateSD(ArrayList<Long> times){
        double sd = 0.0;
        double avg = calculateAvg(times);
        for(long time:times){
            sd += Math.pow(time - avg, 2);
        }
        return Math.sqrt(sd/times.size());
    }

}
