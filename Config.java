/*
 * Config.java
 *
 * Contains config data for the project
 * such as colors, sizes, and debug flag
 * Michael Jasper
 */

//package Mahjong;

import java.awt.Color;
import java.awt.GradientPaint;

public final class Config {
    public static final Color   BLUE  = Color.decode("#254F9C"),
                                RED   = Color.decode("#AB3617"),
                                GREEN = Color.decode("#37612A"),
                                WHITE = Color.WHITE;
    public static final int     TILE_HEIGHT = 95,
                                TILE_WIDTH = 100,
                                WINDOW_HEIGHT = 600,
                                WINDOW_WIDTH = 1200;
    public static final GradientPaint 
                                defaultColor = new GradientPaint(20,85,Color.decode("#EEF4A6"),85,0,Color.decode("#B79920"),true),
                                clickedColor = new GradientPaint(20,85,Color.decode("#EEF96D"),85,0,Color.decode("#C39D02"),true);
    public static final boolean DEBUG = false;
}
