/*
 * GameBoard.java
 *
 * Creates and provided access to the gameboard
 * Michael Jasper
 */

//package Mahjong;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public final class GameBoard extends JPanel{

    //Represent the map in boolean values
    //false spaces are blank,
    //true spaces will be populated with tiles
    private int[][][] boardMap = {  {   {0,1,1,1,1,1,1,1,1,1,1,1,1,0,0},	// [0][x][y]
                                        {0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
                                        {0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
                                        {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                                        {1,1,1,1,1,1,1,1,1,1,1,1,1,0,0},
                                        {0,0,1,1,1,1,1,1,1,1,1,1,0,0,0},
                                        {0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
                                        {0,1,1,1,1,1,1,1,1,1,1,1,1,0,0}},
                                    {   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	// [1][x][y]
                                        {0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
                                        {0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
                                        {0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
                                        {0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
                                        {0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
                                        {0,0,0,0,1,1,1,1,1,1,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}},
                                    {   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},	// [2][x][y]
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,1,1,1,1,0,0,0,0,0,0},
                                        {0,0,0,0,0,1,1,1,1,0,0,0,0,0,0},
                                        {0,0,0,0,0,1,1,1,1,0,0,0,0,0,0},
                                        {0,0,0,0,0,1,1,1,1,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}},
                                    {   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},         // [3][x][y]
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,1,1,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,1,1,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}},
                                    {   {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},         // [4][x][y]
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                                        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}},
                                     };
    
    private final int                   X_AXIS = 15,
                                        Y_AXIS = 8,
                                        Z_AXIS = 5,
                                        TOTAL_TILES = 144;
    private int                         clearedTiles = 0;
    private boolean                     tournament;
    private ArrayList                   allTiles = new ArrayList();
    private TileContainer[][][]         board = new TileContainer[Z_AXIS][Y_AXIS][X_AXIS];
    private Image                       dragon;
    private Image                       background;
    private TileContainer               holdingTank;
    private ArrayList<TileContainer>    removedTiles = new ArrayList<TileContainer>();
    private ArrayList<TileContainer>    redoTiles = new ArrayList<TileContainer>();
    private Main                        parent;

    /* GameBoard
     * @parent - used to call Main.java's functions
     * @seed - random seed to create game
     * @tournament - flag if game is tournament mode
    ====================*/
    
    public GameBoard(Main parent, int seed, boolean tournament){
        this.parent = parent;
        this.tournament = tournament;
        setSize(Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        setLayout(null);
        buildDeck();
        fillGameBoardArray(seed);
        drawTiles();
        shadowifyTiles();
        holdingTank = new TileContainer(false);
        setVisible(true);
    }
    
    /* Expose REDO Functionality to parent
    ====================*/
    
    public boolean hasUndo(){
        return ! removedTiles.isEmpty();
    }
    public void undo(){
        //get tiles back from list
        TileContainer temp1 = removedTiles.remove(removedTiles.size()-1);
        TileContainer temp2 = removedTiles.remove(removedTiles.size()-1);
        
        redoTiles.add(temp1);
        redoTiles.add(temp2);
        
        //place on board
        board[temp1.z][temp1.y][temp1.x] = temp1;
        board[temp2.z][temp2.y][temp2.x] = temp2;
        //set to visible
        temp1.t.setVisible(true);
        temp2.t.setVisible(true);
        //update tile count
        clearedTiles -= 2;
        //update shadows
        shadowifyTiles();
    }
    
    /* Expose REDO Functionality to parent
    ====================*/
    
    public boolean hasRedo(){
        return ! redoTiles.isEmpty();
    }
    public void redo(){
        //get tiles back from list
        TileContainer temp1 = redoTiles.remove(redoTiles.size()-1);
        TileContainer temp2 = redoTiles.remove(redoTiles.size()-1);
        
        removedTiles.add(temp1);
        removedTiles.add(temp2);
        
        //place on board
        board[temp1.z][temp1.y][temp1.x] = new TileContainer(false);
        board[temp2.z][temp2.y][temp2.x] = new TileContainer(false);
        //set to visible
        temp1.t.setVisible(false);
        temp2.t.setVisible(false);
        //update tile count
        clearedTiles += 2;
        //update shadows
        shadowifyTiles();
    }
    
    /* hasWon
     * Creates fireworks screen
    ====================*/
    
    public void hasWon(){
        Fireworks f = new Fireworks(this);
        f.setSound(parent.sound);
        f.setExplosions(10, 1000);
        f.fire();
    }
    
    /* getRemovedTiles
     * clones removedTiles for parent to use is displaying removed tiles
    ====================*/
    
    public ArrayList getRemovedTiles(){
        ArrayList removedTilesCopy = new ArrayList();
        for(TileContainer tc : removedTiles){
            removedTilesCopy.add(tc.t.copy());
        }
        return removedTilesCopy;
    }

    /* Paints the background
    ====================*/
    
    @Override public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;        
        //Get background images
        try{
            dragon = ImageIO.read(getClass().getResource("images/dragon_bg.png"));
            background = ImageIO.read(getClass().getResource("images/wood_bg.jpg"));
        }catch(IOException e){
            //e.printStackTrace();
        }
        
        //Wood textured
        Rectangle2D r = new Rectangle2D.Double(0, 0, 350, 337);
        g2.setPaint(new TexturePaint((BufferedImage) background,r));
        //Paint wood texture onto background
        Rectangle2D bg = new Rectangle2D.Double(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        g2.fill(bg);
        //Set half opacity
        AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
	g2.setComposite(composite);
        //draw dragon
        g2.drawImage(dragon, 200, 20, null);
        //reset opacity for tile painting
        composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
	g2.setComposite(composite); // Set current alpha
    }

    /* buildDeck
     * generates a non-random deck of all tiles
    ====================*/
    
    private void buildDeck(){
            //Character Tiles
            for(int i = 1; i < 10; i++){
                //Add four copies of each tile
                for(int j = 0; j < 4; j++){
                   allTiles.add(new CharacterTile(Character.forDigit(i, 10))); 
                }
            }
            //Circle Tiles
            for(int i = 1; i < 10; i++){
                //Add four copies of each tile
                for(int j = 0; j < 4; j++){
                    allTiles.add(new CircleTile(i));
                }
            }
            //Bamboo Tiles
            for(int i = 2; i < 10; i++){
                //Add four copies of each tile
                for(int j = 0; j < 4; j++){
                    allTiles.add(new BambooTile(i));
                }
            }
            for(int j = 0; j < 4; j++){
                allTiles.add(new Bamboo1Tile());
            }
            //Character Tiles
            for(int i=0; i < 4; i++){
                allTiles.add(new CharacterTile('N'));
                allTiles.add(new CharacterTile('E'));
                allTiles.add(new CharacterTile('W'));
                allTiles.add(new CharacterTile('S'));
                allTiles.add(new CharacterTile('C'));
                allTiles.add(new CharacterTile('F'));
                allTiles.add(new WhiteDragonTile());
            }
            //Flower Tiles
            allTiles.add(new FlowerTile("Chrysanthemum"));
            allTiles.add(new FlowerTile("Orchid"));
            allTiles.add(new FlowerTile("Plum"));
            allTiles.add(new FlowerTile("Bamboo"));
            //Season Tiles
            allTiles.add(new SeasonTile("Spring"));
            allTiles.add(new SeasonTile("Summer"));
            allTiles.add(new SeasonTile("Fall"));
            allTiles.add(new SeasonTile("Winter"));
    }

    /* fillGameBoardArray
     * fills array based on boardMap with randomly selected tiles from the deck
    ====================*/
    
    private void fillGameBoardArray(int seed){
        Random r = new Random(seed);
        for(int z = 0; z < Z_AXIS; z++){
            for(int y = 0; y < Y_AXIS; y++){
                for(int x = 0; x < X_AXIS; x++){
                    if(boardMap[z][y][x] == 1){
                        //read board map and place tile onto board at spot
                        board[z][y][x] = new TileContainer(
                            allTiles.remove(r.nextInt(allTiles.size())), x, y, z
                        ) ;
                        //randomizedTiles.remove(1);
                    } else {
                            //or place an empty piece
                        board[z][y][x] = new TileContainer(false);
                    }
                }
            }
        }
    }

    /* shadowifyTiles
     * provides shadow lists to each tile on the board
    ====================*/
    
    private void shadowifyTiles(){
        for(int z = 0; z < Z_AXIS; z++){
            for(int y = 0; y < Y_AXIS; y++){
                for(int x = 0; x < X_AXIS; x++){
                    if(board[z][y][x].isTile()){
                        board[z][y][x].t.setShadow(
                            getShadow(x,y,z)
                        );
                    }
                }
            }
        }
    }

    /* drawTiles
     * Places tiles on the board according to their position on the tile array
     * assignes a event listener to tiles which checks matches and
     * handles removal of tiles, also triggers hasWon when condition is met
    ====================*/
    
    private void drawTiles(){
        int zOrder = 0;
        for(int z = Z_AXIS - 1; z >= 0; z--){
            for(int y = Y_AXIS-1; y >= 0; y--){
                for(int x = 0; x < X_AXIS; x++){
                    if (board[z][y][x].isTile()){
                        final Tile t = board[z][y][x].getTile();
                        //Handle Special Cases
                        if(x==0 && y==4 & z==0){
                            //Left most tile
                            t.setBounds(x * (Config.TILE_WIDTH-30) + (z*10) + 70, y * (Config.TILE_HEIGHT-35) - (z*20) - 30 + 20, Config.TILE_WIDTH, Config.TILE_HEIGHT);
                        } else if(x==14 && y==3 & z==0) {
                            //Right most tile
                            t.setBounds(x * (Config.TILE_WIDTH-30) + (z*10) + 70, y * (Config.TILE_HEIGHT-35) - (z*20)+30 + 20, Config.TILE_WIDTH, Config.TILE_HEIGHT);
                        } else if(x==13 && y==3 & z==0) {
                            //2nd right most tile
                            t.setBounds(x * (Config.TILE_WIDTH-30) + (z*10) + 70, y * (Config.TILE_HEIGHT-35) - (z*20)+30 + 20, Config.TILE_WIDTH, Config.TILE_HEIGHT);
                        } else if(x==6 && y==3 & z==4) {
                            //Top center
                            t.setBounds(x * (Config.TILE_WIDTH-30) + (z*10) + (35) + 70, y * (Config.TILE_HEIGHT-35) - (z*20) + (40) + 20, Config.TILE_WIDTH, Config.TILE_HEIGHT);

                        } else{
                            t.setBounds(x * (Config.TILE_WIDTH-30) + (z*10) + 70, y * (Config.TILE_HEIGHT-35) - (z*20) + 20, Config.TILE_WIDTH, Config.TILE_HEIGHT);
                        }
                        setComponentZOrder(t, zOrder);
                        board[z][y][x].setZOrder(zOrder);
                        zOrder ++;
                        //System.out.println(t);
                        final int[] c = {x,y,z};
                        t.setCoords(c);
                        
                        t.addMouseListener(new MouseAdapter() {
                            @Override public void mousePressed(MouseEvent e) {
                                if(isClickable(c[0], c[1], c[2])){

                                    if(!holdingTank.isTile()){
                                        //This is the first Tile:
                                        //Save for future use but don't delete yet
                                        holdingTank = board[c[2]][c[1]][c[0]];
                                        board[c[2]][c[1]][c[0]].t.setClicked(true);
                                        repaint();
                                    } else {
                                        //Second tile
                                        //Match tiles and remove, or reset if no match
                                        if( (holdingTank.t.matches(board[c[2]][c[1]][c[0]].t) && (holdingTank != board[c[2]][c[1]][c[0]])) ){
                                            //They match
                                            //Put tiles in removed tiles list
                                            removedTiles.add(holdingTank);
                                            removedTiles.add(board[c[2]][c[1]][c[0]]);
                                            //remove tiles from board map
                                            board[c[2]][c[1]][c[0]] = new TileContainer(false);
                                            board[holdingTank.getZ()][holdingTank.getY()][holdingTank.getX()] = new TileContainer(false);
                                            //Set to invisible
                                            t.setVisible(false);
                                            holdingTank.t.setVisible(false);
                                            clearedTiles += 2;
                                            shadowifyTiles();
                                            //clear redo list
                                            for(TileContainer tc : redoTiles){
                                                redoTiles.remove(tc);
                                            }
                                            if(tournament){
                                                parent.title("Mah Jong - Tournament Mode - Tiles Cleared: " + clearedTiles);
                                            }
                                            //drawTiles();
                                            
                                            //win condition
                                            if(clearedTiles == TOTAL_TILES){                                                
                                                hasWon();
                                            }
                                        }
                                        holdingTank.t.setClicked(false);
                                        repaint();
                                        holdingTank = new TileContainer(false);
                                    }
                                }
                            }
                        });
                        add(t);
                    }
                }
            }
        }
    }


    /* getShadow
     * returns a list of shadow polygons for a specific
     * tile depending on its neighboors
    ====================*/
    
    private ArrayList<Polygon> getShadow(int x, int y, int z){

        ArrayList<Polygon> shadow = new ArrayList<Polygon>();

        //Full North
        if( n(x,y,z) && ne(x,y,z) || (z == 0 && y == 0) ){
            //a,b,c,d
            Polygon temp = new Polygon();
            temp.addPoint(20, 10); // a
            temp.addPoint(90, 10); // b
            temp.addPoint(95, 00); // c
            temp.addPoint(25, 00); // d
            shadow.add(temp);
        }
        //Full East
        if( e(x,y,z) && ne(x,y,z) || (z==0 && y==3 && x==X_AXIS-1)){
            //A,B,C,D
            Polygon temp = new Polygon();
            temp.addPoint(90, 10); // A
            temp.addPoint(90, 70); // B
            temp.addPoint(95, 60); // C
            temp.addPoint(95, 00); // D
            shadow.add(temp);
        }
        //North Truncated Right
        if( n(x,y,z) && !ne(x,y,z) ){
            //a,e,f,d
            Polygon temp = new Polygon();
            temp.addPoint(20, 10); // a
            temp.addPoint(80, 10); // e
            temp.addPoint(80, 00); // f
            temp.addPoint(25, 00); // d
            shadow.add(temp);
        }
        //East Truncated Top
        if( e(x,y,z) && !ne(x,y,z) && !(x==12 && y==4)){
            //E,B,C,F
            Polygon temp = new Polygon();
            temp.addPoint(90, 30); // E
            temp.addPoint(90, 70); // B
            temp.addPoint(95, 60); // C
            temp.addPoint(95, 30); // F
            shadow.add(temp);
        }
        if(x ==12 && y==3){
            //East Top Half
            if( !e(x,y,z) ){
                //A,G,H,D
                Polygon temp = new Polygon();
                temp.addPoint(90, 10); // A
                temp.addPoint(90, 35); // G
                temp.addPoint(95, 29); // H
                temp.addPoint(95, 00); // D
                shadow.add(temp);
            }
        }
        //East Bottom Half
        if(x==12 && y==4){
            if( !e(x,y,z) ){
                //I,B,C,J
                Polygon temp = new Polygon();
                temp.addPoint(70, 67); // I
                temp.addPoint(70, 70); // B
                temp.addPoint(80, 50); // C
                temp.addPoint(80, 67); // J
                shadow.add(temp);
            }
        }
        return shadow;
    }

    private boolean isClickable(int x, int y, int z){
        if(Config.DEBUG){ return true; }
        if (x==0 || x==X_AXIS-1){return true;}  // special side cases
        return ((w(x,y,z) || e(x,y,z)) && t(x,y,z) );
    }

    /*
     *  nw[x-1,y-1]   n[x,y-1] ne[x+1,y-1]
     *  w[x-1,y]      [x,y]    e[x+1,y]
     *  sw[x-1,y+1]   s[x,y+1] se[x+1,y+1]
     */
    
    /* Helper functions for determining neighboors
    ====================*/

    private int y_up    (int y){ return (y == Y_AXIS - 1) ? y : y + 1;}
    private int y_down  (int y){ return (y == 0) ? 0 : y - 1;}
    private int x_up    (int x){ return (x == X_AXIS - 1) ? x : x + 1;}
    private int x_down  (int x){ return (x == 0) ? 0 : x - 1;}
    private int z_up    (int z){ return (z == Z_AXIS-1) ? z : z +1; }
    
    /* Checks if neighboors are tiles
    ====================*/

    private boolean t  (int x, int y, int z) { return   (z == Z_AXIS-1) ? true : ! board[z_up(z)][y][x].isTile(); }
    private boolean n  (int x, int y, int z) { return   ! board[z][y_down(y)]   [x]         .isTile(); }
    private boolean ne (int x, int y, int z) { return   ! board[z][y_down(y)]   [x_up(x)]   .isTile(); }
    private boolean e  (int x, int y, int z) { return   ! board[z][y]           [x_up(x)]   .isTile(); }
    private boolean nw (int x, int y, int z) { return   ! board[z][y_down(y)]   [x_down(x)] .isTile(); }
    private boolean w  (int x, int y, int z) { return   ! board[z][y]           [x_down(x)] .isTile(); }
    private boolean sw (int x, int y, int z) { return   ! board[z][y_up(y)]     [x_down(x)] .isTile(); }
    private boolean s  (int x, int y, int z) { return   ! board[z][y_up(y)]     [x]         .isTile(); }
    private boolean se (int x, int y, int z) { return   ! board[z][y_up(y)]     [x_up(x)]   .isTile(); }

}
