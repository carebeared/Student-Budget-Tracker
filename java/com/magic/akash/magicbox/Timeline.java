package com.magic.akash.magicbox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Timeline extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    BarChart chart, chart_recharge, chart_shoping,chart_transport,chart_total,chart_other;
    BarDataSet Bardataset, Bardataset_recharge, Bardataset_shoping,Bardataset_transport,Bardataset_total,Bardataset_other;
    BarData BARDATA, BARDATA_recharge, BARDATA_shoping,BARDATA_transport,BARDATA_total,BARDATA_other;
    Integer amount_fooding, amount_recharge, amount_shoping,amount_transport,amount_total,amount_other;
    TextView tv_fooding,tv_total;
    SharedPreferences sharedPreferences;
    final Context c = this;
    ImageButton img,img2,img3,img4,img5,img_all;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);



 //imagebutton
        iv= (ImageView) findViewById(R.id.imageView2);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });



















        sqLiteDatabase = openOrCreateDatabase("OLBE_DEMO", MODE_PRIVATE, null);//create database
        sharedPreferences = getSharedPreferences("DATABASE", MODE_PRIVATE);

//DEFINING ATRIBUTE
        chart = (BarChart) findViewById(R.id.chart1);
        final ArrayList<BarEntry> BARENTRY = new ArrayList<>();
        ArrayList<String> BarEntryLabels = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_demo_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_demo_p", null);
        if (cursor.moveToFirst()) {
            amount_fooding = cursor.getInt(0);
        }
        //SharedPreferences.Editor se=sharedPreferences.edit();
        //int p=Integer.parseInt(sharedPreferences.getString("k",""));
        final int p = sharedPreferences.getInt("k", 0);
//ENTRY
        BARENTRY.add(new BarEntry(p, 0));
        BARENTRY.add(new BarEntry(amount_fooding, 1));
        BarEntryLabels.add("");
        BarEntryLabels.add("");
        Bardataset = new BarDataSet(BARENTRY, "");
        BARDATA = new BarData(BarEntryLabels, Bardataset);
        chart.setData(BARDATA);
        Bardataset.setBarSpacePercent(0);
        Bardataset.setColor(Color.parseColor("#50800000"));
        chart.setTouchEnabled(false);
        chart.animateY(3000);
        chart.getAxisRight().setTextSize(8f);
        //chart.setNoDataText("hey there");
        //chart.setDrawBorders(false);
        chart.setDescription("");
        chart.setDrawValueAboveBar(false);
        chart.getAxisLeft().setDrawLabels(false);
        //chart.getAxisRight().setDrawLabels(false);
        //chart.getXAxis().setDrawLabels(false);
        chart.setDrawBorders(true);
        chart.getLegend().setEnabled(false);// Hide the legend
        chart.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);
        chart.setBorderColor(Color.parseColor("#50D3D3D3"));
        chart.getAxisRight().setAxisMinValue(0);
        chart.getAxisLeft().setAxisMinValue(0);
//currency
        Locale defaultLocale = Locale.getDefault();
        Currency currency = Currency.getInstance(defaultLocale);
//SET TEXT
        tv_fooding = (TextView) findViewById(R.id.fooding3);
        String rupee = getResources().getString(R.string.Rs);
        tv_fooding.setText("spend : " +(sharedPreferences.getInt("flag_value", 1)==1 ? rupee : currency.getSymbol() )+ String.valueOf(amount_fooding));

        //Bardataset.setColor(Color.rgb(153, 193, 12));


        img = (ImageButton) findViewById(R.id.imageButton_del_fooding);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        sqLiteDatabase.delete("student_demo_p", null, null);
                        Toast.makeText(Timeline.this, "Expenses in food is Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter1, R.anim.exit1);
//CUSTOMIZE
                    }
                }).setNegativeButton("Delete Last Entry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                try {
                                    final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM student_demo_p_view", null);
                                    cursor.moveToLast();
                                    String id1 = cursor.getString(0);
                                    sqLiteDatabase.delete("student_demo_p_view", "id=?", new String[]{String.valueOf(id1)});
                                    //Toast.makeText(MainActivity.this, "" + id1, Toast.LENGTH_SHORT).show();


                                    final Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT * FROM student_demo_p", null);
                                    cursor1.moveToLast();
                                    String id2 = cursor1.getString(0);
                                    sqLiteDatabase.delete("student_demo_p", "amount=?", new String[]{String.valueOf(id2)});
                                    Toast.makeText(Timeline.this, "Last Entry Deleted !", Toast.LENGTH_SHORT).show();

                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter1, R.anim.exit1);
                                }
                                catch (Exception e) {
                                    Toast.makeText(Timeline.this, "No Data Available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.setCanceledOnTouchOutside(true);
                alertDialogAndroid.show();
            }
        });


