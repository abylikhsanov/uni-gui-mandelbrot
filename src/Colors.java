import java.awt.Color;
import java.awt.Point;

/** This is the class that extends Point, that sets the colors in a given location and given color.
 * @author abylikhsanov
 */

public class Colors extends Point{
    private Color c;

    /** Receives coordinates and the color. Sets in that location a given color.
     *
     * @param x
     * @param y
     * @param color
     */
    public Colors(int x, int y, Color color){
        c = color;
        this.setLocation(x,y);
    }

    /** @return the color.
     *
     * @return
     */
    public Color getC(){
        return c;
    }
}
