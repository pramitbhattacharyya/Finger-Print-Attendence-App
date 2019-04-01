package com.example.pramit.fingerprintattendence;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static Button CreateNew;
    private static Button AddNew;
    private static Button ShowMyAttendence;
    private static Button ShowClassAttendence;
    private static Button takeAtt;
    private static Button changepass;
    private static String filename="aPP#Pass2.txt";
    MediaPlayer yes, not, back;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CreateNew = (Button) findViewById(R.id.CreateNew);
        AddNew = (Button) findViewById(R.id.AddNewStudent);
        ShowMyAttendence = (Button) findViewById(R.id.ShowMyAttendence);
        ShowClassAttendence = (Button) findViewById(R.id.ShowClassAttendence);
        takeAtt=(Button)findViewById(R.id.takeatt);
        changepass=(Button)findViewById(R.id.change_pass);
        yes = MediaPlayer.create(MainActivity.this, R.raw.message);
        not = MediaPlayer.create(MainActivity.this, R.raw.glassbreaking);
        back = MediaPlayer.create(MainActivity.this, R.raw.titanic);
//        deviceManger = (DevicePolicyManager) getSystemService(
//                Context.DEVICE_POLICY_SERVICE);
        try {
            if (back.isPlaying()) {
                back.stop();

            }
            back.release();
            back = MediaPlayer.create(MainActivity.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
        try
        {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput( filename)));
            String lines;
            lines=bufferedReader.readLine();
            if(lines.equals(""))
            {
                yes.start();
                Intent in = new Intent(MainActivity.this, Password.class);
                in.putExtra("Newpass", true);
                startActivity(in);
            }
        }
        catch (FileNotFoundException e)
        {
            yes.start();
            Toast.makeText(MainActivity.this,"Type a new Password !", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(MainActivity.this, Password.class);
            in.putExtra("Newpass", true);
            startActivity(in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        OnClickButtonListener();
    }
    public void OnClickButtonListener() {
        CreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent= new Intent(".CreateNew");
                Intent intent = new Intent(MainActivity.this, CreateNew.class);
                intent.putExtra("addNew", false);
                yes.start();
                cutoff();
                startActivity(intent);
            }
        });
        AddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNew.class);
                intent.putExtra("addNew", true);
                yes.start();
                cutoff();
                startActivity(intent);
            }
        });
        ShowMyAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowMyAtt.class);
                yes.start();
                cutoff();
                startActivity(intent);
            }
        });
        ShowClassAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowClsAtt.class);
                yes.start();
                cutoff();
                startActivity(intent);
            }
        });
        takeAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TakeAtt.class);
                yes.start();
                cutoff();
                startActivity(intent);
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Password.class);
                yes.start();
                intent.putExtra("Newpass",false);
                cutoff();
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder a_builder=new AlertDialog.Builder(MainActivity.this);
        a_builder.setMessage(" Do you want to close this App !")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Toast.makeText(MainActivity.this,"You closed the app",Toast.LENGTH_LONG).show();
//                        Intent intent2 =new Intent();
//                        PendingIntent pintent =PendingIntent.getActivity(MainActivity.this,0,intent2,0);
//                        Notification noti =new Notification.Builder(MainActivity.this)
//                                .setTicker("TickerTitle")
//                                .setContentTitle("Alert")
//                                .setContentText("The app has been closed!")
////                                .setSmallIcon(R.mipmap.ic_launcher)
////                                .addAction(R.mipmap.ic_launcher_round,"Yes",pintent)
////                                .addAction(R.drawable.ic_launcher_foreground,"No",pintent)
//                                .setContentIntent(pintent).getNotification();
//                        noti.flags=Notification.FLAG_AUTO_CANCEL;
//                        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                        nm.notify(0,noti);
  //                      finish();
//                        android.os.Process.killProcess(android.os.Process.myPid());
                        back.release();
                        System.exit(0);
//
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alert=a_builder.create();
        alert.setTitle("Confirm Exit !");
        alert.show();
    }

    @Override
    protected void onPause() {

        try {
//            if (back.isPlaying()) {
//                back.stop();
//                back.release();
//                back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
//            }
            // back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
            back.start();
        } catch (Exception e) {
            //not.start();
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        try {
            if (back.isPlaying()) {
                back.stop();
                back.release();
            }
            back = MediaPlayer.create(MainActivity.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            //not.start();
            e.printStackTrace();
        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public void cutoff()
    {
        try {
            if (back.isPlaying()) {
                back.stop();

            }
            back.release();
            //back = MediaPlayer.create(MainActivity.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            //not.start();
            e.printStackTrace();
        }
    }
}
//    @Override
//    public void onBackPressed() {
//        AlertDialog.Builder a_builder=new AlertDialog.Builder(MainActivity.this);
//        a_builder.setMessage(" Do you want to close this App !")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i)
//                    {
//                        Toast.makeText(MainActivity.this,"You closed the app",Toast.LENGTH_LONG).show();
//                        Intent intent =new Intent();
//                        PendingIntent pintent =PendingIntent.getActivity(MainActivity.this,0,intent,0);
//                        Notification noti =new Notification.Builder(MainActivity.this)
//                                .setTicker("TickerTitle")
//                                .setContentTitle("Goodbye See You!")
//                                .setContentText("Wanna go back ?")
//                                .setSmallIcon(R.mipmap.antara_p)
//                                .addAction(R.mipmap.antara_p_round,"Yes",pintent)
//                                .addAction(R.drawable.ic_launcher_foreground,"No",pintent)
//                                .setContentIntent(pintent).getNotification();
//                        noti.flags=Notification.FLAG_AUTO_CANCEL;
//                        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                        nm.notify(0,noti);
//                        finish();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i)
//                    {
//                        dialogInterface.cancel();
//                    }
//                });
//        AlertDialog alert=a_builder.create();
//        alert.setTitle("Confirm Exit !");
//        alert.show();
//    }

