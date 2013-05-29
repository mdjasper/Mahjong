/*
 * Bamboo1Tile.java
 *
 * Bamboo 1 Tile (bird image)
 * Michael Jasper
 */

//package Mahjong;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bamboo1Tile extends PictureTile {
    
    private Image img;
    
    public Bamboo1Tile(){
        setToolTipText(toString());
         try{
            img = ImageIO.read(getClass().getResource("images/Sparrow.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Bamboo1Tile copy(){
        return new Bamboo1Tile();
    }

    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //use custom resize class to get correct proportions and scaling
        Image scaled = new ImageResize().getImage(img, 50, 50);

        g2.drawImage(scaled, 35, 15, null);
        
    }
    @Override public final String toString(){
        return "Bamboo 1";
    }

}
