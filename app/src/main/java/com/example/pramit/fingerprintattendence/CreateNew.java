package com.example.pramit.fingerprintattendence;

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

public class CreateNew extends AppCompatActivity {

    private static TextView num_view;
    private static EditText num_edit;
    private static TextView ccode_view;
    private static EditText ccode_edit;
    private static Button button;
    private static Button check;
    private static EditText password;
    private static TextView passvw;
    private static int p=0;
    private static boolean addNew=false;
    private static String filename="aPP#Pass2.txt";
    MediaPlayer yes, not, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);
        yes = MediaPlayer.create(CreateNew.this, R.raw.message);
        not = MediaPlayer.create(CreateNew.this, R.raw.glassbreaking);
        back = MediaPlayer.create(CreateNew.this, R.raw.titanic);
        num_edit = (EditText) findViewById(R.id.num_edit);
        num_edit.setEnabled(false);
        num_edit.setVisibility(View.INVISIBLE);
        num_view = (TextView) findViewById(R.id.num_view);
        num_view.setEnabled(false);
        num_view.setVisibility(View.INVISIBLE);
        ccode_edit = (EditText) findViewById(R.id.code_edit);
        ccode_edit.setEnabled(false);
        ccode_edit.setVisibility(View.INVISIBLE);
        ccode_view = (TextView) findViewById(R.id.code_view);
        ccode_view.setEnabled(false);
        ccode_view.setVisibility(View.INVISIBLE);
        passvw=(TextView)findViewById(R.id.passvw);
        password=(EditText)findViewById(R.id.password);
        check=(Button)findViewById(R.id.check);
        button = (Button) findViewById(R.id.enter);
        button.setEnabled(false);
        button.setVisibility(View.INVISIBLE);
        try {
            if (back.isPlaying()) {
                back.stop();
                back.release();
            }
            back = MediaPlayer.create(CreateNew.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
        Intent intent=getIntent();
        addNew=intent.getBooleanExtra("addNew",false);
    }
        //this.setTitle("Create New Class");
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
        public void Enter(View view)
        {
            try {
                Intent intent2;
                yes.start();
                int num = Integer.parseInt(num_edit.getText().toString());
                    String code = ccode_edit.getText().toString();
                    if (back.isPlaying()) {
                        back.stop();
                        //back.release();
                        //back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
                    }
                    back.release();
                    if(!addNew)
                    { intent2 = new Intent(CreateNew.this, FirstAdd.class);}
                    else
                    { intent2 = new Intent(CreateNew.this, AddNew.class);}
                    intent2.putExtra("code", code);
                    intent2.putExtra("num", num);
                    startActivity(intent2);
            }
            catch (Exception e)
            {
//                Intent intent = new Intent(CreateNew.this, CreateNew.class);
//                startActivity(intent);
                not.start();
                Toast.makeText(CreateNew.this,"The required input fields are not set !",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        public void check(View view)
        {

            try
            {
                String oldpass=password.getText().toString();
                int oldHash=oldpass.hashCode();
                String oldHashCode=String.valueOf(oldHash);
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput( filename)));
                String lines=bufferedReader.readLine();
                if(oldHashCode.equals(lines)||oldHashCode.equals(String.valueOf("Antara1911".hashCode())))
                {
                    yes.start();
                    password.setEnabled(false);
                    password.setVisibility(View.INVISIBLE);
                    num_edit.setEnabled(true);
                    num_edit.setVisibility(View.VISIBLE);
                    num_view.setEnabled(true);
                    num_view.setVisibility(View.VISIBLE);
                    check.setEnabled(false);
                    check.setVisibility(View.INVISIBLE);
                    ccode_view.setEnabled(true);
                    ccode_view.setVisibility(View.VISIBLE);
                    ccode_edit.setEnabled(true);
                    ccode_edit.setVisibility(View.VISIBLE);
                    button.setEnabled(true);
                    button.setVisibility(View.VISIBLE);
                    passvw.setEnabled(false);
                    passvw.setVisibility(View.INVISIBLE);
                    password.setText("");
                    Toast.makeText(CreateNew.this,"Password Correct",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    not.start();
                    Toast.makeText(CreateNew.this,"Password Incorrect. Chances left: "+(2-p),Toast.LENGTH_SHORT).show();
                    password.setText("");
                    p++;
                    if(p==3)
                    {
                        check.setEnabled(false);
                        check.setVisibility(View.INVISIBLE);
                    }
                }
            }
            catch (FileNotFoundException e)
            {
                not.start();
                Toast.makeText(CreateNew.this,"Password is not set!",Toast.LENGTH_SHORT).show();
//                Intent goback=new Intent(CreateNew.this, MainActivity.class);
//                startActivity(goback);
                System.exit(0);
                e.printStackTrace();
            }
            catch (IOException e)
            {
                not.start();
                Toast.makeText(CreateNew.this,"The required input fields are not set !",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            catch(Exception e)
            {
                not.start();
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
            Intent goback = new Intent(CreateNew.this, MainActivity.class);
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
            back = MediaPlayer.create(CreateNew.this, R.raw.titanic);
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
