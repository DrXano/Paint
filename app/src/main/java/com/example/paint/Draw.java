package com.example.paint;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;


import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Draw implements Serializable {

    private HashMap<String,Point> p;
    private int SW;
    private int color;
    private int i;

    public Draw() {
    }

    public Draw(int SW, int color){
        this.p = new HashMap<>();

        this.SW = SW;

        this.color = color;
        this.i = 0;
    }

    public Draw(Paint paint) {
        this.p = new HashMap<>();
        if(paint.getStrokeWidth() == 20f){
            this.SW = 1;
        }else if(paint.getStrokeWidth() == 90f){
            this.SW = 2;
        }else{
            this.SW = 1;
        }
        this.color = paint.getColor();
        this.i = 0;
    }

    @Exclude
    public Path getPath() {
        Path result = new Path();
        if(this.p != null && !this.p.isEmpty()) {
            result.moveTo((float) p.get(String.valueOf(0) + "_key").x,(float) p.get(String.valueOf(0) + "_key").y);
            for (int j = 1; j < p.size(); j++) {
                result.lineTo((float) p.get(String.valueOf(j) + "_key").x,(float) p.get(String.valueOf(j) + "_key").y);
            }
        }
        return result;
    }
    @Exclude
    public void addPoint(Point point){
        this.p.put(String.valueOf(this.i) + "_key",point);
        this.i++;
    }

    @Exclude
    public Paint getPaint() {
        Paint p = new Paint();
        p.setAntiAlias(true);

        if(this.SW == 1){
            p.setStrokeWidth(20f);
        }else{
            p.setStrokeWidth(90f);
        }

        p.setColor(this.color);

        p.setStyle(Paint.Style.STROKE);
        p.setStrokeJoin(Paint.Join.ROUND);
        return p;
    }

    @Exclude
    public void setPaint(Paint paint) {
        if(paint.getStrokeWidth() == 20f){
            this.SW = 1;
        }else{
            this.SW = 2;
        }
        this.color = paint.getColor();
    }

    public void setColor(int color) {
        this.color = color;
    }


    @Exclude
    public void setContrast(double contrast) {
        int A = Color.alpha(this.color);
        int R = Color.red(this.color);
        int G = Color.green(this.color);
        int B = Color.blue(this.color);

        R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (R < 0) {
            R = 0;
        } else if (R > 255) {
            R = 255;
        }

        G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (G < 0) {
            G = 0;
        } else if (G > 255) {
            G = 255;
        }

        B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (B < 0) {
            B = 0;
        } else if (B > 255) {
            B = 255;
        }

        this.color = Color.argb(A,R,G,B);
    }

    public int getSW() {
        return this.SW;
    }

    public int getColor() {
        return color;
    }

    public void setP(HashMap<String,Point> p) {
        this.p = p;
    }

    public HashMap<String,Point> getP(){
        return this.p;
    }

    public void setStrokeWidth(int strokeWidth) {
        SW = strokeWidth;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setSW(int SW) {
        this.SW = SW;
    }
}
