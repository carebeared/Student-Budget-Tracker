package com.magic.akash.magicbox;


import android.*;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class notification2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("OLBE_DEMO", context.MODE_PRIVATE, null);
        long when = System.currentTimeMillis();
        NotificationManager notificationManager1 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotifyBuilder1 = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.qw).setContentTitle("Seems no Expenses Today!!").setContentText("click here to add.").setSound(alarmSound).setAutoCancel(true).setContentIntent(pendingIntent2);
        notificationManager1.notify(1001, mNotifyBuilder1.build());

        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + context.getPackageName() + "/databases/OLBE_DEMO";
                String backupDBPath = "magicbox_database.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);
                Toast.makeText(context, "backup creating....", Toast.LENGTH_SHORT).show();
                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    // Toast.makeText(context, "Bckup Created !", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context, "magicbox_database.db  in External Storage", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, "ERROR! backup not created , Have u given storage permission", Toast.LENGTH_SHORT).show();
        }




        //for food zero
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_demo_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_demo_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        float data_food = 0;
            Cursor cursor9 = sqLiteDatabase.rawQuery("SELECT  strftime('%d-%m',dateyo) as date, sum(amount) as total1 FROM student_demo_p_view  GROUP BY date order by date DESC limit 1 ", null);
            while (cursor9.moveToNext()) {
                data_food = cursor9.getFloat(1);
            }

            if (data_food == 0) {
                SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String date = dateF.format(Calendar.getInstance().getTime());
                String time = timeF.format(Calendar.getInstance().getTime());
//making table in database for view
                sqLiteDatabase.execSQL("INSERT INTO student_demo_p_view(id,amount,dateyo,timeyo) VALUES(null,'" + 0 + "','" + date + "','" + time + "')");
//making database, table
                sqLiteDatabase.execSQL("INSERT INTO student_demo_p(amount,dateyo,timeyo) VALUES('" + 0 + "','" + date + "','" + time + "')");//insert data fetch through edit text
            }







        //for rechargr zero
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_recharge_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_recharge_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        float data_re = 0;
        Cursor cursor10 = sqLiteDatabase.rawQuery("SELECT  strftime('%d-%m',dateyo) as date, sum(amount) as total1 FROM student_recharge_p_view  GROUP BY date order by date DESC limit 1 ", null);
        while (cursor10.moveToNext()) {
            data_re = cursor10.getFloat(1);
        }

        if (data_re == 0) {
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String date = dateF.format(Calendar.getInstance().getTime());
            String time = timeF.format(Calendar.getInstance().getTime());
//making table in database for view
            sqLiteDatabase.execSQL("INSERT INTO student_recharge_p_view(id,amount,dateyo,timeyo) VALUES(null,'" + 0 + "','" + date + "','" + time + "')");
//making database, table
            sqLiteDatabase.execSQL("INSERT INTO student_recharge_p(amount,dateyo,timeyo) VALUES('" + 0 + "','" + date + "','" + time + "')");//insert data fetch through edit text
        }






        //for shopping zero
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_shoping_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_shoping_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        float data_fo = 0;
        Cursor cursor11 = sqLiteDatabase.rawQuery("SELECT  strftime('%d-%m',dateyo) as date, sum(amount) as total1 FROM student_shoping_p_view  GROUP BY date order by date DESC limit 1 ", null);
        while (cursor11.moveToNext()) {
            data_fo = cursor11.getFloat(1);
        }

        if (data_fo == 0) {
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String date = dateF.format(Calendar.getInstance().getTime());
            String time = timeF.format(Calendar.getInstance().getTime());
//making table in database for view
            sqLiteDatabase.execSQL("INSERT INTO student_shoping_p_view(id,amount,dateyo,timeyo) VALUES(null,'" + 0 + "','" + date + "','" + time + "')");
//making database, table
            sqLiteDatabase.execSQL("INSERT INTO student_shoping_p(amount,dateyo,timeyo) VALUES('" + 0 + "','" + date + "','" + time + "')");//insert data fetch through edit text
        }



        //for transport zero
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_transport_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_transport_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        float data_tr = 0;
        Cursor cursor12 = sqLiteDatabase.rawQuery("SELECT  strftime('%d-%m',dateyo) as date, sum(amount) as total1 FROM student_transport_p_view  GROUP BY date order by date DESC limit 1 ", null);
        while (cursor12.moveToNext()) {
            data_tr = cursor12.getFloat(1);
        }

        if (data_tr == 0) {
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String date = dateF.format(Calendar.getInstance().getTime());
            String time = timeF.format(Calendar.getInstance().getTime());
//making table in database for view
            sqLiteDatabase.execSQL("INSERT INTO student_transport_p_view(id,amount,dateyo,timeyo) VALUES(null,'" + 0 + "','" + date + "','" + time + "')");
//making database, table
            sqLiteDatabase.execSQL("INSERT INTO student_transport_p(amount,dateyo,timeyo) VALUES('" + 0 + "','" + date + "','" + time + "')");//insert data fetch through edit text
        }




        //other zero
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_other_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_other_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
        float data_ot = 0;
        Cursor cursor13 = sqLiteDatabase.rawQuery("SELECT  strftime('%d-%m',dateyo) as date, sum(amount) as total1 FROM student_other_p_view  GROUP BY date order by date DESC limit 1 ", null);
        while (cursor13.moveToNext()) {
            data_ot = cursor13.getFloat(1);
        }

        if (data_ot == 0) {
            SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String date = dateF.format(Calendar.getInstance().getTime());
            String time = timeF.format(Calendar.getInstance().getTime());
//making table in database for view
            sqLiteDatabase.execSQL("INSERT INTO student_other_p_view(id,amount,dateyo,timeyo) VALUES(null,'" + 0 + "','" + date + "','" + time + "')");
//making database, table
            sqLiteDatabase.execSQL("INSERT INTO student_other_p(amount,dateyo,timeyo) VALUES('" + 0 + "','" + date + "','" + time + "')");//insert data fetch through edit text
        }


    }
}