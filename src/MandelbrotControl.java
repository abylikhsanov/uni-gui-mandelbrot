import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

/** This is the controller for the Mandelbrot.
 * @author abylikhsanov
 *
 */

public class MandelbrotControl extends JPanel implements MouseListener {

    private MandelbrotCalculator mc = new MandelbrotCalculator();
    private int MAX_VAL = 20;
    private Image img;
    private ArrayList<Colors> set;

    public MandelbrotControl(){

        this.addMouseListener(this);
    }

    /** @return the maximum value for the iterations in generating the color set.
     *
     */
    public int getMAX_VAL(){
        return MAX_VAL;
    }

    /** Sets the MAX_VAL.
     *
     * @param max
     */
    public void setMAX_VAL(int max){
        MAX_VAL = max;
    }


    /** Sets the color.
     *
     * @param c
     */
    public void setColor(int c){
        mc.setColor(c);
    }

    public void setZoom(int z){
        mc.setZoom(z);
    }

    public int getZoom(){
        return mc.getZoom();
    }

    public void setImgW(int width, int height){
        mc.setSize(width,height);
    }

    public int getImgW(){
        return mc.ImageWidth;
    }

    public int getImgH(){
        return mc.ImageHeight;
    }

    /** Return the color.
     *
     * @return mc.getColor();
     */
    public int getColor(){
        return mc.getColor();
    }

    public void draw(Graphics graphics){
        img = setImg();
        graphics.drawImage(img,0,0,null);
        graphics.drawLine(0,getHeight()-1,getWidth(),getHeight()-1);
    }

    /** Returns the current Image.
     * @return img
     */
    public Image getImg(){
        return img;
    }

    /** Creates a new image with specified color in a location.
     *
     * @return bi (new Image).
     */
    private Image setImg(){
        BufferedImage bi = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D)bi.createGraphics();
        for(Colors color : set){ // Iterates through every set and sets the color within a location.
            graphics2D.setColor(color.getC());
            graphics2D.drawRect(color.x,color.y,1,1);
        }
        return bi;
    }

    /** Whenever the user presses left or right click on the mouse, we notify this to our model and send,
     * wether to zoom in or out. Left click is zoom in, right click is zoom out.
     * @param click
     */
    public void mouseClicked(MouseEvent click){
        if(click.getButton() == MouseEvent.BUTTON1){
            mc.changeView(click.getPoint(),true);
        }
        else if(click.getButton() == MouseEvent.BUTTON3){
            mc.changeView(click.getPoint(),false);
        }
        else{
            System.out.println("Not left nor right mouse has been clicked");
            return;
        }

        set = calculate();
        this.repaint();
    }

    /** This functions calculates the color set and repaints it.
     */
    public void render(){
        set = calculate();
        this.repaint();
    }

    /** Returns all the color coordinates that need to be rendered.
     *
     * @return temp
     */
    private ArrayList<Colors> calculate(){
        ArrayList<Colors> temp = mc.calculate(MAX_VAL);
        return temp;
    }

    public void reset(){
        mc.reset();
        render();
    }

    /** Overriding the superclass methods, we do not need them, therefore, they are empty */
    public void mouseEntered(MouseEvent arg0) {}
    public void mouseExited(MouseEvent arg0) {}
    public void mousePressed(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
}
