/*
 * Main.java
 *
 * Main File for Majong
 * Controls menu, gameboard
 * Michael Jasper
 */

//package Mahjong;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

public class Main extends JFrame
{
    GameBoard board;
    int gameSeed;
    public boolean sound, tournament;
    JMenuItem soundItem, tournamentItem, undo, reset, load, removed, redo;
    Timer t;

    /*
     * Main
     * initializes a new game and menu
     */
    
    public Main()
    {
        sound = true;
        tournament = false;
        gameSeed = (int) System.currentTimeMillis() % 100000;
        setSize(Config.WINDOW_WIDTH,Config.WINDOW_HEIGHT);
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mah Jong (Game ID: " + gameSeed + ")");

        board = new GameBoard(this, gameSeed, tournament);
        add(board, BorderLayout.CENTER);

        //Menu
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        //Main item
        JMenu game = new JMenu("Game");
        menu.add(game);

        //New Game
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            int confirm = JOptionPane.showConfirmDialog(null,
                "Start a new game (discards this one)?",
                "Message",
                JOptionPane.YES_NO_OPTION);
            if(confirm == 0){
                //OK
                newGame();
            }
          }
        });
        game.add(newGame);
        

        //Load Game
        load = new JMenuItem("Load Game");
        load.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            String value = JOptionPane.showInputDialog(null, "Enter game number: ");
            if (! value.isEmpty() && isInt(value)){
                loadGame(value);
            }
          }
        });
        game.add(load);
        
        
        //Reset Item
        reset = new JMenuItem("Reset Game");
        reset.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            int confirm = JOptionPane.showConfirmDialog(null,
                "Reset game (begin current game over)?",
                "Message",
                JOptionPane.YES_NO_OPTION);
            if(confirm == 0){
                //OK
                undoAll();
            }
          }
        });
        game.add(reset);
        
        game.addSeparator();
        
        //Exit item
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            int confirm = JOptionPane.showConfirmDialog(null,
                "Exit (quit current game)?",
                "Message",
                JOptionPane.YES_NO_OPTION);
            if(confirm == 0){
                //OK
                System.exit(0);
            }
          }
        });
        game.add(exit);
        
        //Play
        JMenu play = new JMenu("Play");
        menu.add(play);

        //Undo Item
        undo = new JMenuItem("Undo");
        undo.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            int confirm = JOptionPane.showConfirmDialog(null,
                "Undo previous action?",
                "Message",
                JOptionPane.YES_NO_OPTION);
            if(confirm == 0){
                //OK
                undo();
            }
          }
        });
        play.add(undo);
        
        //Redo Item
        redo = new JMenuItem("Redo");
        redo.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            int confirm = JOptionPane.showConfirmDialog(null,
                "Redo previous action?",
                "Message",
                JOptionPane.YES_NO_OPTION);
            if(confirm == 0){
                //OK
                redo();
            }
          }
        });
        play.add(redo);

        //Display removed tiles
        removed = new JMenuItem("Removed Tiles");
        removed.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            showRemovedTiles();
          }
        });
        play.add(removed);
        
        //sound
        JMenu soundMenu = new JMenu("Sound");
        menu.add(soundMenu);

        //Toggle Sound
        soundItem = new JMenuItem("Turn Off");
        soundItem.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            toggleSound();
          }
        });
        soundMenu.add(soundItem);
        
        //Help
        JMenu help = new JMenu("Help");
        menu.add(help);

        //Operation
        JMenuItem operation = new JMenuItem("Operation");
        operation.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            Help h = new Help("help/help.html", "Help");
            h.display();
          }
        });
        help.add(operation);
        
        //Rules
        JMenuItem rules = new JMenuItem("Game Rules");
        rules.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            Help h = new Help("help/rules.html", "Help");
            h.display();
          }
        });
        help.add(rules);
        
        //Tournament
        JMenu tournamentMenu = new JMenu("Tournament");
        menu.add(tournamentMenu);

        tournamentItem = new JMenuItem("Start Tourament");
        tournamentItem.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            toggleTournament();
          }
        });
        tournamentMenu.add(tournamentItem);
        
        //Play Win Screen
        if(Config.DEBUG){
            //Play
            JMenu debug = new JMenu("Debug");
            menu.add(debug);

            JMenuItem win = new JMenuItem("Win");
            win.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent evt) {
                board.hasWon();
              }
            });
            debug.add(win);
        }

        setVisible(true);
    }
    
    /*
     * Helper function for loading new games
     */
    private boolean isInt(String v){
        try{
           Integer.parseInt(v);
           return true;
        }
        catch( Exception e ){
           return false;
        }
    }
    
    /*
     * Load a game from a unique game id
     */
    
    private void loadGame(String seed){
        remove(board);
        board = new GameBoard(this, Integer.parseInt(seed), tournament);
        add(board);
        repaint();
        gameSeed = Integer.parseInt(seed);
        setTitle("Mah Jong (Game ID: " + gameSeed + ")");
    }
    
    /*
     * Dispose of current game, and start fresh
     */
    
    private void newGame(){
        remove(board);
        gameSeed = (int) System.currentTimeMillis() % 100000;
        board = new GameBoard(this, gameSeed, tournament);
        add(board, BorderLayout.CENTER);
        setTitle("Mah Jong (Game ID: " + gameSeed + ")");
        repaint();
    }
    
    /*
     * Undo, calls boards undo function
     */
    
    private void undo(){
        if(board.hasUndo()){
            board.undo();
        }
    }
    
    /*
     * undoAll -- Resets current game to 
     * initial state
     */
    
    private void undoAll(){
        while(board.hasUndo()){
            undo();
        }
    }
    
    /*
     * Call boards redo function
     */
    
    private void redo(){
        if(board.hasRedo()){
            board.redo();
        }
    }
    
    /*
     * Toggle sound on/off
     * for win screen
     */
    
    private void toggleSound(){
        sound = !sound;
        soundItem.setText(sound ? "Turn Off" : "Turn On");
    }
    
    /*
     * Toggle Tournament on/off
     * default: off
     */
    
    private void toggleTournament(){
        tournament = !tournament;
        if(tournament){
            tournamentItem.setText("End Tournament");
            newGame();
            setTitle("Mah Jong - Tournament Mode");
            undo.setEnabled(false);
            reset.setEnabled(false);
            load.setEnabled(false);
            removed.setEnabled(false);
            redo.setEnabled(false);
            t = new Timer();
            add(t, BorderLayout.NORTH);
        } else {
            tournamentItem.setText("Start Tournament");
            newGame();
            undo.setEnabled(true);
            reset.setEnabled(true);
            load.setEnabled(true);
            removed.setEnabled(true);
            redo.setEnabled(true);
            remove(t);
        }
    }
    
    /*
     * title
     * allow board to set the screen title for 
     * tournament play
     */
    
    public void title(String title){
        setTitle(title);
    }
    
    /*
     * Create and display a scrolling window
     * containing removed tiles
     */
    
    private void showRemovedTiles(){  
        JPanel inner = new JPanel();
        inner.setLayout(new GridLayout(0,4,2,2));

        ArrayList<Tile> tiles = board.getRemovedTiles();
        for(Tile t : tiles){
            t.setShadow(new ArrayList());
            inner.add(t);
        }

        int height = ((tiles.size() / 4) + 1) * 100;
        int width = 380;
        inner.setSize(width,height);
        inner.setPreferredSize(new Dimension(width,height));
        
        System.out.println("Inner size: " + inner.getSize());
        
        JScrollPane scroller = new JScrollPane(inner);
        scroller.setLayout( new ScrollPaneLayout());
        scroller.setPreferredSize(new Dimension(400,410));
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        //scroller.add(inner);

        JDialog popUpContainer = new JDialog(this, true);
        popUpContainer.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        popUpContainer.setPreferredSize(new Dimension(400,410));
        popUpContainer.setBounds(400,100,410,400);
        popUpContainer.setResizable(false);
        popUpContainer.setLayout(new BorderLayout());
        
        popUpContainer.add(scroller, BorderLayout.CENTER);

        popUpContainer.setVisible(true);
    }
    
    public static void main(String[] args)
    {
        new Main();
    }
}
