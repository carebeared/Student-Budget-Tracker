package com.magic.akash.magicbox;

import android.*;
import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;


//Our class extending fragment
public class Tab_borrow  extends Fragment{
    SQLiteDatabase sqLiteDatabase;
    ArrayList arrayList=new ArrayList();
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    //Override method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tab_borrow, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("DATABASE", getActivity().MODE_PRIVATE);
        sqLiteDatabase = getActivity().openOrCreateDatabase("OLBE_DEMO", android.content.Context.MODE_PRIVATE, null);//create database
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_borrow_pp(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,name VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table

        listView=(ListView) getActivity().findViewById(R.id.list);
        dataModels= new ArrayList<>();
        final String[]symbol = {"","₹","₱","£","kr","$","$","Дин.","RM","Rf."};
        final String[] data = new String[1];
        final String[] data1 = new String[1];
        final String[] data2 = new String[1];
        final String[] data3 = new String[1];
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM student_borrow_pp",null);
        while(cursor.moveToNext()) {
             data[0] = cursor.getString(1);
             data1[0] = cursor.getString(2);
             data2[0] = cursor.getString(0);
             data3[0] = cursor.getString(3);
            String rupee = getResources().getString(R.string.Rs);

            Locale defaultLocale = Locale.getDefault();
            Currency currency = Currency.getInstance(defaultLocale);
            dataModels.add(new DataModel( symbol[sharedPreferences.getInt("flag_value", 0)]+""+data[0], data1[0],data3[0]));
        }


        adapter= new CustomAdapter(dataModels,getContext());
        listView.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.lenn211);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/*");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), b, "Title", null);
                Uri imageUri =  Uri.parse(path);
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.putExtra(Intent.EXTRA_TEXT,"powered by- https://play.google.com/store/apps/details?id="+getActivity().getApplicationContext().getPackageName() );
                startActivity(Intent.createChooser(share, "Select"));
                Toast.makeText(getActivity(), "Text them to trust you.", Toast.LENGTH_SHORT).show();
            }
        });






































        super.onActivityCreated(savedInstanceState);




        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> arg0, final View arg1, final int arg2, final long arg3) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete?");
                builder.setIcon(R.drawable.del);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ii) {
                      Cursor cursor2=sqLiteDatabase.rawQuery("SELECT * FROM student_borrow_pp;", null);
                        dataModels.remove(arg2);
                        listView.setAdapter(adapter);
                        cursor2.moveToPosition(arg2);
                        final int id= cursor2.getInt(cursor2.getColumnIndex("id"));
                        sqLiteDatabase.delete("student_borrow_pp", "id=?", new String[] {Integer.toString(id)});
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                final View mView = layoutInflaterAndroid.inflate(R.layout.dialog_update, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                alertDialogBuilderUserInput.setView(mView);
                final AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                Button btn= (Button) mView.findViewById(R.id.button3);

                Cursor cursor3=sqLiteDatabase.rawQuery("SELECT * FROM student_borrow_pp;", null);
                cursor3.moveToPosition(arg2);
                final int id= cursor3.getInt(cursor3.getColumnIndex("id"));

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText ed = (EditText) mView.findViewById(R.id.userInputDialog);
                        if (ed.getText().length() <= 8) {
                            ContentValues cv = new ContentValues();
                            cv.put("amount", ed.getText().toString()); //These Fields should be your String values of actual column names
                            sqLiteDatabase.update("student_borrow_pp", cv, "id=" + id, null);
                            dataModels.clear();
                            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM student_borrow_pp", null);
                            while (cursor.moveToNext()) {
                                data[0] = cursor.getString(1);
                                data1[0] = cursor.getString(2);
                                data2[0] = cursor.getString(0);
                                data3[0] = cursor.getString(3);
                                String rupee = getResources().getString(R.string.Rs);
                                dataModels.add(new DataModel(rupee + "" + data[0], data1[0], data3[0]));
                            }
                            listView.setAdapter(adapter);
                            alertDialogAndroid.dismiss();
                        }
                        else{
                            Toast.makeText(getContext(), "Enter valid amount", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                alertDialogAndroid.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alertDialogAndroid.show();
                alertDialogAndroid.setCanceledOnTouchOutside(true);
            }
        });



    }

}