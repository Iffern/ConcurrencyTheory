import com.sun.net.httpserver.Headers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    public static final int MAX_ITER = 570;
    public static final double ZOOM = 150;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private BufferedImage I;

    public Mandelbrot(int numOfThreads, int numOfTasks) {
        super("Mandelbrot Set");
        setBounds(100, 100, WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        paintMandelbrot(numOfThreads, numOfTasks);
    }

    private void paintMandelbrot(int numOfThreads, int numOfTasks){

        if(numOfTasks == WIDTH*HEIGHT) taskForEveryPixel(numOfThreads);
        else forNumberOfTasks(numOfThreads, numOfTasks);
    }

    private void taskForEveryPixel(int numOfThreads){

    }

    private void forNumberOfTasks(int numOfThreads, int numOfTasks){

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
