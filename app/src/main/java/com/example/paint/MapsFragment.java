package com.example.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    boolean drawmode = false;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng loc = new LatLng(38.715521, -9.217589);
            googleMap.addMarker(new MarkerOptions().position(loc).title("Home"));

            googleMap.setMinZoomPreference(16.0f);
            googleMap.setMaxZoomPreference(18.0f);
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    };

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
                drawmode = drawmode ? false : true;
                b.setText(getResources().getString(drawmode ? R.string.stopdraw : R.string.startdraw));
            }

            ;
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}