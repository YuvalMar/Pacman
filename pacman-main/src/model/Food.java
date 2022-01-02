package model;
/**
 * Normal food class.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Food {

    public Point position;
    public Timer foodTimer;
    public ActionListener pendingAL;
    public boolean isEaten=false;
    
    public Food(int x,int y){
        position = new Point(x,y);
        pendingAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	isEaten=true;
            	foodTimer.stop();
            }
        };
        foodTimer = new Timer(10000,pendingAL);
    }
    
    public void restoreFood() {
    	foodTimer.start();
    }
    
}
