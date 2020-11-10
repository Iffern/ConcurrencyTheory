import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MandelbrotThread implements Callable<Integer> {
    private final BufferedImage I;
    private final ArrayList<Pixel> pixels;
    private int maxIter;

    public MandelbrotThread(BufferedImage I, ArrayList<Pixel> pixels, int numOfIter){
        this.I=I;
        this.pixels = pixels;
        this.maxIter = numOfIter;
    }

    @Override
    public Integer call(){
        for (Pixel pixel: pixels){
                int x=pixel.x;
                int y=pixel.y;
                double zy;
                double zx = zy = 0;
                double cX = (x - 400) / Mandelbrot.ZOOM;
                double cY = (y - 300) / Mandelbrot.ZOOM;
                int iter = maxIter;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 8));
            }
        return 0;
    }
}
