package com.example.paint;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
import java.util.List;

public class PolyCanvas {

    private GoogleMap googleMap;
    private Polyline current;
    private List<Polyline> polys = new ArrayList<>();

    public PolyCanvas(GoogleMap googleMap){
        this.googleMap = googleMap;
        this.current = googleMap.addPolyline(new PolylineOptions().color(Color.BLACK).width(12).endCap(new RoundCap()).jointType(JointType.ROUND));
        this.polys.add(this.current);
    }

    public void drawPolyline(LatLng loc) {
        List<LatLng> points = this.current.getPoints();
        points.add(loc);
        this.current.setPoints(points);
    }

    public void startNewPolyline() {
        this.current = googleMap.addPolyline(new PolylineOptions().color(Color.BLACK).width(12).endCap(new RoundCap()).jointType(JointType.ROUND));
        this.polys.add(this.current);
    }
}
