package model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class PowerUpFood {

    public Point position;
    public int type; //0-4
    public ActionListener pendingAL;
    public boolean isEaten=false;
    public Timer foodTimer;

    public PowerUpFood(int x,int y,int type){
        position = new Point(x,y);
        this.type = type;
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
