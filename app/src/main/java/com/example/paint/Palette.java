package com.example.paint;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
                        result.putInt("bgcolor",color);
                        getParentFragmentManager().setFragmentResult("color", result);
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
                result.putInt("bgcolor",getResources().getColor(R.color.white));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        brown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.brown));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.black));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.pink));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.red));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.orange));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.yellow));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        lightgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.lightgreen));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        darkgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.darkgreen));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        darkblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.darkblue));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        lightblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.lightblue));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });
        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putInt("bgcolor",getResources().getColor(R.color.purple));
                getParentFragmentManager().setFragmentResult("color", result);
            }
        });

       return v;
    }
}