//DEFINING ATRIBUTE
        chart_recharge = (BarChart) findViewById(R.id.chart_recharge);
        ArrayList<BarEntry> BARENTRY_recharge = new ArrayList<>();
        ArrayList<String> BarEntryLabels_recharge = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_recharge_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table

        Cursor cursor_recharge = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_recharge_p", null);
        if (cursor_recharge.moveToFirst()) {
            amount_recharge = cursor_recharge.getInt(0);
        }
        // Toast.makeText(Timeline.this, ""+sharedPreferences.getString("k1",""), Toast.LENGTH_SHORT).show();
        //int p1=Integer.parseInt(sharedPreferences.getString("k1",""));
        int p1 = sharedPreferences.getInt("k1", 0);
//ENTRY
        BARENTRY_recharge.add(new BarEntry(p1, 0));
        BARENTRY_recharge.add(new BarEntry(amount_recharge, 1));
        BarEntryLabels_recharge.add("");
        BarEntryLabels_recharge.add("");
        Bardataset_recharge = new BarDataSet(BARENTRY_recharge, "");
        BARDATA_recharge = new BarData(BarEntryLabels_recharge, Bardataset_recharge);
        chart_recharge.setData(BARDATA_recharge);
        Bardataset_recharge.setBarSpacePercent(0);

//CUSTOMIZE
        Bardataset_recharge.setColor(Color.parseColor("#50DC143C"));
        // Bardataset_recharge.setColor(R.color.box);
        chart_recharge.setTouchEnabled(false);
        chart_recharge.animateY(3000);
        //chart.setNoDataText("hey there");
        // chart.setDrawBorders(false);
        chart_recharge.setDescription("");
        chart_recharge.setDrawValueAboveBar(false);
        chart_recharge.getAxisLeft().setDrawLabels(false);
        //chart.getAxisRight().setDrawLabels(false);
        //chart.getXAxis().setDrawLabels(false);
        chart_recharge.setDrawBorders(true);
        chart_recharge.getLegend().setEnabled(false);// Hide the legend
        chart_recharge.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);
        chart_recharge.getAxisRight().setAxisMinValue(0);
        chart_recharge.getAxisLeft().setAxisMinValue(0);
        chart_recharge.setBorderColor(Color.parseColor("#50D3D3D3"));
        chart_recharge.getAxisRight().setTextSize(8f);
        //SET TEXT
        tv_fooding = (TextView) findViewById(R.id.recharge3);
        tv_fooding.setText("spend : " + (sharedPreferences.getInt("flag_value", 1)==1 ? rupee : currency.getSymbol() ) + String.valueOf(amount_recharge));


        img2 = (ImageButton) findViewById(R.id.imageButton_del_recharge);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        sqLiteDatabase.delete("student_recharge_p", null, null);
                        Toast.makeText(Timeline.this, "Expenses in Reacharge is Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter1, R.anim.exit1);
                    }
                }).setNegativeButton("Delete Last Entry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                try {
                                    final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM student_recharge_p_view", null);
                                    cursor.moveToLast();
                                    String id1 = cursor.getString(0);
                                    sqLiteDatabase.delete("student_recharge_p_view", "id=?", new String[]{String.valueOf(id1)});
                                    //Toast.makeText(MainActivity.this, "" + id1, Toast.LENGTH_SHORT).show();


                                    final Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT * FROM student_recharge_p", null);
                                    cursor1.moveToLast();
                                    String id2 = cursor1.getString(0);
                                    sqLiteDatabase.delete("student_recharge_p", "amount=?", new String[]{String.valueOf(id2)});
                                    Toast.makeText(Timeline.this, "Last Entry Deleted !", Toast.LENGTH_SHORT).show();

                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter1, R.anim.exit1);
                                }
                                catch (Exception e) {
                                    Toast.makeText(Timeline.this, "No Data Available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.setCanceledOnTouchOutside(true);
                alertDialogAndroid.show();
            }
        });





//DEFINING ATRIBUTE
        chart_shoping = (BarChart) findViewById(R.id.chart_shoping);
        ArrayList<BarEntry> BARENTRY_shoping = new ArrayList<>();
        ArrayList<String> BarEntryLabels_shoping = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_shoping_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        Cursor cursor_shoping = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_shoping_p", null);
        if (cursor_shoping.moveToFirst()) {
            amount_shoping = cursor_shoping.getInt(0);
        }
        int p2 = sharedPreferences.getInt("k2", 0);
