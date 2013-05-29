/*
 * CharacterTile.java
 *
 * Creates chinese character tiles
 * 1-9, n,e,s,w,c,f
 * Michael Jasper
 */

//package Mahjong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class CharacterTile extends Tile {

    protected char symbol;
    protected char[] chinese = {
        '\u4E00',   //0     1
        '\u4E8C',   //1     2
        '\u4E09',   //2     3
        '\u56DB',   //3     4
        '\u4E94',   //4     5
        '\u516D',   //5     6
        '\u4E03',   //6     7
        '\u516B',   //7     8
        '\u4E5D',   //8     9
        '\u5317',   //9     North
        '\u6771',   //10    East
        '\u897F',   //11    West
        '\u5357',   //12    South
        '\u4E2D',   //13    Red
        '\u767C',   //14    Green
    };

    public CharacterTile(char symbol){
        this.symbol = symbol;
        setToolTipText(toString());
    }

    public CharacterTile copy(){
        return new CharacterTile(this.symbol);
    }

    @Override public final String toString(){
        return "Character: " + Character.toString(symbol);
    }
    
    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        //Draw Small Character in Corner
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(Character.toString(symbol), 78, 24);
        //Draw Large Chinese Character
        if(symbol < 58){
            paintNumber(g2);
        } else {
            paintOther(g2);
        }
        
    }

    private void paintNumber(Graphics2D g2){
        //Set Font
        g2.setFont(new Font("KaiTi", Font.PLAIN, 24));
        //Paint number
        g2.setColor(Color.WHITE);
        g2.drawString(Character.toString(chinese[characterToIndex(symbol)]),44,35);
        g2.setColor(Color.BLACK);
        g2.drawString(Character.toString(chinese[characterToIndex(symbol)]),43,34);
        //Paint "Wan"
        //g2.setColor(Color.BLACK);
        //g2.drawString(Character.toString(chinese[14]),44,61);
        g2.setColor(Color.decode("#AB3617"));
        g2.drawString(Character.toString(chinese[14]),43,60);
    }

    private void paintOther(Graphics2D g2){
        //Set Font
        g2.setFont(new Font("KaiTi", Font.PLAIN, 40));
        g2.setColor(symbol == 'F' || symbol == 'C' ? Color.BLACK : Color.WHITE);
        g2.drawString(Character.toString(chinese[characterToIndex(symbol)]),36,56);
        g2.setColor(symbol == 'F' ? Config.GREEN : symbol == 'C' ? Config.RED : Color.BLACK);
        g2.drawString(Character.toString(chinese[characterToIndex(symbol)]),35,56);
    }

    private int characterToIndex(char c){
        switch (c){
            case 'N':
                return 9;
            case 'E':
                return 10;
            case 'W':
                return 11;
            case 'S':
                return 12;
            case 'C':
                return 13;
            case 'F':
                return 14;
        }
        return Integer.parseInt(Character.toString(c))-1;
    }
    @Override public boolean matches(Tile other){
        if (other == this){
            return true;
        }

        if(other.getClass() == this.getClass()){
            CharacterTile o = (CharacterTile)other;
            if(o.symbol == this.symbol){
                return true;
            } else{
                return false;
            }
        } else {
            return false;
        }
    }
}
