package com.example.pramit.fingerprintattendence;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShowMyAtt extends AppCompatActivity {
    private static TextView num_view;
    private static EditText num_edit;
    private static TextView code_view;
    private static EditText code_edit;
    private static Button button;
    private static TextView read;
    String filename = "", roll = "";
    //DevicePolicyManager deviceManger;
    MediaPlayer yes, not, back;

    //    ActivityManager activityManager;
//    ComponentName compName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_att);
        num_edit = (EditText) findViewById(R.id.rn_edit);
        code_view = (TextView) findViewById(R.id.code_view);
        read = (TextView) findViewById(R.id.read);
        num_view = (TextView) findViewById(R.id.num_view);
        code_edit = (EditText) findViewById(R.id.code_edit);
        button = (Button) findViewById(R.id.enter);
        yes = MediaPlayer.create(ShowMyAtt.this, R.raw.message);
        not = MediaPlayer.create(ShowMyAtt.this, R.raw.glassbreaking);
        back = MediaPlayer.create(ShowMyAtt.this, R.raw.titanic);
//        deviceManger = (DevicePolicyManager) getSystemService(
//                Context.DEVICE_POLICY_SERVICE);
        try {
            if (back.isPlaying()) {
                back.stop();
                back.release();
            }
            back = MediaPlayer.create(ShowMyAtt.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
    }

    public void read(View view) {
        try {
            int k = 0;
            String roll = num_edit.getText().toString();
            String code = code_edit.getText().toString();
            filename = "Att" + code + ".txt";
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(filename)));
            StringBuffer sb = new StringBuffer("");
            String lines;
            char c;
            while ((lines = bufferedReader.readLine()) != null) {
                for (int i = 0; i < lines.length(); i++) {
                    c = lines.charAt(i);
                    if (c == ' ')
                        break;
                    sb.append(String.valueOf(c));
                }
                if (sb.toString().equals(roll)) {
                    k = 1;
                    read.setText(lines);
                    yes.start();
                    break;
                }
                sb.delete(0, sb.length());
            }
            if (k == 0) {
                read.setText("Sorry! No such roll number found!");
                not.start();
                Toast.makeText(ShowMyAtt.this, "Write proper roll number!", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(ShowMyAtt.this, "Write proper class name!", Toast.LENGTH_SHORT).show();
            not.start();
            read.setText("No such class exists!");
            e.printStackTrace();//
        } catch (IOException e) {
            not.start();
            Toast.makeText(ShowMyAtt.this, "The required input fields are not set !", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    public void onBackPressed () {

            try {
                if (back.isPlaying()) {
                    back.stop();
                    //back.release();
                    //back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
                }
                back.release();
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
                not.start();
            Intent goback = new Intent(ShowMyAtt.this, MainActivity.class);
            startActivity(goback);
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
            back = MediaPlayer.create(ShowMyAtt.this, R.raw.titanic);
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

//    @Override
//    protected void onPause() {
//        deviceManger.lockNow();
//        super.onPause();
//    }

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
//    public static int DisplayAttendence(String code)
//    {
//        int att=0;
//        try
//        {
//            Scanner sc=new Scanner(System.in);
//            Object obj=new String(sc.next()); // Entering fingerprint.
//            //Read the file with name "attcode" and take input in var att.
//            return att;
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            System.exit(0);
//        }
//        return -1;
//    }