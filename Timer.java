/*
 * Timer.java
 * adapted from http://answers.yahoo.com/question/index?qid=20110731030452AAeMQa3
 */

//package Mahjong;

import javax.swing.JLabel;

public class Timer extends JLabel implements Runnable {

    public Timer() {
        new Thread(this).start();
    }

    @Override public void run() {
        long initialTime = System.currentTimeMillis();
        int seconds, minutes;
        while(true) {
            seconds=(int) ((System.currentTimeMillis ()-initialTime)/1000);
            minutes = (seconds/60)%60;
            seconds = seconds%60;
            setText("Tournament Timer: " + String.format("%02d:%02d", minutes, seconds));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
