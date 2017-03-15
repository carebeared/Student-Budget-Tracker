package com.magic.akash.magicbox;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

public class Budget extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    BarChart chart, chart_recharge, chart_shoping,chart_transport,chart_debt,chart_other;
    BarDataSet Bardataset, Bardataset_recharge, Bardataset_shoping,Bardataset_transport,Bardataset_debt,Bardataset_other;
    BarData BARDATA, BARDATA_recharge, BARDATA_shoping,BARDATA_transport,BARDATA_debt,BARDATA_other;
    Integer amount_fooding, amount_recharge, amount_shoping,amount_transport,amount_debt,amount_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        sqLiteDatabase = openOrCreateDatabase("OLBE_DEMO", MODE_PRIVATE, null);//create database


//DEFINING ATRIBUTE
        chart = (BarChart) findViewById(R.id.chart1);
        ArrayList<BarEntry> BARENTRY = new ArrayList<>();
        ArrayList<String> BarEntryLabels = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_demo(name VARCHAR,email VARCHAR)");//create table
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(name) FROM student_demo", null);
        if (cursor.moveToFirst()) {
            amount_fooding = cursor.getInt(0);
        }

//ENTRY
        BARENTRY.add(new BarEntry(0, 0));
        BARENTRY.add(new BarEntry(amount_fooding, 1));
        BarEntryLabels.add("");
        BarEntryLabels.add("");
        Bardataset = new BarDataSet(BARENTRY, "");
        BARDATA = new BarData(BarEntryLabels, Bardataset);
        chart.setData(BARDATA);
        Bardataset.setBarSpacePercent(0f);

//CUSTOMIZE
        chart.setTouchEnabled(false);
        chart.animateY(3000);
        //chart.setNoDataText("hey there");
        // chart.setDrawBorders(false);
        chart.setDescription("");
        chart.setDrawValueAboveBar(false);
        chart.getAxisLeft().setDrawLabels(false);
        //chart.getAxisRight().setDrawLabels(false);
        //chart.getXAxis().setDrawLabels(false);
        chart.setDrawBorders(true);
        chart.getLegend().setEnabled(false);// Hide the legend
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);







//DEFINING ATRIBUTE
        chart_recharge = (BarChart) findViewById(R.id.chart_recharge);
        ArrayList<BarEntry> BARENTRY_recharge = new ArrayList<>();
        ArrayList<String> BarEntryLabels_recharge = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_recharge(name VARCHAR,email VARCHAR)");//create table

        Cursor cursor_recharge = sqLiteDatabase.rawQuery("SELECT SUM(name) FROM student_recharge", null);
        if (cursor_recharge.moveToFirst()) {
            amount_recharge = cursor_recharge.getInt(0);
        }

//ENTRY
        BARENTRY_recharge.add(new BarEntry(0, 0));
        BARENTRY_recharge.add(new BarEntry(amount_recharge, 1));
        BarEntryLabels_recharge.add("");
        BarEntryLabels_recharge.add("");
        Bardataset_recharge = new BarDataSet(BARENTRY_recharge, "");
        BARDATA_recharge = new BarData(BarEntryLabels_recharge, Bardataset_recharge);
        chart_recharge.setData(BARDATA_recharge);
        Bardataset_recharge.setBarSpacePercent(0f);

//CUSTOMIZE
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
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);





//DEFINING ATRIBUTE
        chart_shoping = (BarChart) findViewById(R.id.chart_shoping);
        ArrayList<BarEntry> BARENTRY_shoping = new ArrayList<>();
        ArrayList<String> BarEntryLabels_shoping = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_shoping(name VARCHAR,email VARCHAR)");//create table
        Cursor cursor_shoping  = sqLiteDatabase.rawQuery("SELECT SUM(name) FROM student_shoping", null);
        if(cursor_shoping.moveToFirst())
        {
            amount_shoping= cursor_shoping.getInt(0);
        }

//ENTRY
        BARENTRY_shoping.add(new BarEntry(0, 0));
        BARENTRY_shoping.add(new BarEntry(amount_shoping, 1));
        BarEntryLabels_shoping.add("");
        BarEntryLabels_shoping.add("");
        Bardataset_shoping = new BarDataSet(BARENTRY_shoping, "");
        BARDATA_shoping = new BarData(BarEntryLabels_shoping, Bardataset_shoping);
        chart_shoping.setData(BARDATA_shoping);
        Bardataset_shoping.setBarSpacePercent(0f);

