package com.magic.akash.magicbox;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class star_fooding extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    ArrayList arrayList=new ArrayList();
    ArrayList<DataModel_star> dataModels;
    ListView listView;
    Context c=this;
    private static CustomAdapter_star adapter;
    private static final String[]symbol = {"","₹","₱","£","kr","$","$","Дин.","RM","Rf."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star);
        ImageView iv= (ImageView) findViewById(R.id.imageView2);
        assert iv != null;
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences sharedPreferences =getSharedPreferences("DATABASE", MODE_PRIVATE);

        sqLiteDatabase = openOrCreateDatabase("OLBE_DEMO", android.content.Context.MODE_PRIVATE, null);//create database
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_demo_p_view_star(id INTEGER PRIMARY KEY AUTOINCREMENT,amount ,place VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table

        listView=(ListView) findViewById(R.id.list);
        dataModels= new ArrayList<>();

        final String[] data = new String[1];
        final String[] data1 = new String[1];
        final String[] data2 = new String[1];
        final String[] data3 = new String[1];
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM student_demo_p_view_star",null);
        while(cursor.moveToNext()) {
            data[0] = cursor.getString(1);
            data1[0] = cursor.getString(2);
            data2[0] = cursor.getString(0);
            data3[0] = cursor.getString(3);
            String rupee = getResources().getString(R.string.Rs);

            Locale defaultLocale = Locale.getDefault();
            Currency currency = Currency.getInstance(defaultLocale);
            dataModels.add(new DataModel_star( symbol[sharedPreferences.getInt("flag_value", 0)],data[0], data1[0],data3[0]));

        }

        adapter= new CustomAdapter_star(dataModels,getApplicationContext());
        listView.setAdapter(adapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete?");
                builder.setIcon(R.drawable.del);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {
                        Cursor cursor2=sqLiteDatabase.rawQuery("SELECT * FROM student_demo_p_view_star;", null);
                        dataModels.remove(arg2);
                        listView.setAdapter(adapter);
                        cursor2.moveToPosition(arg2);
                        final int id= cursor2.getInt(cursor2.getColumnIndex("id"));
                        sqLiteDatabase.delete("student_demo_p_view_star", "id=?", new String[] {Integer.toString(id)});
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int ii) {
                                dialog.dismiss();
                            }
                        }
                );
                builder.show();
                return true ;
            }
        });


    }
}
