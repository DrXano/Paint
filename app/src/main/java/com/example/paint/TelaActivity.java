package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TelaActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    public static final String PREFS = "Paintprefs";

    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private int color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            Fragment canvas = new Canvas();
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.mainfrag,canvas);
            trans.commit();
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Fragment canvas = new Canvas();
            Fragment palette = new Palette();
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.canvas_cont,canvas);
            trans.add(R.id.palette_cont,palette);
            trans.commit();
        }

        this.prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        this.editor = this.prefs.edit();
        if(prefs.contains("color")){
            this.color = prefs.getInt("color",0);
        }else {
            this.color = R.color.white;
        }
        this.findViewById(R.id.tela).setBackgroundColor(getResources().getColor(this.color));
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
                        nextfrag = new Palette();
                    } else {
                        nextfrag = new Canvas();
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
                this.findViewById(R.id.tela).setBackgroundColor(getResources().getColor(color));
            }
        }
    }
}