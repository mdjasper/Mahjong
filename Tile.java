/*
 * Tile.java
 *
 * Parent class for all tiles
 * Michael Jasper
 */

//package Mahjong;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;

abstract public class Tile extends JPanel {

    private int x, y, z;
    private ArrayList<Polygon> shadow;
    private boolean clicked;
    
    private GradientPaint currentColor = Config.defaultColor;

    public abstract Tile copy();

    public void setCoords(int[] c){
        this.x = c[0];
        this.y = c[1];
        this.z = c[2];
    }
    public int[] getCoords(){
        int[] c = {x,y,z};
        return c;
    }
    public void setClicked(boolean clicked){
        this.clicked = clicked;
    }
    @Override public void paintComponent(Graphics g){
        //Set Size
        setSize(Config.TILE_WIDTH,Config.TILE_HEIGHT);

        Graphics2D g2 = (Graphics2D)g;

        //Left
        Polygon left = new Polygon();
        left.addPoint(20, 10);
        left.addPoint(10, 30);
        left.addPoint(10, 90);
        left.addPoint(20, 70);
        g2.setPaint(Color.decode("#483D0F"));
        g2.fillPolygon(left);

        //Bottom
        Polygon bottom = new Polygon();
        bottom.addPoint(15, 80);
        bottom.addPoint(10, 90);
        bottom.addPoint(80, 90);
        bottom.addPoint(85, 80);
        Color s1 = Color.decode("#483D0F");
        Color e = Color.decode("#272108");
        g2.setPaint(new GradientPaint(0,0,s1,75,0,e,true));
        g2.fillPolygon(bottom);


        //Middle-Left
        Polygon ml = new Polygon();
        ml.addPoint(20, 10);
        ml.addPoint(15, 20);
        ml.addPoint(15, 80);
        ml.addPoint(20, 70);
        g2.setPaint(Color.decode("#C6C374"));
        g2.fillPolygon(ml);


        //Middle-bottom
        Polygon mb = new Polygon();
        mb.addPoint(20, 70);
        mb.addPoint(15, 80);
        mb.addPoint(85, 80);
        mb.addPoint(90, 70);

        s1 = Color.decode("#C6C374");
        e = Color.decode("#AA9D45");
        g2.setPaint(new GradientPaint(0,0,s1,85,0,e,true));

        g2.fillPolygon(mb);


        //top layer
       
        g2.setPaint(clicked ? Config.clickedColor : Config.defaultColor);
        Rectangle2D top = new Rectangle2D.Double(20, 10, 70, 60);
        //new RoundRectangle2D.Double(x, y, rectwidth,rectheight,10, 10));
        //RoundRectangle2D top = new RoundRectangle2D.Double(20, 10, 70, 60,10,10);
        g2.fill(top);

        //Draw Shadow
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
	g2.setComposite(composite);
        g2.setColor(Color.black);
        for (Polygon p : shadow){
            g2.fillPolygon(p);
        }
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
	g2.setComposite(composite); // Set current alpha

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    }
    @Override public Dimension getMinimumSize()
    { return new Dimension(Config.TILE_WIDTH,Config.TILE_HEIGHT); }

    @Override public Dimension getPreferredSize()
    { return getMinimumSize(); }

    public boolean matches(Tile other){
        //If they are an instance of the same object, return true
        if(this.getClass() == other.getClass()) return true;
        //Otherwise, return false
        return false;
    }

    public void setShadow(ArrayList shadow){
        this.shadow = shadow;
    }

}
