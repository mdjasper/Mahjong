/*
 * Bamboo.java
 *
 * Draws specific bamboo shapes
 * Michael Jasper
 */ 


//package Mahjong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Bamboo {

    private int x, y;
    private Color c;
    public Bamboo(int centerX,int centerY, Color c){
        this.x = centerX;
        this.y = centerY;
        this.c = c;
    }

    public void draw(Graphics2D g2){
        
        Polygon shape = new Polygon();
        shape.addPoint(x-3, y-9);
        shape.addPoint(x+3, y-9);
        shape.addPoint(x+4, y-8);
        shape.addPoint(x+4, y-6);
        shape.addPoint(x+3, y-6);
        shape.addPoint(x+2, y-5);
        shape.addPoint(x+2, y-2);
        shape.addPoint(x+4, y+1);
        shape.addPoint(x+4, y+3);
        shape.addPoint(x+3, y+3);
        shape.addPoint(x+2, y+4);
        shape.addPoint(x+2, y+7);
        shape.addPoint(x+4, y+9);
        shape.addPoint(x+4, y+11);
        shape.addPoint(x-4, y+11);
        shape.addPoint(x-4, y+9);
        shape.addPoint(x-2, y+7);
        shape.addPoint(x-2, y+4);
        shape.addPoint(x-3, y+3);
        shape.addPoint(x-4, y+3);
        shape.addPoint(x-4, y);
        shape.addPoint(x-2, y-1);
        shape.addPoint(x-2, y-6);
        shape.addPoint(x-3, y-6);
        shape.addPoint(x-4, y-7);
        shape.addPoint(x-4, y-8);

        g2.setColor(c);
        g2.fillPolygon(shape);
        g2.setColor(Config.WHITE);
        g2.drawLine(x, y-1, x, y-6);
        g2.drawLine(x, y+3, x, y+9);
    }
}
