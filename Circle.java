/*
 * Circle.java
 *
 * Draws specific Circle shapes
 * Michael Jasper
 */

//package Mahjong;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Circle {

    private double r, x, y;
    private Color c;
    
    public Circle(double centerX, double centerY, double radius, Color c){
        this.r = radius;
        this.x = centerX;
        this.y = centerY;
        this.c = c;
    }

    public void draw(Graphics2D g2){
        RawCircle(x+.5, y+.5,   r,          Config.WHITE,   g2);
        RawCircle(x,    y,      r,          c,              g2);
        RawCircle(x,    y,      r * .666,   Config.WHITE,   g2);
        RawCircle(x,    y,      r * .45,    c,              g2);

        
    }
    
    private void RawCircle(double x, double y, double r, Color c, Graphics2D g2){
        Shape circle = new Ellipse2D.Double(x-r, y-r, 2*r, 2*r);
        g2.setPaint(c);
        g2.fill(circle);
    }
}
