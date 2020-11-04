package com.example.paint;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class Draw {

    private Path path;
    private Paint paint;

    public Draw(Path path, Paint paint){
        this.paint = paint;
        this.path = path;
    }

    public Path getPath(){
        return this.path;
    }

    public Paint getPaint(){
        return this.paint;
    }

    public void setColor(int color){
        this.paint.setColor(color);
    }

    public void setContrast(double contrast){
        int A = Color.alpha(this.paint.getColor());
        int R = Color.red(this.paint.getColor());
        int G = Color.green(this.paint.getColor());
        int B = Color.blue(this.paint.getColor());

        R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if(R < 0) { R = 0; }
        else if(R > 255) { R = 255; }

        G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if(G < 0) { G = 0; }
        else if(G > 255) { G = 255; }

        B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if(B < 0) { B = 0; }
        else if(B > 255) { B = 255; }

        this.paint.setARGB(A,R,G,B);
    }
}
