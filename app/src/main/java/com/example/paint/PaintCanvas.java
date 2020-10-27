package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class PaintCanvas extends View implements View.OnTouchListener{

    private Paint paint;
    private Path path;
    private ArrayList<Draw> paths = new ArrayList<>();
    private int backGroundColor = getResources().getColor(R.color.white);
    private GestureDetector mGestureDetector;

    public PaintCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        paint = new Paint();
        path = new Path();
        paths.add(new Draw(path,paint));
        initPaint();
    }

    public PaintCanvas(Context context, AttributeSet attrs, GestureDetector mGestureDetector) {
        super(context, attrs);
        this.mGestureDetector = mGestureDetector;
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        paint = new Paint();
        path = new Path();
        paths.add(new Draw(path,paint));
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawPath(path, paint);// draws the path with the paint
        for(Draw d: paths)
            canvas.drawPath(d.getPath(),d.getPaint());
    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return false; // let the event go to the rest of the listeners
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);// updates the path initial point
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);// makes a line to the point each time this event is fired
                break;
            case MotionEvent.ACTION_UP:// when you lift your finger
                performClick();
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }

    public void changeBackground(int color){
        backGroundColor = color;
        setBackgroundColor(color);
    }

    public void changeBackground(){
        Random r = new Random();
        backGroundColor = Color.rgb(r.nextInt(256), r.nextInt(256), r.nextInt(256));
        setBackgroundColor(backGroundColor);
    }

    public void setStyle(Paint.Style style){
        paint = new Paint();
        path = new Path();
        paths.add(new Draw(path,paint));
        paint.setStyle(style);
    }

    public void setStrokeWidth (int width) {
        paint = new Paint();
        path = new Path();
        initPaint();
        paths.add(new Draw(path,paint));
        paint.setStrokeWidth(width);
    }

    public void changeLineColor(int color){
        paint = new Paint();
        path = new Path();
        initPaint();
        paths.add(new Draw(path,paint));
        paint.setColor(color);
    }

    public void erase(){
        for(Draw d: paths)
            d.setColor(backGroundColor);
        //paint.setColor(backGroundColor);
    }

    private void initPaint(){
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

}