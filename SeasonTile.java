/*
 * SeasonTile.java
 *
 * Creates season image tiles
 * Michael Jasper
 */

//package Mahjong;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SeasonTile extends PictureTile {
    Image img;
    String imgName;
    public SeasonTile(String name){
        this.imgName = name;
        super.PictureTile(name);
        setToolTipText(toString());
        
        try{
            img = ImageIO.read(getClass().getResource("images/" + name + ".png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public SeasonTile copy(){
        return new SeasonTile(this.imgName);
    }

    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        //use custom resize class to get correct proportions and scaling
        Image scaled = new ImageResize().getImage(img, 50, 50);

        g2.drawImage(scaled, 30, 15, null);
    }
    
    
}
