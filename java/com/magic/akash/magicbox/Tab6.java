package com.magic.akash.magicbox;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;


//Our class extending fragment
public class Tab6  extends Fragment{
    SQLiteDatabase sqLiteDatabase;
    ArrayList arrayList=new ArrayList();
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.activity_tab6, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {

        //Returning the layout file after inflating
        sqLiteDatabase = getActivity().openOrCreateDatabase("OLBE_DEMO", android.content.Context.MODE_PRIVATE, null);//create database
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_other_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        Cursor cursor1=sqLiteDatabase.rawQuery( "SELECT  strftime('%Y-%m',dateyo) as month_of_year, sum(amount) as total FROM student_other_p_view GROUP BY month_of_year",null);
        int i=0;
        BarChart lineChart = (BarChart) getActivity().findViewById(R.id.chart60);
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
        while(cursor1.moveToNext()) {
            float data2 = cursor1.getFloat(1);
            entries.add(new BarEntry(data2, i));
            String data1=cursor1.getString(0);
            String str=data1.substring(5,7);
            String str2=data1.substring(2,4);
            String month_string[]={"Jan", "FEB", "MAR","apr","MAY", "JUN","Jul","AUG","SEP","OCT","NOV","DEC"};
            labels.add(month_string[Integer.parseInt(str)-1]+"/"+str2);
            i++;
        }
        BarDataSet dataset = new BarDataSet(entries, "");
        BarData data = new BarData(labels, dataset);
        //data.setValueTextSize(100f);
        lineChart.getXAxis().setTextColor(Color.parseColor("#DC143C"));
        lineChart.getXAxis().setTextSize(4f);
        data.setValueTextColor(Color.parseColor("#66000000"));
        data.setValueTextSize(7f);
        data.setValueTextColor(Color.parseColor("#DC143C"));
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        //dataset.setDrawCubic(true);
        //dataset.setDrawFilled(false);
        lineChart.setPinchZoom(false);
        //dataset.setLineWidth(0f);
        lineChart.setScaleMinima((float) data.getXValCount() / 10f, 1f);
        lineChart.setData(data);
        lineChart.animateY(1000);
        lineChart.setDescription("");
        //lineChart.getAxisLeft().setDrawLabels(false);
        //lineChart.getAxisRight().setDrawLabels(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderColor(Color.parseColor("#1e90ff"));
        lineChart.getLegend().setEnabled(false);
        //lineChart.getXAxis().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);
        //dataset.setFillColor(Color.parseColor("#9400D3"));
        //dataset.setFillAlpha(100);
        lineChart.setDoubleTapToZoomEnabled(false);
        //dataset.setCircleColor(Color.parseColor("#9400D3"));
        //lineChart.getData().setHighlightEnabled(false);
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);




        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DATABASE", getActivity().MODE_PRIVATE);
        String rupee = getResources().getString(R.string.Rs);
        Cursor cursor_yo = sqLiteDatabase.rawQuery("SELECT SUM(amount) FROM student_other_p_view", null);
        if (cursor_yo.moveToFirst()) {
            String rupee1 = getResources().getString(R.string.Rs);
            long  amount_yo = cursor_yo.getLong(0);
            TextView t = (TextView) getActivity().findViewById(R.id.textView10);
            Locale defaultLocale = Locale.getDefault();
            Currency currency = Currency.getInstance(defaultLocale);
            t.setText("Total Expenditure :  "+(sharedPreferences.getInt("flag_value", 1)==1 ? rupee : currency.getSymbol() )+""+String.valueOf(amount_yo));
        }


        Cursor cursor2=sqLiteDatabase.rawQuery( "SELECT  strftime('%d-%m',dateyo) as date, sum(amount) as total1 FROM student_other_p_view  GROUP BY date order by date DESC limit 8 ",null);
        int j=0;
        BarChart lineChart1 = (BarChart) getActivity().findViewById(R.id.chartyear60);
        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<String> labels1 = new ArrayList<String>();
        while(cursor2.moveToNext()) {
            float data2_n = cursor2.getFloat(1);
            entries1.add(new BarEntry(data2_n, j));
            String data1=cursor2.getString(0);
            String str=data1.substring(0,2);
            String str1=data1.substring(3,5);
            //Toast.makeText(getContext(), ""+data1.substring(0,5), Toast.LENGTH_SHORT).show();
            // String str2=data1.substring(2,4);
            String month_string[]={"Jan", "FEB", "MAR","APR","MAY", "JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
            labels1.add(str+"/"+month_string[Integer.parseInt(str1)-1]);
            j++;
        }
        BarDataSet dataset1 = new BarDataSet(entries1, "");
        BarData data1 = new BarData(labels1, dataset1);
        //data.setValueTextSize(100f);
        lineChart1.getXAxis().setTextColor(Color.parseColor("#DC143C"));
        lineChart1.getXAxis().setTextSize(4f);
        data1.setValueTextColor(Color.parseColor("#66000000"));
        data1.setValueTextSize(7f);
        data1.setValueTextColor(Color.parseColor("#DC143C"));
        dataset1.setColors(ColorTemplate.COLORFUL_COLORS); //
        //dataset.setDrawCubic(true);
        //dataset.setDrawFilled(false);
        lineChart1.setPinchZoom(false);
        //dataset.setLineWidth(0f);
        lineChart1.setScaleMinima((float) data1.getXValCount() / 10f, 1f);
        lineChart1.setData(data1);
        lineChart1.animateY(1000);
        lineChart1.setDescription("");
        //lineChart.getAxisLeft().setDrawLabels(false);
        //lineChart.getAxisRight().setDrawLabels(false);
        lineChart1.setDrawBorders(true);
        lineChart1.setBorderColor(Color.parseColor("#1e90ff"));
        lineChart1.getLegend().setEnabled(false);
        //lineChart.getXAxis().setEnabled(false);
        lineChart1.getAxisLeft().setEnabled(false);
        lineChart1.getAxisRight().setEnabled(false);
        //dataset.setFillColor(Color.parseColor("#9400D3"));
        //dataset.setFillAlpha(100);
        lineChart1.setDoubleTapToZoomEnabled(false);
        //dataset.setCircleColor(Color.parseColor("#9400D3"));
        //lineChart.getData().setHighlightEnabled(false);
        // TODO Auto-generated method stub



        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }
}