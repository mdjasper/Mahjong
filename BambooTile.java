/*
 * BambooTile.java
 *
 * Numbered Bamboo Tiles
 * Utilizes Bamboo.java
 * Michael Jasper
 */

//package Mahjong;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class BambooTile extends RankTile {
    
    protected int    centerY = 40,
                     centerX = 55;

    public BambooTile(int rank){
        this.rank = rank;
        setToolTipText(toString());
    }
    
    public BambooTile copy(){
        return new BambooTile(this.rank);
    }

    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        ArrayList<Bamboo> list= new ArrayList();
        switch(rank){
            case 2: list=Tile2(); break;
            case 3: list=Tile3(); break;
            case 4: list=Tile4(); break;
            case 5: list=Tile5(); break;
            case 6: list=Tile6(); break;
            case 7: list=Tile7(); break;
            case 8: list=Tile8(); break;
            case 9: list=Tile9(); break;
        }
        for(Bamboo b : list){
            b.draw(g2);
        }
    }
    
    public ArrayList<Bamboo> Tile2(){
        ArrayList<Bamboo> list = new ArrayList();
        list.add(new Bamboo(centerX, centerY-13,    Config.BLUE));
        list.add(new Bamboo(centerX, centerY+13,    Config.GREEN));
        return list;
    }
    
    public ArrayList<Bamboo> Tile3(){
        ArrayList<Bamboo> list = new ArrayList();
        list.add(new Bamboo(centerX,    centerY-13, Config.BLUE));
        list.add(new Bamboo(centerX-10, centerY+13, Config.GREEN));
        list.add(new Bamboo(centerX+10, centerY+13, Config.GREEN));
        return list;
    }
    
    public ArrayList<Bamboo> Tile4(){
        ArrayList<Bamboo> list = new ArrayList();
        list.add(new Bamboo(centerX-10, centerY-13, Config.BLUE));
        list.add(new Bamboo(centerX+10, centerY-13, Config.GREEN));
        list.add(new Bamboo(centerX-10, centerY+13, Config.GREEN));
        list.add(new Bamboo(centerX+10, centerY+13, Config.BLUE));
        return list;
    }
    
    public ArrayList<Bamboo> Tile5(){
        ArrayList<Bamboo> list = new ArrayList();
        list.add(new Bamboo(centerX-15, centerY-13, Config.GREEN));
        list.add(new Bamboo(centerX+15, centerY-13, Config.BLUE));
        list.add(new Bamboo(centerX-15, centerY+13, Config.BLUE));
        list.add(new Bamboo(centerX+15, centerY+13, Config.GREEN));
        list.add(new Bamboo(centerX,    centerY,    Config.RED));
        return list;
    }
    
    public ArrayList<Bamboo> Tile6(){
        ArrayList<Bamboo> list = new ArrayList();
        list.add(new Bamboo(centerX-10, centerY-13, Config.GREEN));
        list.add(new Bamboo(centerX,    centerY-13, Config.GREEN));
        list.add(new Bamboo(centerX+10, centerY-13, Config.GREEN));
        list.add(new Bamboo(centerX-10, centerY+13, Config.BLUE));
        list.add(new Bamboo(centerX,    centerY+13, Config.BLUE));
        list.add(new Bamboo(centerX+10, centerY+13, Config.BLUE));
        return list;
    }
    
    public ArrayList<Bamboo> Tile7(){
        ArrayList<Bamboo> list = new ArrayList();
        list.add(new Bamboo(centerX,    centerY-17, Config.RED));
        list.add(new Bamboo(centerX-15, centerY,    Config.GREEN));
        list.add(new Bamboo(centerX,    centerY,    Config.BLUE));
        list.add(new Bamboo(centerX+15, centerY,    Config.GREEN));
        list.add(new Bamboo(centerX-15, centerY+17, Config.GREEN));
        list.add(new Bamboo(centerX,    centerY+17, Config.BLUE));
        list.add(new Bamboo(centerX+15, centerY+17, Config.GREEN));
        return list;
    }
    
    public ArrayList<Bamboo> Tile8(){
        ArrayList<Bamboo> list = new ArrayList();
        list.add(new Bamboo(centerX-19, centerY-17, Config.GREEN));
        list.add(new Bamboo(centerX,    centerY-17, Config.GREEN));
        list.add(new Bamboo(centerX+19, centerY-17, Config.GREEN));
        list.add(new Bamboo(centerX-10, centerY,    Config.RED));
        list.add(new Bamboo(centerX+10, centerY,    Config.RED));
        list.add(new Bamboo(centerX-19, centerY+17, Config.BLUE));
        list.add(new Bamboo(centerX,    centerY+17, Config.BLUE));
        list.add(new Bamboo(centerX+19, centerY+17, Config.BLUE));
        return list;
    }
    
    public ArrayList<Bamboo> Tile9(){
        ArrayList<Bamboo> list = new ArrayList();
        list.add(new Bamboo(centerX-19, centerY-17, Config.RED));
        list.add(new Bamboo(centerX,    centerY-17, Config.BLUE));
        list.add(new Bamboo(centerX+19, centerY-17, Config.GREEN));
        list.add(new Bamboo(centerX-19, centerY,    Config.RED));
        list.add(new Bamboo(centerX,    centerY,    Config.BLUE));
        list.add(new Bamboo(centerX+19, centerY,    Config.GREEN));
        list.add(new Bamboo(centerX-19, centerY+17, Config.RED));
        list.add(new Bamboo(centerX,    centerY+17, Config.BLUE));
        list.add(new Bamboo(centerX+19, centerY+17, Config.GREEN));
        return list;
    }
    
    @Override public final String toString(){
        return "Bamboo " + rank;
    }
}
