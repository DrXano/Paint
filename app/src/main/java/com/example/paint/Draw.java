package com.example.paint;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;


import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Draw implements Serializable {

    private SerialPath p;
    private int StrokeWidth;
    private int color;

    public Draw() {
    }

    public Draw(Path p, int StrokeWidth, int color){
        this.p = (SerialPath) p;

        if(StrokeWidth == 20f){
            this.StrokeWidth = 1;
        }else if(StrokeWidth == 90f){
            this.StrokeWidth = 2;
        }else{
            this.StrokeWidth = 1;
        }

        this.color = color;
    }

    public Draw(Path path, Paint paint) {

        if(paint.getStrokeWidth() == 20f){
            this.StrokeWidth = 1;
        }else if(paint.getStrokeWidth() == 90f){
            this.StrokeWidth = 2;
        }else{
            this.StrokeWidth = 1;
        }
        this.color = paint.getColor();
        this.p = (SerialPath) path;

    }

    public Path getP() {
        return this.p;
    }


    @Exclude
    public Paint getPaint() {
        Paint p = new Paint();
        p.setAntiAlias(true);


        if(this.StrokeWidth == 1){
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
            this.StrokeWidth = 1;
        }else{
            this.StrokeWidth = 2;
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

    public float getStrokeWidth() {
        if(this.StrokeWidth == 1){
            return 20f;
        }else{
            return 90f;
        }
    }

    public int getColor() {
        return color;
    }

    public void setStrokeWidth(float strokeWidth) {
        if(strokeWidth == 20f){
            this.StrokeWidth = 1;
        }else{
            this.StrokeWidth = 2;
        }
    }

    public void setP(Path p) {
        this.p = (SerialPath) p;
    }
}
