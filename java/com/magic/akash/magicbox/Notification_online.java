package com.magic.akash.magicbox;


import android.*;
import android.Manifest;
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
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Notification_online extends BroadcastReceiver {


    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    SQLiteDatabase sqLiteDatabase;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        sqLiteDatabase = context.openOrCreateDatabase("OLBE_DEMO", context.MODE_PRIVATE, null);//create database
        try {
            String rupee = context.getString(R.string.Rs);
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    // Show Alert



                    //freecharge
                    int p=0,q=0;
                    //split message
                    String[] splited = message.split("\\s+");
                    for(int j=0;j<splited.length;j++){
                        //System.out.println(splited[j]);
                        if (splited[j].equals("Recharge")) {
                            p++;
                        }
                        if (splited[j].equals("successful!")) {
                            q++;
                        }
                    }
                    String msgData="";
                    if (p + q >= 2) {
                        for (int k = 0; k < splited.length; k++) {
                            if (splited[k].equals("Rs")) {
                                msgData = splited[k + 1];
                                //Toast.makeText(context, ""+msgData, Toast.LENGTH_SHORT).show();
                                //putting data
                                SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                SimpleDateFormat timeF = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                String date = dateF.format(Calendar.getInstance().getTime());
                                String time = timeF.format(Calendar.getInstance().getTime());
                                //making database, table

                                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_recharge_p_view(id INTEGER PRIMARY KEY AUTOINCREMENT,amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
                                sqLiteDatabase.execSQL("INSERT INTO student_recharge_p_view(id,amount,dateyo,timeyo) VALUES(null,'" + msgData + "','" + date + "','" + time + "')");//insert data fetch through edit text
//making database, table

                                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS student_recharge_p(amount VARCHAR,dateyo VARCHAR,timeyo VARCHAR)");//create table
                                sqLiteDatabase.execSQL("INSERT INTO student_recharge_p(amount,dateyo,timeyo) VALUES('" + msgData + "','" + date + "','" + time + "')");//insert data fetch through edit text
                                Toast.makeText(context, rupee+""+ msgData +" saved in your Magic Account", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }

}
    //For checking permission
