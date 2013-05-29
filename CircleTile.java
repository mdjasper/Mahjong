/*
 * CircleTile.java
 *
 * Draws circle tiles (1-9)
 * Utilizes Circle.java
 * Michael Jasper
 */

//package Mahjong;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class CircleTile extends RankTile{
    
    public double    centerY = 40.0,
                     centerX = 55.0;

    public CircleTile(int rank){
        this.rank = rank;
        setToolTipText(toString());
    }

    public CircleTile copy(){
        return new CircleTile(this.rank);
    }

    @Override public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        ArrayList<Circle> list = new ArrayList();
        switch(rank){
            case 1: list=Tile1(); break;
            case 2: list=Tile2(); break;
            case 3: list=Tile3(); break;
            case 4: list=Tile4(); break;
            case 5: list=Tile5(); break;
            case 6: list=Tile6(); break;
            case 7: list=Tile7(); break;
            case 8: list=Tile8(); break;
            case 9: list=Tile9(); break;
        }
        for(Circle c : list){
            c.draw(g2);
        }
    }

    public ArrayList<Circle> Tile1(){

        ArrayList<Circle> list = new ArrayList();

        double r = 27.0;
        list.add(new Circle(centerX,centerY,r,Config.GREEN));
        list.add(new Circle(centerX,centerY,15.0,Config.BLUE));

        int n = 10;
        int r2 = 3;
        for (int i = 0; i < n; i++) {
            double t = 2 * Math.PI * i / n;
            int x = (int) Math.round(centerX + (r-8) * Math.cos(t));
            int y = (int) Math.round(centerY + (r-8) * Math.sin(t));
            list.add(new Circle(x, y,r2, Config.WHITE));
        }
        return list;
    }
    
    public ArrayList<Circle> Tile2(){
        double o = 13.0;
        double r = 12.0;
        ArrayList<Circle> list = new ArrayList();
        list.add(new Circle(centerX,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX,centerY+o,r,Config.RED));
        return list;
    }
    
    public ArrayList<Circle> Tile3(){
        ArrayList<Circle> list = new ArrayList();
        double r = 10.0;
        double o = 16.0;
        list.add(new Circle(centerX-o,centerY-o,r,Config.BLUE));
        list.add(new Circle(centerX,centerY,r,Config.RED));
        list.add(new Circle(centerX+o,centerY+o,r,Config.GREEN));
        return list;
    }
    
    public ArrayList<Circle> Tile4(){
        ArrayList<Circle> list = new ArrayList();
        double r = 12.0;
        double o = 13.0;
        list.add(new Circle(centerX-o,centerY-o,r,Config.BLUE));
        list.add(new Circle(centerX+o,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX-o,centerY+o,r,Config.GREEN));
        list.add(new Circle(centerX+o,centerY+o,r,Config.BLUE));
        return list;
    }
    
    public ArrayList<Circle> Tile5(){
        ArrayList<Circle> list = new ArrayList();
        double r = 8.0;
        double o = 15.0;
        list.add(new Circle(centerX-o,centerY-o,r,Config.BLUE));
        list.add(new Circle(centerX+o,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX-o,centerY+o,r,Config.GREEN));
        list.add(new Circle(centerX+o,centerY+o,r,Config.BLUE));
        list.add(new Circle(centerX,centerY,r,Config.RED));
        return list;
    }
    
    public ArrayList<Circle> Tile6(){
        ArrayList<Circle> list = new ArrayList();
        double r = 8.0;
        double o = 17.0;
        list.add(new Circle(centerX-o,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX-o,centerY,r,Config.RED));
        list.add(new Circle(centerX-o,centerY+o,r,Config.RED));
        list.add(new Circle(centerX+o,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX+o,centerY,r,Config.RED));
        list.add(new Circle(centerX+o,centerY+o,r,Config.RED));
        return list;
    }
    
    public ArrayList<Circle> Tile7(){
        ArrayList<Circle> list = new ArrayList();
        double r = 7.0;
        double o = 15.0;
        list.add(new Circle(centerX-o-3,centerY-o-2,r,Config.GREEN));
        list.add(new Circle(centerX-o,centerY,r,Config.RED));
        list.add(new Circle(centerX-o,centerY+o,r,Config.RED));
        list.add(new Circle(centerX,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX+o+3,centerY-o+2,r,Config.GREEN));
        list.add(new Circle(centerX+o,centerY,r,Config.RED));
        list.add(new Circle(centerX+o,centerY+o,r,Config.RED));
        return list;
    }
    
    public ArrayList<Circle> Tile8(){
        ArrayList<Circle> list = new ArrayList();
        double r = 6.0;
        double o = 12.0;
        list.add(new Circle(centerX-o,centerY-7,r,Config.BLUE));
        list.add(new Circle(centerX-o,centerY-20,r,Config.BLUE));
        list.add(new Circle(centerX-o,centerY+6,r,Config.BLUE));
        list.add(new Circle(centerX-o,centerY+19,r,Config.BLUE));
        list.add(new Circle(centerX+o,centerY-7,r,Config.BLUE));
        list.add(new Circle(centerX+o,centerY-20,r,Config.BLUE));
        list.add(new Circle(centerX+o,centerY+6,r,Config.BLUE));
        list.add(new Circle(centerX+o,centerY+19,r,Config.BLUE));
        return list;
    }
    
    public ArrayList<Circle> Tile9(){
        ArrayList<Circle> list = new ArrayList();
        double r = 9.0;
        double o = 19.0;
        list.add(new Circle(centerX-o,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX-o,centerY,r,Config.RED));
        list.add(new Circle(centerX-o,centerY+o,r,Config.BLUE));
        list.add(new Circle(centerX,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX,centerY,r,Config.RED));
        list.add(new Circle(centerX,centerY+o,r,Config.BLUE));
        list.add(new Circle(centerX+o,centerY-o,r,Config.GREEN));
        list.add(new Circle(centerX+o,centerY,r,Config.RED));
        list.add(new Circle(centerX+o,centerY+o,r,Config.BLUE));
        return list;
    }

    @Override public final String toString(){
        return "Circle: " + rank;
    }
}