//ENTRY
        BARENTRY_shoping.add(new BarEntry(p2, 0));
        BARENTRY_shoping.add(new BarEntry(amount_shoping, 1));
        BarEntryLabels_shoping.add("");
        BarEntryLabels_shoping.add("");
        Bardataset_shoping = new BarDataSet(BARENTRY_shoping, "");
        BARDATA_shoping = new BarData(BarEntryLabels_shoping, Bardataset_shoping);
        chart_shoping.setData(BARDATA_shoping);
        Bardataset_shoping.setBarSpacePercent(0);

//CUSTOMIZE
        Bardataset_shoping.setColor(Color.parseColor("#50FF00FF"));
        chart_shoping.setTouchEnabled(false);
        chart_shoping.animateY(3000);
        //chart.setNoDataText("hey there");
        // chart.setDrawBorders(false);
        chart_shoping.setDescription("");
        chart_shoping.setDrawValueAboveBar(false);
        chart_shoping.getAxisLeft().setDrawLabels(false);
        //chart.getAxisRight().setDrawLabels(false);
        //chart.getXAxis().setDrawLabels(false);
        chart_shoping.setDrawBorders(true);
        chart_shoping.getLegend().setEnabled(false);// Hide the legend
        chart_shoping.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);
        chart_shoping.getAxisRight().setAxisMinValue(0);
        chart_shoping.getAxisLeft().setAxisMinValue(0);
        chart_shoping.setBorderColor(Color.parseColor("#50D3D3D3"));
        chart_shoping.getAxisRight().setTextSize(8f);
        //SET TEXT
        tv_fooding = (TextView) findViewById(R.id.shoping3);
        tv_fooding.setText("spend : " + (sharedPreferences.getInt("flag_value", 1)==1 ? rupee : currency.getSymbol() ) + String.valueOf(amount_shoping));


        img3 = (ImageButton) findViewById(R.id.imageButton_del_shoping);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        sqLiteDatabase.delete("student_shoping_p", null, null);
                        Toast.makeText(Timeline.this, "Expenses in  Shopping is Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter1, R.anim.exit1);
                    }
                }).setNegativeButton("Delete Last Entry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                try {
                                    final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM student_shoping_p_view", null);
                                    cursor.moveToLast();
                                    String id1 = cursor.getString(0);
                                    sqLiteDatabase.delete("student_shoping_p_view", "id=?", new String[]{String.valueOf(id1)});
                                    //Toast.makeText(MainActivity.this, "" + id1, Toast.LENGTH_SHORT).show();


                                    final Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT * FROM student_shoping_p", null);
                                    cursor1.moveToLast();
                                    String id2 = cursor1.getString(0);
                                    sqLiteDatabase.delete("student_shoping_p", "amount=?", new String[]{String.valueOf(id2)});
                                    Toast.makeText(Timeline.this, "Last Entry Deleted !", Toast.LENGTH_SHORT).show();

                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter1, R.anim.exit1);
                                }
                                catch (Exception e) {
                                    Toast.makeText(Timeline.this, "No Data Available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.setCanceledOnTouchOutside(true);
                alertDialogAndroid.show();

            }
        });


//DEFINING ATRIBUTE
        chart_transport = (BarChart) findViewById(R.id.chart_transport);
        ArrayList<BarEntry> BARENTRY_transport = new ArrayList<>();
        ArrayList<String> BarEntryLabels_transport = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_transport_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        Cursor cursor_transport = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_transport_p", null);
        if (cursor_transport.moveToFirst()) {
            amount_transport = cursor_transport.getInt(0);
        }
        int p3 = sharedPreferences.getInt("k3", 0);
//ENTRY
        BARENTRY_transport.add(new BarEntry(p3, 0));
        BARENTRY_transport.add(new BarEntry(amount_transport, 1));
        BarEntryLabels_transport.add("");
        BarEntryLabels_transport.add("");
        Bardataset_transport = new BarDataSet(BARENTRY_transport, "");
        BARDATA_transport = new BarData(BarEntryLabels_transport, Bardataset_transport);
        chart_transport.setData(BARDATA_transport);
        Bardataset_transport.setBarSpacePercent(0);

