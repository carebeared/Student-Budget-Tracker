
package com.magic.akash.magicbox;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class welcomeScreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    final Context c = this;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
//database open
        sqLiteDatabase = openOrCreateDatabase("OLBE_DEMO", MODE_PRIVATE, null);//create database
        sharedPreferences=getSharedPreferences("DATABASE",MODE_PRIVATE);

        final EditText user_Input = (EditText) findViewById(R.id.userInputDialog);
        final Button btn= (Button) findViewById(R.id.button3);
        assert btn != null;
        assert user_Input != null;
        user_Input.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btn.performClick();
                    return true;
                }
                return false;
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ToDo get user input here

                String s_budget = user_Input.getText().toString();
                user_Input.requestFocus();
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                if(!user_Input.getText().toString().isEmpty()) {
                    if (user_Input.getText().toString().length() <= 8) {
                        SimpleDateFormat dateF = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                        SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String date = dateF.format(Calendar.getInstance().getTime());
                        String time = timeF.format(Calendar.getInstance().getTime());

//making database, table
                        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_total_budget_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
                        sqLiteDatabase.execSQL("INSERT INTO student_total_budget_p(amount,dateyo,timeyo) VALUES('" + s_budget + "','" + date + "','" + time + "')");//insert data fetch through edit text
//sharedprefrece budget

                        int s = Integer.parseInt(user_Input.getText().toString());
                        SharedPreferences.Editor se = sharedPreferences.edit();
                        se.putInt("k_total", s);
                        se.putString("k_date", date);
                        se.commit();
                        //Toast.makeText(welcomeScreen.this, "Your budget limit is set : " + sharedPreferences.getInt("k_total", 0), Toast.LENGTH_LONG).show();
                        // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


                        Intent intent = new Intent(welcomeScreen.this, ChooseCountry.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        // Toast.makeText(welcomeScreen.this, "Welcome to magicBank", Toast.LENGTH_SHORT).show();


                        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(welcomeScreen.this).setSmallIcon(R.drawable.qw).setContentTitle("Total Budget is Set !").setContentText("Rs " + String.valueOf(sharedPreferences.getInt("k_total", 0)) + " set until next Reset Budget");
                        Intent notificationIntent = new Intent(welcomeScreen.this, MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(welcomeScreen.this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        builder.setSound(alarmSound);
                        // Add as notification
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
                        finish();

                    }
                    else {
                        Toast.makeText(welcomeScreen.this, "Please Enter valid Amount !", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(welcomeScreen.this, "EMPTY , Put amount again", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    //for double tab exit
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
                        Toast.makeText(getApplicationContext(), "Press again to exit.",
                                Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                    }
                    return true;
            }
        }
        return false;
    }

}

