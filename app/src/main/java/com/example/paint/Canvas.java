package com.example.paint;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.CanvasInterface;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Canvas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Canvas extends Fragment implements CanvasInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PaintCanvas paintCanvas;

    public Canvas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Canvas.
     */
    // TODO: Rename and change types and number of parameters
    public static Canvas newInstance(String param1, String param2) {
        Canvas fragment = new Canvas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //final View v = inflater.inflate(R.layout.fragment_canvas, container, false);

        GestureListener mGestureListener = new GestureListener();
        GestureDetector mGestureDetector = new GestureDetector(getContext(), mGestureListener);
        mGestureDetector.setIsLongpressEnabled(true);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        final PaintCanvas paintCanvas = new PaintCanvas(getContext(), null, mGestureDetector, getActivity().getWindow());
        mGestureListener.setCanvas(paintCanvas);

        SensorManager mSensorManager = (SensorManager) this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(new Accelerometer(this.getContext(), paintCanvas), mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(new Lights(this.getContext(), paintCanvas), mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);

        getParentFragmentManager().setFragmentResultListener("bgcolor", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle result) {
                int color = result.getInt("color");
                paintCanvas.changeBackground(color);
            }
        });
        getParentFragmentManager().setFragmentResultListener("lncolor", this, new FragmentResultListener() {

            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle result) {
                int color = result.getInt("color");
                paintCanvas.changeLineColor(color);
            }
        });

        this.paintCanvas = paintCanvas;
        return paintCanvas;
    }

    public void saveDraw(final String drawname) {

        if (drawname != null) {
            if (drawname.length() > 20) {
                Toast.makeText(getActivity(), "The name is to big", Toast.LENGTH_SHORT).show();
            } else if (drawname.length() <= 0) {
                Toast.makeText(getActivity(), "Please pick a name", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("draws");

                SavedDraw draw = new SavedDraw(drawname, this.paintCanvas.getPaths());

                /*
                List<Draw> paths = this.paintCanvas.getPaths();

                Map<String, Draw> canvas = new HashMap<>();
                for(int i = 0; i < paths.size(); i++){
                    canvas.put(i+"",paths.get(i));
                }
                */


                mFirebaseDatabaseReference.child(drawname).setValue(draw, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            Toast.makeText(getActivity(), drawname + " was saved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "There was an error saving your draw", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else {
            Toast.makeText(getActivity(), "No name was picked", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadDraw(String name) {
        DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("draws").child(name);
        //Query q = mFirebaseDatabaseReference;

        //mFirebaseDatabaseReference.addListenerForSingleValueEvent();

        mFirebaseDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    SavedDraw sd = snapshot.getValue(SavedDraw.class);

                    String name = sd.getName();
                    List<Draw> draws = new ArrayList<Draw>(sd.getPaths().values());

                    paintCanvas.setPaths(new ArrayList<>(draws));
                }else{
                    Toast.makeText(getActivity(), "No data found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(getActivity(), name + " loaded", Toast.LENGTH_LONG).show();
    }

    private List<Draw> parseInfo(List canv) {
        List<Draw> result = new ArrayList<>();
        for(Object m: canv){
            Map can = (Map) m;
            Draw d = new Draw();
            long n = (long) can.get("color");
            d.setColor((int)n);
            long e = (long) can.get("i");
            d.setI((int)e);
            long s = (long)can.get("sw");
            d.setSW((int) s);
            Map<String, Point> points = parsePoints((List) can.get("p"));
            d.setP((HashMap<String, Point>) points);
        }
        return result;
    }

    private Map<String, Point> parsePoints(List p) {
        Map<String, Point> points = new HashMap<>();
        int i = 0;
        for(Object m: p){
            Map can = (Map) m;
            double x = (double) can.get("x");
            double y = (double) can.get("y");
            points.put(i+"",new Point(x,y));
        }
        return points;
    }

    @Override
    public void save(String name) {
        this.saveDraw(name);
    }

    @Override
    public void load(String name) {
        this.loadDraw(name);
    }
}