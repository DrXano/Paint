package com.example.paint;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    public Polyline line;
    boolean drawmode = false;
    private boolean requestingLocationUpdates = true;
    private LocationCallback locationCallback;
    private LocationRequest locReq;
    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        final Button b = v.findViewById(R.id.mapdrawbutton);
        b.setText(getResources().getString(drawmode ? R.string.stopdraw : R.string.startdraw));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawmode = !drawmode;
                if (!drawmode) {

                }
                b.setText(getResources().getString(drawmode ? R.string.stopdraw : R.string.startdraw));
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
            }
        }
        googleMap.setMinZoomPreference(16.0f);
        googleMap.setMaxZoomPreference(18.0f);
        googleMap.setMyLocationEnabled(true);
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        this.locReq = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(1000);
        this.mMap = googleMap;
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

                        }
                    }
                });
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                    if (line == null) {
                        line = mMap.addPolyline(new PolylineOptions().add(loc));
                    }
                    if (drawmode) {
                        if (line == null) {
                            line = mMap.addPolyline(new PolylineOptions().add(loc));
                        } else {
                            List<LatLng> points = new ArrayList<>(line.getPoints());
                            points.add(loc);
                            line.setPoints(points);
                        }
                    }
                }
            }
        };
        fusedLocationClient.requestLocationUpdates(this.locReq, locationCallback, Looper.getMainLooper());
    }
}