package com.example.paint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Palette#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Palette extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String bgKey = "bgcolor";
    private static final String lnKey = "lncolor";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String requestKey = lnKey;
    private String colorKey = "color";

    int defaultColor = R.color.white;

    public Palette() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Palette.
     */
    // TODO: Rename and change types and number of parameters
    public static Palette newInstance(String param1, String param2) {
        Palette fragment = new Palette();
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

    void openDialog() {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(
                this.getActivity(), defaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        Bundle result = new Bundle();
                        result.putInt(colorKey,color);
                        getParentFragmentManager().setFragmentResult(requestKey, result);
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                });
        dialog.show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_palette, container, false);

        final Button mButton = (Button) v.findViewById(R.id.buttoncolor);
        final Button white = v.findViewById(R.id.white);
        final Button brown = v.findViewById(R.id.brown);
        final Button black = v.findViewById(R.id.black);
        final Button pink = v.findViewById(R.id.pink);
        final Button red = v.findViewById(R.id.red);
        final Button orange = v.findViewById(R.id.orange);
        final Button yellow = v.findViewById(R.id.yellow);
        final Button lightgreen = v.findViewById(R.id.lightgreen);
        final Button darkgreen = v.findViewById(R.id.darkgreen);
        final Button darkblue = v.findViewById(R.id.darkblue);
        final Button lightblue = v.findViewById(R.id.lightblue);
        final Button purple = v.findViewById(R.id.purple);
        final ToggleButton toggle = v.findViewById(R.id.toggle_pallette);
        toggle.setChecked(true);
        toggle.setText(getResources().getString(R.string.line));
        toggle.setTextOn(getResources().getString(R.string.line));
        toggle.setTextOff(getResources().getString(R.string.background));
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //line
                    requestKey = lnKey;
                } else {
                    //background
                    requestKey = bgKey;
                }
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.white));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        brown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.brown));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.black));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.pink));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.red));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.orange));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.yellow));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        lightgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.lightgreen));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        darkgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.darkgreen));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        darkblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.darkblue));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        lightblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.lightblue));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt(colorKey,getResources().getColor(R.color.purple));
                getParentFragmentManager().setFragmentResult(requestKey, result);
            }
        });

       return v;
    }
}