//CUSTOMIZE
        Bardataset_transport.setColor(Color.parseColor("#508B008B"));
        chart_transport.setTouchEnabled(false);
        chart_transport.animateY(3000);
        //chart.setNoDataText("hey there");
        // chart.setDrawBorders(false);
        chart_transport.setDescription("");
        chart_transport.setDrawValueAboveBar(false);
        chart_transport.getAxisLeft().setDrawLabels(false);
        //chart.getAxisRight().setDrawLabels(false);
        //chart.getXAxis().setDrawLabels(false);
        chart_transport.setDrawBorders(true);
        chart_transport.getLegend().setEnabled(false);// Hide the legend
        chart_transport.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);
        chart_transport.getAxisRight().setAxisMinValue(0);
        chart_transport.getAxisLeft().setAxisMinValue(0);
        chart_transport.setBorderColor(Color.parseColor("#50D3D3D3"));
        chart_transport.getAxisRight().setTextSize(8f);
        //SET TEXT
        tv_fooding = (TextView) findViewById(R.id.transport3);
        tv_fooding.setText("spend : " + (sharedPreferences.getInt("flag_value", 1)==1 ? rupee : currency.getSymbol() )  + String.valueOf(amount_transport));


        img4 = (ImageButton) findViewById(R.id.imageButton_del_transport);
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        sqLiteDatabase.delete("student_transport_p", null, null);
                        Toast.makeText(Timeline.this, "Expenses in Transport is Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter1, R.anim.exit1);
                    }
                }).setNegativeButton("Delete Last Entry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                try {
                                    final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM student_transport_p_view", null);
                                    cursor.moveToLast();
                                    String id1 = cursor.getString(0);
                                    sqLiteDatabase.delete("student_transport_p_view", "id=?", new String[]{String.valueOf(id1)});
                                    //Toast.makeText(MainActivity.this, "" + id1, Toast.LENGTH_SHORT).show();


                                    final Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT * FROM student_transport_p", null);
                                    cursor1.moveToLast();
                                    String id2 = cursor1.getString(0);
                                    sqLiteDatabase.delete("student_transport_p", "amount=?", new String[]{String.valueOf(id2)});
                                    Toast.makeText(Timeline.this, "Last Entry Deleted !", Toast.LENGTH_SHORT).show();

                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter1, R.anim.exit1);
                                }
                                catch (Exception e) {
                                    Toast.makeText(Timeline.this, "No Data Available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.setCanceledOnTouchOutside(true);
                alertDialogAndroid.show();

            }
        });


        //OTHER

//DEFINING ATRIBUTE
        chart_other = (BarChart) findViewById(R.id.chart_other);
        ArrayList<BarEntry> BARENTRY_other = new ArrayList<>();
        ArrayList<String> BarEntryLabels_other = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_other_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        Cursor cursor_other = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_other_p", null);
        if (cursor_other.moveToFirst()) {
            amount_other = cursor_other.getInt(0);
        }

        int p5 = sharedPreferences.getInt("k5", 0);
//ENTRY
        BARENTRY_other.add(new BarEntry(p5, 0));
        BARENTRY_other.add(new BarEntry(amount_other, 1));
        BarEntryLabels_other.add("");
        BarEntryLabels_other.add("");
        Bardataset_other = new BarDataSet(BARENTRY_other, "");
        BARDATA_other = new BarData(BarEntryLabels_other, Bardataset_other);
        chart_other.setData(BARDATA_other);
        Bardataset_other.setBarSpacePercent(0);

//CUSTOMIZE
        Bardataset_other.setColor(Color.parseColor("#708FBC8F"));
        chart_other.setTouchEnabled(false);
        chart_other.animateY(3000);
        //chart.setNoDataText("hey there");
        // chart.setDrawBorders(false);
        chart_other.setDescription("");
        chart_other.setDrawValueAboveBar(false);
        chart_other.getAxisLeft().setDrawLabels(false);
        //chart.getAxisRight().setDrawLabels(false);
        //chart.getXAxis().setDrawLabels(false);
        chart_other.setDrawBorders(true);
        chart_other.getLegend().setEnabled(false);// Hide the legend
        //chart_other.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);
        chart_other.getAxisRight().setAxisMinValue(0);
        chart_other.getAxisLeft().setAxisMinValue(0);
        chart_other.setBorderColor(Color.parseColor("#50D3D3D3"));
        chart_other.getAxisRight().setTextSize(8f);
        //SET TEXT
        tv_fooding = (TextView) findViewById(R.id.other3);
        tv_fooding.setText("spend : " + (sharedPreferences.getInt("flag_value", 1)==1 ? rupee : currency.getSymbol() )  + String.valueOf(amount_other));


        img5 = (ImageButton) findViewById(R.id.imageButton_del_other);
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Delete All", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        sqLiteDatabase.delete("student_other_p", null, null);
                        Toast.makeText(Timeline.this, "Expenses in Others is Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter1, R.anim.exit1);
                    }
                }).setNegativeButton("Delete Last Entry",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                try {
                                    final Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM student_other_p_view", null);
                                    cursor.moveToLast();
                                    String id1 = cursor.getString(0);
                                    sqLiteDatabase.delete("student_other_p_view", "id=?", new String[]{String.valueOf(id1)});
                                    //Toast.makeText(MainActivity.this, "" + id1, Toast.LENGTH_SHORT).show();


                                    final Cursor cursor1 = sqLiteDatabase.rawQuery("SELECT * FROM student_other_p", null);
                                    cursor1.moveToLast();
                                    String id2 = cursor1.getString(0);
                                    sqLiteDatabase.delete("student_other_p", "amount=?", new String[]{String.valueOf(id2)});
                                    Toast.makeText(Timeline.this, "Last Entry Deleted !", Toast.LENGTH_SHORT).show();

                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.enter1, R.anim.exit1);
                                }
                                catch (Exception e) {
                                    Toast.makeText(Timeline.this, "No Data Available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.setCanceledOnTouchOutside(true);
                alertDialogAndroid.show();

            }
        });


        //TOTAL

