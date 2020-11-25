package com.example.paint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import interfaces.PathInterface;

public class TelaActivity extends AppCompatActivity implements GetNameDialog.gndListener, PathInterface {

    public static final String SAVED_CANVAS = "savedcanvas";

    public static final String PREFS = "Paintprefs";
    private static final int REQUEST_CODE = 1;

    int color;
    Fragment canvas = new Canvas();
    Fragment palette = new Palette();
    Fragment map = new MapsFragment();
    Fragment currenttt;
    FirebaseDatabase database;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private boolean hasDraw = false;
    private ArrayList<Draw> paths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela);

        database = FirebaseDatabase.getInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        this.editor = this.prefs.edit();
        if (prefs.contains("color")) {
            this.color = prefs.getInt("color", 0);
        } else {
            this.color = R.color.white;
        }

        this.currenttt = canvas;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.mainfrag, this.canvas);
            trans.commit();

        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.add(R.id.canvas_cont, this.canvas);
            trans.add(R.id.palette_cont, this.palette);
            trans.commit();
        }
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
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent1,REQUEST_CODE);
                */

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Fragment current = getSupportFragmentManager().findFragmentById(R.id.mainfrag);

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    Fragment nextfrag;
                    if (current instanceof Canvas || current instanceof MapsFragment) {
                        nextfrag = this.palette;
                    } else {
                        nextfrag = this.currenttt;
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
            case R.id.map:

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Fragment current = getSupportFragmentManager().findFragmentById(R.id.mainfrag);

                    if (this.currenttt instanceof Canvas) {
                        this.currenttt = this.map;
                    } else {
                        this.currenttt = this.canvas;
                    }

                    if (!(current instanceof Palette)) {
                        fragmentTransaction.replace(R.id.mainfrag, this.currenttt);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                } else {
                    Fragment current = getSupportFragmentManager().findFragmentById(R.id.canvas_cont);

                    if (current instanceof Canvas) {
                        this.currenttt = this.map;
                    } else {
                        this.currenttt = this.canvas;
                    }

                    fragmentTransaction.replace(R.id.canvas_cont, this.currenttt);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                return true;
            case R.id.save:
                if (this.currenttt instanceof Canvas) {
                    getNameFromUser();
                }
                return true;
            case R.id.load:
                if (this.currenttt instanceof Canvas) {
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getNameFromUser() {
        GetNameDialog gnd = new GetNameDialog();
        gnd.show(getSupportFragmentManager(),"draw name dialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("bgColor")) {
                color = data.getExtras().getInt("bgColor");
                editor.putInt("color", color);
                editor.apply();
                this.canvas.getView().setBackgroundColor(getResources().getColor(color));
            }
        }
    }

    @Override
    public void getName(String drawname) {
        if(drawname != null){
            if(drawname.length() > 20) {
                Toast.makeText(this, "The name is to big", Toast.LENGTH_SHORT).show();
            }else if(drawname.length() <= 0){
                Toast.makeText(this, "Please pick a name", Toast.LENGTH_SHORT).show();
            }else{
                if(this.hasDraw){
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(drawname);
                    mDatabase.setValue(new savedDraw(drawname,this.paths));
                    Toast.makeText(this, drawname + " was saved", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "There is no draw to be saved", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(this, "No name was picked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataReceived(ArrayList<Draw> paths, boolean hasDraw) {
        if(!this.paths.isEmpty())
            this.paths.clear();
        this.paths = new ArrayList<>(paths);
        this.hasDraw = hasDraw;
    }

    public class savedDraw{
        private String nome;
        private ArrayList<Draw> paths;
        public savedDraw(String nome, ArrayList<Draw> paths){
            this.nome = nome;
            this.paths = paths;
        }

        public String getNome(){
            return this.nome;
        }

        public ArrayList<Draw> getPaths(){
            return this.paths;
        }
    }
}