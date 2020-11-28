package com.example.paint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import interfaces.gndListener;

public class GetNameDialog extends AppCompatDialogFragment {

    gndListener listener;
    private EditText drawname;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.layout_canvasnamedialog, null);

        drawname = v.findViewById(R.id.canvasname);

        builder.setView(v)
                .setTitle("Pick a name for your draw")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.saveWithName(null);
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = drawname.getText().toString();
                        listener.saveWithName(name);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (gndListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement gndListener");
        }
    }
}
