package com.magic.akash.magicbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class ChooseCountry extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private Spinner spinner;
    private static final String[]paths = {"Choose Country","India", "Philips", "UK","Sweden", "Canada", "Australia", "Serbia", "Malaysia"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);


        sharedPreferences=getSharedPreferences("DATABASE",MODE_PRIVATE);
        final SharedPreferences.Editor se = sharedPreferences.edit();

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(ChooseCountry.this, android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                switch (i) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                    case 1:
                        // Whatever you want to happen when the first item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                    case 2:
                        // Whatever you want to happen when the second item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                    case 3:
                        // Whatever you want to happen when the second item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                    case 4:
                        // Whatever you want to happen when the second item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                    case 5:
                        // Whatever you want to happen when the second item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                    case 6:
                        // Whatever you want to happen when the second item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                    case 7:
                        // Whatever you want to happen when the second item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                    case 8:
                        // Whatever you want to happen when the second item gets selected
                        se.putInt("flag_value",i);
                        se.commit();
                        break;
                }

                Button btn = (Button) findViewById(R.id.main_custom_button);
                assert btn != null;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(i!=0) {
                            Intent intent9 = new Intent(ChooseCountry.this, MainActivity.class);
                            startActivity(intent9);
                            finish();
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            Toast.makeText(ChooseCountry.this, "All set ,welcome to MagicBank", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ChooseCountry.this, "Choose your country", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    //for two button exit
    private long lastPressedTime;
    private static final int PERIOD = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Press again to exit.", Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                    }
                    return true;
            }
        }
        return false;
    }


}
