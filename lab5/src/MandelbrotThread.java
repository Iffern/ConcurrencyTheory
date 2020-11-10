import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class MandelbrotThread implements Callable<Integer> {
    private final int x0, x1, y0, y1;
    private final BufferedImage I;

    public MandelbrotThread(int x0, int y0, int x1, int y1, BufferedImage I){
        this.x0=x0;
        this.y0=y0;
        this.x1=x1;
        this.y1=y1;
        this.I=I;
    }

    @Override
    public Integer call(){
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                double zy;
                double zx = zy = 0;
                double cX = (x - 400) / Mandelbrot.ZOOM;
                double cY = (y - 300) / Mandelbrot.ZOOM;
                int iter = Mandelbrot.MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 8));
            }
        }
        return 0;
    }
}
