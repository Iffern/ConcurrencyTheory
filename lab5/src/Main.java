import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Mandelbrot mandelbrot = new Mandelbrot(500);
        mandelbrot.paintMandelbrot(8,8);
        mandelbrot.setVisible(true);
    }

    public static void timeMeasurement() {
        int[] numOfThreads = {1, 4, 8, 16};
        int[] numOfTasks = {1, 10};
        int[] numOfIterations = {500, 1000, 10000};

        File summary = new File("summary.txt");
        if(summary.delete()) System.out.println("File deleted");
        try {
            if(summary.createNewFile()) System.out.println("File created");
        }catch (IOException e){e.printStackTrace();}

        FileWriter writer = null;
        try {
            writer = new FileWriter("summary.txt");

            writer.write("Number of threads | Number of tasks | Number of iterations " +
                    "| Average time in ms | Standard derivation in ms\n");
            writer.write("--------------------------------------------------------------" +
                    "---------------------------------------------\n");
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
            try {
                writer.write("--------------------------------------------------------------" +
                        "---------------------------------------------\n");
            }catch (IOException e){e.printStackTrace();}
        }

        try {
            writer.close();
        } catch(IOException e){e.printStackTrace();}
    }

    private static void writeToFile(FileWriter writer, int threadNumber, int iteration, int taskNum) {
        double nanoToMili = 1./1000000;

        ArrayList<Long> times = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Mandelbrot mandelbrot = new Mandelbrot(iteration);
            long startTime = System.nanoTime();
            mandelbrot.paintMandelbrot(threadNumber, taskNum);
            long endTime = System.nanoTime();
            times.add(endTime-startTime);
        }
        System.out.printf("%18d|%17d|%22d|%20.2f|%26.2f\n", threadNumber, taskNum, iteration,
                calculateAvg(times)*nanoToMili, calculateSD(times)*nanoToMili);
        try {
            writer.write(String.format("%18d|%17d|%22d|%20.2f|%26.2f\n", threadNumber, taskNum, iteration,
                    calculateAvg(times)*nanoToMili, calculateSD(times)*nanoToMili));
        }catch (IOException e){e.printStackTrace();}
    }


    private static double calculateAvg(ArrayList<Long> times){
        long sum = 0;
        for(long time:times){
            sum += time;
        }

        return (double) sum/(times.size());
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
