package com.example.paint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import yuku.ambilwarna.AmbilWarnaDialog;

public class TelaActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    public static final String PREFS = "Paintprefs";

    int defaultColor = R.color.white;//0xffffff00;
    //ConstraintLayout mLayout;

    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    int color;
    Fragment canvas = new Canvas();
    Fragment palette = new Palette();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        this.editor = this.prefs.edit();
        if(prefs.contains("color")){
            this.color = prefs.getInt("color",0);
        }else {
            this.color = R.color.white;
        }

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.mainfrag,this.canvas);
            trans.commit();

        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.canvas_cont,this.canvas);
            trans.add(R.id.palette_cont,this.palette);
            trans.commit();
        }
    }

    void openDialog() {
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(
                TelaActivity.this, defaultColor,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        /*
                        TelaActivity.this.defaultColor = color;
                        TelaActivity.this.canvas.getView().setBackgroundColor(color);
                        TelaActivity.this.color = defaultColor;
                        TelaActivity.this.editor.putInt("color",color);
                        TelaActivity.this.editor.apply();
                        */
                    }

                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tela_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                /*
                Intent intent1 = new Intent(this, Settings.class);
                startActivityForResult(intent1,REQUEST_CODE);
                */

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Fragment current = getSupportFragmentManager().findFragmentById(R.id.mainfrag);

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    Fragment nextfrag;
                    if (current instanceof Canvas) {
                        nextfrag = this.palette;
                    } else {
                        nextfrag = this.canvas;
                    }
                    fragmentTransaction.replace(R.id.mainfrag, nextfrag);
                    fragmentTransaction.addToBackStack(null);

                    fragmentTransaction.commit();
                }
                return true;
            case R.id.about:
                Intent intent2 = new Intent(this, About.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("bgColor")) {
                color = data.getExtras().getInt("bgColor");
                editor.putInt("color",color);
                editor.apply();
                this.canvas.getView().setBackgroundColor(getResources().getColor(color));
            }
        }
    }
}