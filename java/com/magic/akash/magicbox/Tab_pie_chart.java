package com.magic.akash.magicbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

public class Tab_pie_chart extends Fragment {
    PieChart mChart;
    SQLiteDatabase sqLiteDatabase;
    Integer amount_fooding, amount_recharge, amount_shoping,amount_transport,amount_total,amount_other,amount_saving;
    TextView tv;
    ImageButton btn_click;
    // we're going to display pie chart for school attendance


    // colors for different sections in pieChart
    public static  final int[] MY_COLORS = {
            Color.parseColor("#9400D3"), Color.parseColor("#8FBC8F"), Color.parseColor("#FFA500"),
            Color.parseColor("#5F9EA0"),  Color.parseColor("#1e90ff")
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.activity_tab_pie_chart, container, false);
        //Returning the layout file after inflating

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        /*AdView mAdView = (AdView) getActivity().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("8E19E8A296564FDAAFB2E816705A798B").build();
        mAdView.loadAd(adRequest);
*/
        mChart = (PieChart) getActivity().findViewById(R.id.chart1);
        //   mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setClickable(false);
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(false);
        // setting sample Data for Pie Chart
        setDataForPieChart();
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        sqLiteDatabase = getActivity().openOrCreateDatabase("OLBE_DEMO", android.content.Context.MODE_PRIVATE, null);//create database

        btn_click= (ImageButton) getActivity().findViewById(R.id.imageButton_del_all);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_del_total_expenses, null);
                final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                alertDialogBuilderUserInput.setView(mView);
                final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                Button btn= (Button) mView.findViewById(R.id.buttona);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                        alertDialogBuilderUserInput.setView(mView);
                        alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                //after reset delete expenditure
                                sqLiteDatabase.delete("student_demo_p_view", null, null);
                                Toast.makeText(getContext(), "Expeneses deleted successful !!", Toast.LENGTH_SHORT).show();
                                alertDialogAndroid.dismiss();
                                getActivity().finish();
                                Intent i= new Intent(getContext(),Display.class);
                                startActivity(i);

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


                Button btn1= (Button) mView.findViewById(R.id.buttonb);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                        alertDialogBuilderUserInput.setView(mView);
                        alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                //after reset delete expenditure
                                sqLiteDatabase.delete("student_recharge_p_view", null, null);
                                Toast.makeText(getContext(), "Expeneses deleted successful !!", Toast.LENGTH_SHORT).show();
                                alertDialogAndroid.dismiss();
                                getActivity().finish();
                                Intent i= new Intent(getContext(),Display.class);
                                startActivity(i);
//CUSTOMIZE
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


                Button btn2= (Button) mView.findViewById(R.id.buttonc);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                        alertDialogBuilderUserInput.setView(mView);
                        alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                sqLiteDatabase.delete("student_shoping_p_view", null, null);
                                Toast.makeText(getContext(), "Expeneses deleted successful !!", Toast.LENGTH_SHORT).show();
                                alertDialogAndroid.dismiss();
                                getActivity().finish();
                                Intent i= new Intent(getContext(),Display.class);
                                startActivity(i);
//CUSTOMIZE
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


                Button btn3= (Button) mView.findViewById(R.id.buttond);
                btn3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                        alertDialogBuilderUserInput.setView(mView);
                        alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {

                                //after reset delete expenditure
                                sqLiteDatabase.delete("student_transport_p_view", null, null);
                                Toast.makeText(getContext(), "Expeneses deleted successful !!", Toast.LENGTH_SHORT).show();
                                alertDialogAndroid.dismiss();
                                getActivity().finish();
                                Intent i= new Intent(getContext(),Display.class);
                                startActivity(i);
//CUSTOMIZE
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


                Button btn4= (Button) mView.findViewById(R.id.buttone);
                btn4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_alert_2, null);
                        final AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                        alertDialogBuilderUserInput.setView(mView);
                        alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                sqLiteDatabase.delete("student_other_p_view", null, null);
                                Toast.makeText(getContext(), "Expeneses deleted successful !!", Toast.LENGTH_SHORT).show();
                                alertDialogAndroid.dismiss();
                                getActivity().finish();
                                Intent i= new Intent(getContext(),Display.class);
                                startActivity(i);
//CUSTOMIZE
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
                alertDialogAndroid.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialogAndroid.dismiss();
                alertDialogAndroid.show();
                alertDialogAndroid.setCanceledOnTouchOutside(true);
            }
        });


    }



































    public void setDataForPieChart() {

        final String[]symbol = {"","₹","₱","£","kr","$","$","Дин.","RM","Rf."};
        sqLiteDatabase = getActivity().openOrCreateDatabase("OLBE_DEMO", android.content.Context.MODE_PRIVATE, null);//create database
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_demo_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        Cursor cursor_fooding = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_demo_p_view", null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_recharge_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        Cursor cursor_recharge = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_recharge_p_view", null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_shoping_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        Cursor cursor_shoping = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_shoping_p_view", null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_transport_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        Cursor cursor_transport = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_transport_p_view", null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_other_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        Cursor cursor_other = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_other_p_view", null);
        if (cursor_fooding.moveToFirst()) {amount_fooding = cursor_fooding.getInt(0);}
        if (cursor_recharge.moveToFirst()) {amount_recharge = cursor_recharge.getInt(0);}
        if (cursor_shoping.moveToFirst()) {amount_shoping = cursor_shoping.getInt(0);}
        if (cursor_transport.moveToFirst()) {amount_transport = cursor_transport.getInt(0);}
        if (cursor_other.moveToFirst()) {amount_other = cursor_other.getInt(0);}

//setting text
        SimpleDateFormat dateF = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
       int yopo= amount_fooding+ amount_recharge+ amount_shoping+amount_transport+amount_other;
        tv = (TextView) getActivity().findViewById(R.id.chart_content);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DATABASE", getActivity().MODE_PRIVATE);
        String rupee = getResources().getString(R.string.Rs);

        Locale defaultLocale = Locale.getDefault();
        Currency currency = Currency.getInstance(defaultLocale);
        tv.setText("Your Total Expenditute : " + symbol[sharedPreferences.getInt("flag_value", 0)] + String.valueOf(yopo));
        TextView tv_1 = (TextView) getActivity().findViewById(R.id.chart_content_1);
        tv_1.setText("Till date :"+date);


         int[] yValues = {amount_fooding, amount_recharge, amount_shoping,amount_transport,amount_other};
         String[] xValues = {"Fooding", "Recharge", "Shopping","Transport","Others"};


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yValues.length; i++) {
           if(yValues[i]!=0) {
               yVals1.add(new Entry(yValues[i], i));
           }
        }
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xValues.length; i++) {
            if(yValues[i]!=0) {
                xVals.add(xValues[i]);
            }
        }
        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(xVals, dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());


        data.setValueTextSize(7f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);
        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);
        // Legends to show on bottom of the graph
       Legend l = mChart.getLegend();
        l.setTextColor(Color.parseColor("#66000000"));
       /*  l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(5);
        l.setYEntrySpace(10);*/
    }














}
