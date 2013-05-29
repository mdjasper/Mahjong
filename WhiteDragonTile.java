/*
 * WhiteDragon.java
 *
 * Creates white-dragon tile
 * (Dashed border tile)
 * Michael Jasper
 */

//package Mahjong;

import java.awt.*;

public class WhiteDragonTile extends Tile{
    public WhiteDragonTile(){
        setToolTipText(toString());
    }

    public WhiteDragonTile copy(){
        return new WhiteDragonTile();
    }

    @Override public final String toString(){
        return "White Dragon";
    }

    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Stroke s = new BasicStroke(4.0f,                      // Width
                           BasicStroke.CAP_SQUARE,    // End cap
                           BasicStroke.JOIN_MITER,    // Join style
                           10.0f,                     // Miter limit
                           new float[] {6.0f,15.0f}, // Dash pattern
                           1.0f);                     // Dash phase

        g2.setStroke(s);

        g2.setPaint(Config.BLUE);

        Rectangle r = new Rectangle(40,18,30,45);
        g2.draw(r);

        g2.setStroke(new BasicStroke());
        //g2.setColor(Color.);
        r = new Rectangle(38,16,34,49);
        g2.draw(r);

        r = new Rectangle(42,20,26,41);
        g2.draw(r);
        
    }
    
}