//DEFINING ATRIBUTE
        chart_total = (BarChart) findViewById(R.id.chart_total);
        ArrayList<BarEntry> BARENTRY_total = new ArrayList<>();
        ArrayList<String> BarEntryLabels_total = new ArrayList<String>();

        amount_total = amount_transport + amount_other + amount_fooding + amount_shoping + amount_recharge;
        int p_total = sharedPreferences.getInt("k_total", 0);
//ENTRY
        BARENTRY_total.add(new BarEntry(p_total, 0));
        BARENTRY_total.add(new BarEntry(amount_total, 1));
        BarEntryLabels_total.add("");
        BarEntryLabels_total.add("");
        Bardataset_total = new BarDataSet(BARENTRY_total, "");
        BARDATA_total = new BarData(BarEntryLabels_total, Bardataset_total);
        chart_total.setData(BARDATA_total);
        Bardataset_total.setBarSpacePercent(0f);

//CUSTOMIZE
        Bardataset_total.setColor(Color.parseColor("#80FF4500"));
        chart_total.setTouchEnabled(false);
        chart_total.animateY(3000);
        //chart.setNoDataText("hey there");
        // chart.setDrawBorders(false);
        chart_total.setDescription("");
        chart_total.setDrawValueAboveBar(false);
        chart_total.getAxisLeft().setDrawLabels(false);
        //chart.getAxisRight().setDrawLabels(false);
        //chart.getXAxis().setDrawLabels(false);
        chart_total.setDrawBorders(true);
        chart_total.getLegend().setEnabled(false);// Hide the legend
        chart_total.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);
        chart_total.getAxisRight().setAxisMinValue(0);
        chart_total.getAxisLeft().setAxisMinValue(0);

        chart_total.setBorderColor(Color.parseColor("#50D3D3D3"));
        chart_total.getAxisRight().setTextSize(9f);
        img_all = (ImageButton) findViewById(R.id.imageButton_del_all);
        img_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert, null);
                final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {
                        sqLiteDatabase.delete("student_demo_p", null, null);
                        sqLiteDatabase.delete("student_recharge_p", null, null);
                        sqLiteDatabase.delete("student_shoping_p", null, null);
                        sqLiteDatabase.delete("student_transport_p", null, null);
                        sqLiteDatabase.delete("student_other_p", null, null);
                        Toast.makeText(Timeline.this, "Budget deleted successful !!", Toast.LENGTH_LONG).show();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        overridePendingTransition(R.anim.enter1, R.anim.exit1);
                    }
                }).setNegativeButton("No Way",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.setCanceledOnTouchOutside(true);
                alertDialogAndroid.show();
            }
        });

//SET TEXT
        tv_total = (TextView) findViewById(R.id.total);
        tv_total.setText("spend : " + (sharedPreferences.getInt("flag_value", 1)==1 ? rupee : currency.getSymbol() )  + String.valueOf(amount_total));



        //date
        String dt = sharedPreferences.getString("k_date", "");  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 30);

        SimpleDateFormat dateF = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());

        try {

            //Dates to compare
            String CurrentDate = date;
            String FinalDate = dt;

            Date date1;
            Date date2;

            SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");

            //Setting dates
            date1 = dates.parse(CurrentDate);
            date2 = dates.parse(FinalDate);

            //Comparing dates
            long difference =  date1.getTime()-date2.getTime();
            final long differenceDates = difference / (24 * 60 * 60 * 1000);

            //Convert long to String
            String dayDifference = Long.toString(differenceDates);
            TextView tv_date = (TextView) findViewById(R.id.date);
            tv_date.setText(dayDifference + "  days over");


        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }


    }

}
