package com.example.paint;

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

    public void setContrast(double value){}
}
