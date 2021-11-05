package model;

import misc.moveType;

import java.awt.*;

public class TeleportTunnel {

    public Point from;
    public Point to;
    public moveType reqMove;

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public moveType getReqMove() {
        return reqMove;
    }

    public void setReqMove(moveType reqMove) {
        this.reqMove = reqMove;
    }

    public TeleportTunnel(int x1, int y1, int x2, int y2, moveType reqMove){
        this.from = new Point(x1,y1);
        this.to = new Point(x2,y2);
        this.reqMove = reqMove;
    }
}
