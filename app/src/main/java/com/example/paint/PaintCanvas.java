package com.example.paint;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class PaintCanvas extends View implements View.OnTouchListener {

    private Paint paint;
    private Path path;
    private ArrayList<Draw> paths = new ArrayList<>();
    private int backGroundColor = getResources().getColor(R.color.white);
    private GestureDetector mGestureDetector;
    private ContentResolver resolver;
    private Window window;

    public PaintCanvas(Context context, AttributeSet attrs, Window window) {
        super(context, attrs);
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        paint = new Paint();
        path = new SerialPath();
        paths.add(new Draw(path, paint));
        initPaint();
        this.window = window;
        this.resolver = context.getContentResolver();
    }

    public PaintCanvas(Context context, AttributeSet attrs, GestureDetector mGestureDetector, Window window) {
        super(context, attrs);
        this.mGestureDetector = mGestureDetector;
        setOnTouchListener(this);
        setBackgroundColor(backGroundColor);
        paint = new Paint();
        path = new SerialPath();
        paths.add(new Draw(path, paint));
        initPaint();
        this.window = window;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Draw d : paths)
            canvas.drawPath(d.getP(), d.getPaint());
    }

    @Override
    public boolean performClick() {
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

    public void changeBackground(int color) {
        backGroundColor = color;
        setBackgroundColor(color);
    }

    public void swapStyle() {

        Paint.Style style;
        if (this.paint.getStyle() == Paint.Style.STROKE) {
            style = Paint.Style.FILL;
            CharSequence text = "Fill Mode";
            Toast toast = Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            style = Paint.Style.STROKE;
            CharSequence text = "Stroke Mode";
            Toast toast = Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }

        Paint newPaint = new Paint();
        SerialPath newPath = new SerialPath();

        newPaint.setAntiAlias(true);
        newPaint.setStrokeWidth(this.paint.getStrokeWidth());
        newPaint.setColor(this.paint.getColor());
        newPaint.setStyle(style);
        newPaint.setStrokeJoin(this.paint.getStrokeJoin());

        paths.add(new Draw(newPath, newPaint));
        this.paint = newPaint;
        this.path = newPath;
    }

    public void swapStrokeWidth() {
        float width;
        if (this.paint.getStrokeWidth() == 20f) {
            width = 90f;
            CharSequence text = "thick line";
            Toast toast = Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            width = 20f;
            CharSequence text = "small line";
            Toast toast = Toast.makeText(this.getContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }
        Paint newPaint = new Paint();
        SerialPath newPath = new SerialPath();

        newPaint.setAntiAlias(true);
        newPaint.setStrokeWidth(width);
        newPaint.setColor(this.paint.getColor());
        newPaint.setStyle(this.paint.getStyle());
        newPaint.setStrokeJoin(this.paint.getStrokeJoin());

        paths.add(new Draw(newPath, newPaint));
        this.paint = newPaint;
        this.path = newPath;
    }

    public void changeLineColor(int color) {
        Paint newPaint = new Paint();
        SerialPath newPath = new SerialPath();

        newPaint.setAntiAlias(true);
        newPaint.setStrokeWidth(this.paint.getStrokeWidth());
        newPaint.setColor(color);
        newPaint.setStyle(this.paint.getStyle());
        newPaint.setStrokeJoin(this.paint.getStrokeJoin());

        paths.add(new Draw(newPath, newPaint));
        this.paint = newPaint;
        this.path = newPath;
    }

    public void erase() {
        /*
        for(Draw d: paths)
            d.setColor(backGroundColor);

         */
        Paint newPaint = new Paint();
        SerialPath newPath = new SerialPath();

        newPaint.setAntiAlias(true);
        newPaint.setStrokeWidth(this.paint.getStrokeWidth());
        newPaint.setColor(this.paint.getColor());
        newPaint.setStyle(this.paint.getStyle());
        newPaint.setStrokeJoin(this.paint.getStrokeJoin());

        paths.clear();

        paths.add(new Draw(newPath, newPaint));
        this.paint = newPaint;
        this.path = newPath;
        invalidate();
    }

    private void initPaint() {
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public void workBrightness(float v, float max) {
        //Settings.System.putInt(this.resolver, Settings.System.SCREEN_BRIGHTNESS, (int) (1 - v / max));
        ViewGroup.LayoutParams layoutpars = this.window.getAttributes();
        ((WindowManager.LayoutParams) layoutpars).screenBrightness = 1 - v / max;
        this.window.setAttributes((WindowManager.LayoutParams) layoutpars);
    }

    public void setContrast(double contrast) {
        for (Draw d : paths)
            d.setContrast(contrast);
        invalidate();
    }

    public ArrayList<Draw> getPaths() {
        return this.paths;
    }

    public void setPaths(ArrayList<Draw> paths) {
        paths.clear();
        this.paths = paths;
        invalidate();
    }
}