package com.magic.akash.magicbox;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.DrawableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

//Implementing the interface OnTabSelectedListener to our MainActivity
//This interface would help in swiping views
public class Display extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    SQLiteDatabase sqLiteDatabase;
    final Context c = this;
    ImageButton btn_click;
    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        final int[] ICONS = new int[]{
                R.drawable.j,
                R.drawable.l,
                R.drawable.h,
                R.drawable.i,
                R.drawable.k,
                R.drawable.m,
        };


        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs using addTab() method

        tabLayout.addTab(tabLayout.newTab().setText(" Chart ").setIcon(ICONS[4]));
        tabLayout.addTab(tabLayout.newTab().setText(" Food & Drinks ").setIcon(ICONS[0]));
        tabLayout.addTab(tabLayout.newTab().setText(" Shopping ").setIcon(ICONS[2]));
        tabLayout.addTab(tabLayout.newTab().setText(" Travel ").setIcon(ICONS[3]));
        tabLayout.addTab(tabLayout.newTab().setText(" Recharge ").setIcon(ICONS[1]));
        tabLayout.addTab(tabLayout.newTab().setText(" Others ").setIcon(ICONS[5]));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.pager);

        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);


        sqLiteDatabase = openOrCreateDatabase("OLBE_DEMO", android.content.Context.MODE_PRIVATE, null);//create database
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_demo_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_recharge_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_shoping_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_transport_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_other_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table








    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


}