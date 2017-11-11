import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class MandelbrotCalculator {
    // Initial parameter values
    public int ImageHeight = 400;
    public int ImageWidth = 400;
    protected double INITIAL_MIN_REAL = -2.0;
    protected double INITIAL_MAX_REAL = 0.7;
    protected double INITIAL_MIN_IMAGINARY = -1.25;
    protected double INITIAL_MAX_IMAGINARY = 1.25;
    protected static final int INITIAL_MAX_ITERATIONS = 50;
    protected double REAL_FACTOR = (INITIAL_MAX_REAL-INITIAL_MIN_REAL)/(ImageWidth-1);
    protected double IMAGINARY_FACTOR = (INITIAL_MAX_IMAGINARY-INITIAL_MIN_IMAGINARY)/(ImageHeight-1);
    private int zoom;
    private int color;

    public MandelbrotCalculator(){
        color = 5;
        zoom = 2;
    }

    /** This method actually implements the zoom function. We receive the click coordinates from the controller,
     * and check which mouse has been clicked. If left, then we zoom in, as zoomIn will be false. **/
    public void changeView(Point click, boolean zoomIn){
        double click_im = INITIAL_MAX_IMAGINARY-click.y * IMAGINARY_FACTOR;
        double click_real = INITIAL_MIN_REAL + click.x * REAL_FACTOR;
        double rdiff, imdiff;
        if(zoomIn){
            rdiff = (INITIAL_MAX_REAL-INITIAL_MIN_REAL)/zoom;
            imdiff = (INITIAL_MAX_IMAGINARY-INITIAL_MIN_IMAGINARY)/zoom;
        }
        else{
            rdiff = (INITIAL_MAX_REAL-INITIAL_MIN_REAL)*zoom;
            imdiff = (INITIAL_MAX_IMAGINARY-INITIAL_MIN_IMAGINARY)*zoom;
        }

        // As we have zoomed in or out, we have to change our initial size of the container:
        INITIAL_MIN_IMAGINARY = click_im - imdiff/2;
        INITIAL_MIN_REAL = click_real - rdiff/2;
        INITIAL_MAX_REAL = click_real + rdiff/2;
        setValues();

    }
    /** If the controller decides to change the size of the container, this function will be called.
     * It simply changes the container size in terms of width and height. **/
    public void setSize(int width, int height){
        ImageHeight = height;
        ImageWidth = width;
        setValues();
    }

    /** This function simply sets a new zoom factor. **/
    public void setZoom(int z){
        zoom = z;
    }

    /** For the debugging purpose, to check the zoom value. **/
    public int getZoom(){
        return zoom;
    }

    /** This function can change the color chosen by the user. **/
    public void setColor(int c){
        color = c;
    }

    /** Returns the color. **/
    public int getColor(){
        return color;
    }

    /** Reset the values **/
    public void reset(){
        INITIAL_MIN_REAL = -2.0;
        INITIAL_MAX_REAL = 0.7;
        INITIAL_MIN_IMAGINARY = -1.25;
        setValues();
    }

    /** To avoid the duplicate setting, this function allows to set the new values to the min/max (re/im) variables **/
    public void setValues(){

        INITIAL_MAX_IMAGINARY = INITIAL_MIN_IMAGINARY+(INITIAL_MAX_REAL-INITIAL_MIN_REAL);
        REAL_FACTOR = (INITIAL_MAX_REAL-INITIAL_MIN_REAL)/(ImageWidth-1);
        IMAGINARY_FACTOR = (INITIAL_MAX_IMAGINARY-INITIAL_MIN_IMAGINARY)/(ImageHeight-1);
    }

    public ArrayList<Colors> calculate(int MaxIterations)
    {
        ArrayList<Colors> ret = new ArrayList<Colors>();
        for(int y = 0; y < ImageHeight; y++)
        {
            double c_im = INITIAL_MAX_IMAGINARY - y * IMAGINARY_FACTOR;
            for(int x = 0; x < ImageWidth; x++)
            {
                double c_re = INITIAL_MIN_REAL + x * REAL_FACTOR;
                double Z_re = c_re, Z_im = c_im;
                int n;
                boolean partSet = true;
                double smoothcolor = -Math.exp(-(Z_re*Z_re+Z_im*Z_im));
                for(n = 0; n < MaxIterations; n++)
                {
                    double Z_re2 = Z_re*Z_re, Z_im2 = Z_im*Z_im;
                    if(Z_re2 + Z_im2 > 4)
                    {
                        partSet = false;
                        break;
                    }
                    Z_im = 2*Z_re*Z_im + c_im;
                    Z_re = Z_re2 - Z_im2 + c_re;
                    Z_re2 = Z_re*Z_re; Z_im2 = Z_im*Z_im;
                    smoothcolor += Math.exp(-(Z_re2+Z_im2));
                }
                Color color = Color.getHSBColor((float)(.9f * (getColor() + smoothcolor/MaxIterations)),1.0f,1.0f);
                if(partSet) color = Color.black;
                ret.add(new Colors(x, y,color));
            }
        }
        return ret;
    }
}
