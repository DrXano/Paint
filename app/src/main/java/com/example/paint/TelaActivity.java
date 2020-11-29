package com.example.paint;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import interfaces.CanvasInterface;
import interfaces.gndListener;

public class TelaActivity extends AppCompatActivity implements gndListener {

    public static final String SAVED_CANVAS = "savedcanvas";

    public static final String PREFS = "Paintprefs";
    private static final int REQUEST_CODE = 1;

    int color;
    Fragment canvas;
    Fragment palette = new Palette();
    Fragment map = new MapsFragment();
    Fragment currenttt;
    FirebaseDatabase database;
    private CanvasInterface listener;
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

        canvas = new Canvas();
        setListener((CanvasInterface) canvas);

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
                GetNameDialog gnd = new GetNameDialog();
                gnd.show(getSupportFragmentManager(), "draw name dialog");
                return true;
            case R.id.load:
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("draws")){
                            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("draws");
                            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    List<String> names = new ArrayList<>();
                                    for(DataSnapshot ds : snapshot.getChildren()) {
                                        names.add(ds.getKey());
                                    }
                                    chooseDraw(names);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), "There are no Draws", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                //listener.load();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void chooseDraw(final List<String> names) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the draw to be loaded");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, names);

        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.load(names.get(which));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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

    public void setListener(CanvasInterface listener) {
        this.listener = listener;
    }

    @Override
    public void saveWithName(String drawname) {
        listener.save(drawname);
    }
}