//CUSTOMIZE
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
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);













//DEFINING ATRIBUTE
        chart_transport = (BarChart) findViewById(R.id.chart_transport);
        ArrayList<BarEntry> BARENTRY_transport = new ArrayList<>();
        ArrayList<String> BarEntryLabels_transport = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_transport(name VARCHAR,email VARCHAR)");//create table
        Cursor cursor_transport  = sqLiteDatabase.rawQuery("SELECT SUM(name) FROM student_transport", null);
        if(cursor_transport.moveToFirst())
        {
            amount_transport= cursor_transport.getInt(0);
        }

//ENTRY
        BARENTRY_transport.add(new BarEntry(0, 0));
        BARENTRY_transport.add(new BarEntry(amount_transport, 1));
        BarEntryLabels_transport.add("");
        BarEntryLabels_transport.add("");
        Bardataset_transport = new BarDataSet(BARENTRY_transport, "");
        BARDATA_transport = new BarData(BarEntryLabels_transport, Bardataset_transport);
        chart_transport.setData(BARDATA_transport);
        Bardataset_transport.setBarSpacePercent(0f);

//CUSTOMIZE
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
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);




















        //DEBT

//DEFINING ATRIBUTE
        chart_debt = (BarChart) findViewById(R.id.chart_debt);
        ArrayList<BarEntry> BARENTRY_debt = new ArrayList<>();
        ArrayList<String> BarEntryLabels_debt = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_debt(name VARCHAR,email VARCHAR)");//create table
        Cursor cursor_debt  = sqLiteDatabase.rawQuery("SELECT SUM(name) FROM student_debt", null);
        if(cursor_debt.moveToFirst())
        {
            amount_debt= cursor_debt.getInt(0);
        }

//ENTRY
        BARENTRY_debt.add(new BarEntry(0, 0));
        BARENTRY_debt.add(new BarEntry(amount_debt, 1));
        BarEntryLabels_debt.add("");
        BarEntryLabels_debt.add("");
        Bardataset_debt = new BarDataSet(BARENTRY_debt, "");
        BARDATA_debt = new BarData(BarEntryLabels_debt, Bardataset_debt);
        chart_debt.setData(BARDATA_debt);
        Bardataset_debt.setBarSpacePercent(0f);

//CUSTOMIZE
        chart_debt.setTouchEnabled(false);
        chart_debt.animateY(3000);
        //chart.setNoDataText("hey there");
        // chart.setDrawBorders(false);
        chart_debt.setDescription("");
        chart_debt.setDrawValueAboveBar(false);
        chart_debt.getAxisLeft().setDrawLabels(false);
        //chart.getAxisRight().setDrawLabels(false);
        //chart.getXAxis().setDrawLabels(false);
        chart_debt.setDrawBorders(true);
        chart_debt.getLegend().setEnabled(false);// Hide the legend
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);









        //OTHER

//DEFINING ATRIBUTE
        chart_other = (BarChart) findViewById(R.id.chart_other);
        ArrayList<BarEntry> BARENTRY_other = new ArrayList<>();
        ArrayList<String> BarEntryLabels_other = new ArrayList<String>();
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_other(name VARCHAR,email VARCHAR)");//create table
        Cursor cursor_other  = sqLiteDatabase.rawQuery("SELECT SUM(name) FROM student_other", null);
        if(cursor_other .moveToFirst())
        {
            amount_other= cursor_other .getInt(0);
        }

//ENTRY
        BARENTRY_other.add(new BarEntry(0, 0));
        BARENTRY_other.add(new BarEntry(amount_other, 1));
        BarEntryLabels_other.add("");
        BarEntryLabels_other.add("");
        Bardataset_other = new BarDataSet(BARENTRY_other, "");
        BARDATA_other = new BarData(BarEntryLabels_other, Bardataset_other);
        chart_other.setData(BARDATA_other);
        Bardataset_other.setBarSpacePercent(0f);

//CUSTOMIZE
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
        //chart.getAxisLeft().setDrawGridLines(false);
        //chart.getXAxis().setDrawGridLines(false);
        //chart.getAxisRight().setDrawGridLines(false);





    }

}
