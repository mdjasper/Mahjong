/*
 * RankTile.java
 *
 * Parent class for tiles that use number or character
 * ranks
 * Michael Jasper
 */

//package Mahjong;

abstract public class RankTile extends Tile {

    protected int rank;

    public void rankTile(int rank){
        this.rank = rank;
    }

    @Override public boolean matches(Tile other){
        if (this == other) return true;
        if(other.getClass() != getClass()) return false;
        RankTile o = (RankTile)other;
        return o.rank == rank ? true : false;
    }
}
