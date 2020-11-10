import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.*;

public class Mandelbrot extends JFrame {

    public static final double ZOOM = 150;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public int maxIter = 500;
    private BufferedImage I;
    ExecutorService pool;
    Set<Future<Integer>> set;

    public Mandelbrot(int numOfIter) {
        super("Mandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        this.maxIter = numOfIter;
    }

    public void paintMandelbrot(int numOfThreads, int numOfTasks){
        this.pool = Executors.newFixedThreadPool(numOfThreads);
        this.set = new HashSet<>();

        if(numOfTasks == WIDTH*HEIGHT) taskForEveryPixel();
        else forNumberOfTasks(numOfTasks);

        try {
            for (Future<Integer> future : set)
                future.get();
        }catch (ExecutionException | InterruptedException e){e.printStackTrace();}

        pool.shutdown();
    }

    private void taskForEveryPixel(){
        for(int y=0;y<HEIGHT;y++){
            for(int x=0;x<WIDTH;x++) {
                ArrayList<Pixel> pixel = new ArrayList<>();
                pixel.add(new Pixel(x,y));
                MandelbrotThread thread = new MandelbrotThread(I, pixel, maxIter);
                Future<Integer> future = pool.submit(thread);
                set.add(future);
            }
        }
    }

    private void forNumberOfTasks(int numOfTasks){
        int pixelsPerTask = HEIGHT*WIDTH/numOfTasks;
        int leftovers = HEIGHT*WIDTH%numOfTasks;

        int pixelsForCurrentTaskLeft = pixelsPerTask;
        int tasksLeft = numOfTasks;

        ArrayList<Pixel> pixels = new ArrayList<>();

        for(int y=0;y<HEIGHT;y++){
            for(int x=0;x<WIDTH;x++) {
                pixels.add(new Pixel(x,y));
                pixelsForCurrentTaskLeft--;
                if(pixelsForCurrentTaskLeft==0) {
                    tasksLeft--;

                    pixelsForCurrentTaskLeft=pixelsPerTask;
                    if(tasksLeft==1){
                        pixelsForCurrentTaskLeft+=leftovers;
                    }

                    MandelbrotThread thread = new MandelbrotThread(I, pixels, maxIter);
                    Future<Integer> future = pool.submit(thread);
                    set.add(future);

                    pixels = new ArrayList<>();
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
