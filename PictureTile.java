/*
 * PictureTile.java
 *
 * Parent class for other image tiles
 * Michael Jasper
 */

//package Mahjong;

abstract public class PictureTile extends Tile {
    private String name;

    public void PictureTile(String name){
        this.name = name;
    }

    @Override public String toString(){
        return name;
    }
}
