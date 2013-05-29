/*
 * ImageResize.java
 *
 * Utility class for resizing images
 * Takes max width or height and
 * mathmagicly creates correct dimentions
 * Michael Jasper
 */

//package Mahjong;

import java.awt.Image;

public class ImageResize {
    public Image getImage(Image img, int maxWidth, int maxHeight){

        //initialize values incase we return unexpectedly
        int oldHeight = img.getHeight(null);
        int oldWidth =  img.getWidth(null);

        //Get properly scaled dimentions
        if(oldHeight > maxHeight || oldWidth > maxWidth){

            int newHeight= 0,
                newWidth = 0;

            if(oldWidth > oldHeight){
                newWidth = maxWidth;
                float Ratio = (float)oldWidth / (float)maxWidth;
                newHeight = Math.round(oldHeight / Ratio);

            } else if(oldHeight > oldWidth){
                newHeight = maxHeight;
                float Ratio = (float)oldHeight / (float)maxHeight;
                newWidth = Math.round(oldWidth / Ratio);

            } else if(oldWidth == oldHeight){
                newHeight = maxHeight;
                newWidth = maxWidth;
            }
            //return new image
            return img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }
        //return origional image
        return img;
    }
}
