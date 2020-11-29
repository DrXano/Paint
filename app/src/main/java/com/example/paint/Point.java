package com.example.paint;

import java.io.Serializable;

public class Point implements Serializable {
    double x;
    double y;
    boolean firstpoint;

    public Point(){}

    public Point(double x, double y, boolean firstpoint){
        this.x = x;
        this.y = y;
        this.firstpoint = firstpoint;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isFirstpoint() {
        return firstpoint;
    }

    public void setFirstpoint(boolean firstpoint) {
        this.firstpoint = firstpoint;
    }
}
