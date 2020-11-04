package com.example.paint;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Lights implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor lights;
    private PaintCanvas canvas;
    private float max;

    public Lights(Context context, PaintCanvas canvas){
        this.canvas = canvas;
        this.mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.lights = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        this.max = this.lights.getMaximumRange();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(this.lights != null) {
            float value = event.values[0];
            canvas.workBrightness(value,this.max);
            canvas.setContrast(Math.pow((100 + (1 - value/this.max)) / 100, 2));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
