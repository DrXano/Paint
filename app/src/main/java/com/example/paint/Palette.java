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
    int color;

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
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
       return v;
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.white:
                if (checked && color != R.color.white)
                    color = R.color.white;
                break;
            case R.id.brown:
                if (checked && color != R.color.brown)
                    color = R.color.brown;
                break;
            case R.id.black:
                if (checked && color != R.color.black)
                    color = R.color.black;
                break;
            case R.id.pink:
                if (checked && color != R.color.pink)
                    color = R.color.pink;
                break;
            case R.id.red:
                if (checked && color != R.color.red)
                    color = R.color.red;
                break;
            case R.id.orange:
                if (checked && color != R.color.orange)
                    color = R.color.orange;
                break;
            case R.id.yellow:
                if (checked && color != R.color.yellow)
                    color = R.color.yellow;
                break;
            case R.id.lightgreen:
                if (checked && color != R.color.lightgreen)
                    color = R.color.lightgreen;
                break;
            case R.id.darkgreen:
                if (checked && color != R.color.darkgreen)
                    color = R.color.darkgreen;
                break;
            case R.id.darkblue:
                if (checked && color != R.color.darkblue)
                    color = R.color.darkblue;
                break;
            case R.id.lightblue:
                if (checked && color != R.color.lightblue)
                    color = R.color.lightblue;
                break;
            case R.id.purple:
                if (checked && color != R.color.purple)
                    color = R.color.purple;
                break;
        }
    }
}