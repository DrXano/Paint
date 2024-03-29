package com.example.paint;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class GestureListener extends GestureDetector.SimpleOnGestureListener implements GestureDetector.OnDoubleTapListener {

    private PaintCanvas canvas;

    void setCanvas(PaintCanvas canvas) {
        this.canvas = canvas;
    }

    ////////SimpleOnGestureListener
    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /////////OnDoubleTapListener
    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        canvas.swapStrokeWidth();
        return false;
    }

}
