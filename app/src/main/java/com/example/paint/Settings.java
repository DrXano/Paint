package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private int color;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.Settings_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.radioGroup = (RadioGroup)findViewById(R.id.colors);

        this.prefs = getSharedPreferences(TelaActivity.PREFS, MODE_PRIVATE);
        this.editor = this.prefs.edit();

        if(prefs.contains("optId"))
            this.radioGroup.check(prefs.getInt("optId",0));
    }

    public void save(View view){
        Intent i = new Intent(this,TelaActivity.class);
        i.putExtra("bgColor",color);
        editor.putInt("optId",this.radioGroup.getCheckedRadioButtonId());
        editor.apply();
        setResult(RESULT_OK,i);
        super.finish();
    }


    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.white:
                if (checked)
                    color = R.color.white;
                    break;
            case R.id.brown:
                if (checked)
                    color = R.color.brown;
                    break;
            case R.id.black:
                if (checked)
                    color = R.color.black;
                    break;
            case R.id.pink:
                if (checked)
                    color = R.color.pink;
                    break;
            case R.id.red:
                if (checked)
                    color = R.color.red;
                    break;
            case R.id.orange:
                if (checked)
                    color = R.color.orange;
                    break;
            case R.id.yellow:
                if (checked)
                    color = R.color.yellow;
                    break;
            case R.id.lightgreen:
                if (checked)
                    color = R.color.lightgreen;
                    break;
            case R.id.darkgreen:
                if (checked)
                    color = R.color.darkgreen;
                    break;
            case R.id.darkblue:
                if (checked)
                    color = R.color.darkblue;
                    break;
            case R.id.lightblue:
                if (checked)
                    color = R.color.lightblue;
                    break;
            case R.id.purple:
                if (checked)
                    color = R.color.purple;
                    break;
        }
    }

}