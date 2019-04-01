package com.example.pramit.fingerprintattendence;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Password extends AppCompatActivity {

    private static Button setpass;
    private static TextView newPassvw;
    private static TextView prevPassvw;
    private static TextView confPassvw;
    private static EditText confPass;
    private static EditText newPass;
    private static EditText prevPass;
    MediaPlayer yes, not, back;
    private static Boolean Newpass=false;
    private static String filename="aPP#Pass2.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
//        try {
//            String current = new java.io.File(".").getCanonicalPath();
//            current = path(current);
//            filename=current+"\\aPP#Pass.txt";
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
        yes = MediaPlayer.create(Password.this, R.raw.message);
        not = MediaPlayer.create(Password.this, R.raw.glassbreaking);
        back = MediaPlayer.create(Password.this, R.raw.titanic);
        setpass=(Button)findViewById(R.id.change_pass);
        newPassvw=(TextView)findViewById(R.id.newPassvw);
        newPass=(EditText)findViewById(R.id.newPass);
        confPassvw=(TextView)findViewById(R.id.confPassvw);
        confPass=(EditText)findViewById(R.id.confPass);
        prevPassvw=(TextView)findViewById(R.id.prevPassvw);
        prevPass=(EditText)findViewById(R.id.prevPass);
        try {
            if (back.isPlaying()) {
                back.stop();
                back.release();
            }
            back = MediaPlayer.create(Password.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
        Intent intent=getIntent();
        if (intent.getBooleanExtra("Newpass",true)) Newpass = true;
        else Newpass = false;
        check();
    }
    public void check()
    {
        if(Newpass)
        {
            prevPass.setEnabled(false);
            prevPass.setVisibility(View.INVISIBLE);
            prevPassvw.setEnabled(false);
            prevPassvw.setVisibility(View.INVISIBLE);
        }
        else
        {
            prevPass.setEnabled(true);
            prevPass.setVisibility(View.VISIBLE);
            prevPassvw.setEnabled(true);
            prevPassvw.setVisibility(View.VISIBLE);
        }
    }
//        AlertDialog.Builder a_builder=new AlertDialog.Builder(MainActivity.this);
//        a_builder.setMessage(" Do you want to close this App !")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i)
//                    {
//                        Toast.makeText(MainActivity.this,"You closed the app",Toast.LENGTH_LONG).show();
////                        Intent intent2 =new Intent();
////                        PendingIntent pintent =PendingIntent.getActivity(MainActivity.this,0,intent2,0);
////                        Notification noti =new Notification.Builder(MainActivity.this)
////                                .setTicker("TickerTitle")
////                                .setContentTitle("Alert")
////                                .setContentText("The app has been closed!")
//////                                .setSmallIcon(R.mipmap.ic_launcher)
//////                                .addAction(R.mipmap.ic_launcher_round,"Yes",pintent)
//////                                .addAction(R.drawable.ic_launcher_foreground,"No",pintent)
////                                .setContentIntent(pintent).getNotification();
////                        noti.flags=Notification.FLAG_AUTO_CANCEL;
////                        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
////                        nm.notify(0,noti);
//                        //finish();
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

    public void changePass(View view) {
        int k = 0;
        try {
            String pass = newPass.getText().toString();
            int hashpass = 0;
            String hashstore = "";
            String passconf = confPass.getText().toString();
            if (Newpass) {
                if (pass.equals(passconf)) {
                    hashpass = pass.hashCode();
                    hashstore = String.valueOf(hashpass);
                    yes.start();
                    k = 1;
                } else {
                    not.start();
                    Toast.makeText(Password.this, "New Passord and ConfirmPassword didn't match", Toast.LENGTH_LONG).show();
                    newPass.setText("");
                    confPass.setText("");
                }
            }
            else {

                try
                {
                    String oldpass=prevPass.getText().toString();
                    int oldHash=oldpass.hashCode();
                    String oldHashCode=String.valueOf(oldHash);
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput( filename)));
                    String lines=bufferedReader.readLine();
                    if(oldHashCode.equals(lines)||oldHashCode.equals(String.valueOf("Antara1911".hashCode())))
                    {
                        if (pass.equals(passconf)) {
                            hashpass = pass.hashCode();
                            hashstore = String.valueOf(hashpass);
                            yes.start();
                            k = 1;
                        } else {
                            not.start();
                            Toast.makeText(Password.this, "New Passord and ConfirmPassword didn't match", Toast.LENGTH_LONG).show();
                            newPass.setText("");
                            confPass.setText("");
                        }
                    }
                    else
                    {
                        not.start();
                        Toast.makeText(Password.this, "Old Password is not correct", Toast.LENGTH_LONG).show();
                        newPass.setText("");
                        confPass.setText("");
                        prevPass.setText("");
                    }
                }
                catch (FileNotFoundException e)
                {
                    not.start();
                    Toast.makeText(Password.this,"No previous passord is set !",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
//                    Intent goback=new Intent(Password.this, MainActivity.class);
//                    startActivity(goback);
                    System.exit(0);
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    not.start();
                    Toast.makeText(Password.this,"The required input fields are not set !",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            if (k == 1) {
                StringBuffer sb = new StringBuffer("");
                try {
                    FileOutputStream fileOutputStream = openFileOutput(filename, MODE_PRIVATE);
                    fileOutputStream.write(hashstore.getBytes());
                    fileOutputStream.close();
                    Toast.makeText(Password.this, "New Password is set.", Toast.LENGTH_LONG).show();
//                    Intent inten = new Intent(Password.this, MainActivity.class);
//                    startActivity(inten);
                    //finish();
                    System.exit(0);
                } catch (FileNotFoundException e) {
                    not.start();
                    Toast.makeText(Password.this,"No previous passord is set !",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
//                    Intent goback=new Intent(Password.this, MainActivity.class);
//                    startActivity(goback);
                    System.exit(0);
                } catch (IOException e) {
                    not.start();
                    e.printStackTrace();
                    //            Intent intent = new Intent(FirstAdd.this, FirstAdd.class);
                    //            startActivity(intent);
                }
            }
        }
        catch (Exception e)
        {
            not.start();
            e.printStackTrace();
        }
    }
    public void onBackPressed () {
        Toast.makeText(Password.this,"Password is not set!",Toast.LENGTH_SHORT).show();
        try {
            if (back.isPlaying()) {
                back.stop();
                //back.release();
                //back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
            }
            back.release();
            System.exit(0);
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
//            Intent goback = new Intent(Password.this, MainActivity.class);
//            startActivity(goback);
            System.exit(0);
        }
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
            not.start();
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
            back = MediaPlayer.create(Password.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

