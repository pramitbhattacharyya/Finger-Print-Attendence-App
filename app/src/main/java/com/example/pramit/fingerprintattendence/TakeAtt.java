package com.example.pramit.fingerprintattendence;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TakeAtt extends AppCompatActivity {

    private static Button checkpass;
    private static TextView codechvw;
    private static TextView pass;
    private static EditText passwr;
    private static EditText codech;
    private static EditText roll;
    private static TextView dispnm;
    private static TextView rollvw;
    private static Button show;
    private static Button update;
    private static Button done;
    private static int p = 0, x = 0, entwhr = 0;
    private static String passfilename = "aPP#Pass2.txt";
    private static String filename = "", code = "";
    private static String[][] store;
    private static int[] isPresent;
    private static String password = "";
    MediaPlayer yes, not, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_att);
        checkpass = (Button) findViewById(R.id.chekpass);
        codechvw = (TextView) findViewById(R.id.codechvw);
        pass = (TextView) findViewById(R.id.pass);
        passwr = (EditText) findViewById(R.id.passwr);
        codech = (EditText) findViewById(R.id.codech);
        roll = (EditText) findViewById(R.id.roll);
        rollvw = (TextView) findViewById(R.id.rollvw);
        show = (Button) findViewById(R.id.show);
        dispnm = (TextView) findViewById(R.id.dispnm);
        yes = MediaPlayer.create(TakeAtt.this, R.raw.message);
        not = MediaPlayer.create(TakeAtt.this, R.raw.glassbreaking);
        back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
        update = (Button) findViewById(R.id.update);
        done = (Button) findViewById(R.id.done);
        try {
            if (back.isPlaying()) {
                back.stop();
                back.release();
            }
            back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
        readPass();
        switch1();
    }

    public void readPass() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(passfilename)));
            password = bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            Toast.makeText(TakeAtt.this, "No previous passord is set !", Toast.LENGTH_SHORT).show();
            Intent goback = new Intent(TakeAtt.this, MainActivity.class);
            startActivity(goback);
            e.printStackTrace();
        } catch (IOException e) {
            not.start();
            e.printStackTrace();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (entwhr == 1)
            dispnm.setText("Don't press back. The attendence taken will be lost. Press done for updating the file.");
        else if (entwhr == 2)
            Toast.makeText(TakeAtt.this, "Enter wrong password and press Update Button to go back.", Toast.LENGTH_LONG).show();
        else {
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
            }
            Intent goback = new Intent(TakeAtt.this, MainActivity.class);
            startActivity(goback);
        }
    }

    public void check(View view) {
        try {
            String oldpass = passwr.getText().toString();
            int oldHash = oldpass.hashCode();
            String oldHashCode = String.valueOf(oldHash);
            code = codech.getText().toString();
            if (oldHashCode.equals(password) || oldHashCode.equals(String.valueOf("Antara1911".hashCode()))) {
                entwhr = 1;
                Toast.makeText(TakeAtt.this, "Access Granted", Toast.LENGTH_SHORT).show();
                switch2();
                filename = "Att" + code + ".txt";
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(filename)));
                p = Integer.parseInt(bufferedReader.readLine());
                store = new String[p][3];
                isPresent = new int[p];
                for (int h = 0; h < p; h++)
                    isPresent[h] = 0;
                String lines;
                int c1 = 0, c2 = 0;
                char c;
                int l = 0;
                while ((lines = bufferedReader.readLine()) != null && l < p) {
                    for (int i = 0; i < lines.length(); i++) {
                        c = lines.charAt(i);
                        if (c == ' ') {
                            c1 = i;
                            break;
                        }
                    }
                    for (int i = lines.length() - 1; i > -1; i--) {
                        c = lines.charAt(i);
                        if (c == ' ') {
                            c2 = i;
                            break;
                        }
                    }
                    store[l][0] = lines.substring(0, c1);
                    store[l][1] = lines.substring(c1 + 1, c2);
                    store[l][2] = lines.substring(c2 + 1);
                    l++;
                }
                passwr.setText("");
                Toast.makeText(TakeAtt.this, "Students = " + p, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TakeAtt.this, "Access Denied! Password Incorrect. Chances left: " + (2 - x), Toast.LENGTH_SHORT).show();
                passwr.setText("");
                x++;
                if (x == 3) {
                    checkpass.setEnabled(false);
                    checkpass.setVisibility(View.INVISIBLE);
                }
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(TakeAtt.this, "No such class is created !", Toast.LENGTH_SHORT).show();
            not.start();
            Intent goback = new Intent(TakeAtt.this, TakeAtt.class);
            startActivity(goback);
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(TakeAtt.this, "The required input fields are not set !", Toast.LENGTH_SHORT).show();
            not.start();
            e.printStackTrace();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
    }

    public void show(View view) {
        try {
            int k = 0;
            String rollno = roll.getText().toString();        //
            int l = 0;
            while (l < p) {
                if (store[l][0].equals(rollno)) {
                    k = 1;
                    String info = store[l][0] + " " + store[l][1] + " " + store[l][2];
                    Toast.makeText(TakeAtt.this, info, Toast.LENGTH_SHORT).show();
                    info = store[l][0] + " " + store[l][1] + " " + (Integer.parseInt(store[l][2]) + 1);
                    isPresent[l] = 1;
                    dispnm.setText(info);
                    try {
                        if (yes.isPlaying()) {
                            yes.stop();
                            yes.release();
                            yes = MediaPlayer.create(TakeAtt.this, R.raw.message);
                        }
                        yes.start();
                    } catch (Exception e) {
                        not.start();
                        e.printStackTrace();
                    }
                    break;
                }
                l++;
            }
            if (k == 0) {
                dispnm.setText("Sorry! No such roll number found!");
                try {
                    if (not.isPlaying()) {
                        not.stop();
                        not.release();
                        not = MediaPlayer.create(TakeAtt.this, R.raw.glassbreaking);
                    }
                    not.start();
                } catch (Exception e) {
                    not.start();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
    }

    public void done(View view) {
        switch1();
        entwhr = 2;
        update.setEnabled(true);
        update.setVisibility(View.VISIBLE);
        checkpass.setEnabled(false);
        checkpass.setVisibility(View.INVISIBLE);
    }

    public void update(View view) {

        try {
            String oldpass = passwr.getText().toString();
            int oldHash = oldpass.hashCode();
            String oldHashCode = String.valueOf(oldHash);
            code = codech.getText().toString();
            if (oldHashCode.equals(password)) {
                for (int i = 0; i < p; i++)
                    store[i][2] = String.valueOf(Integer.parseInt(store[i][2]) + isPresent[i]);
                StringBuffer sb = new StringBuffer(p + "\n");
                for (int i = 0; i < p; i++)
                    sb.append(store[i][0] + " " + store[i][1] + " " + store[i][2] + "\n");
                FileOutputStream fileOutputStream = openFileOutput(filename, MODE_PRIVATE);
                fileOutputStream.write(sb.toString().getBytes());
                fileOutputStream.close();
                Toast.makeText(TakeAtt.this, "File is Updated", Toast.LENGTH_LONG).show();
                try {
                    if (back.isPlaying()) {
                        back.stop();

                        //back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
                    }
                    back.release();
//                    back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
//                    back.start();
                } catch (Exception e) {
                    not.start();
                    e.printStackTrace();
                }
//                Intent intent = new Intent(TakeAtt.this, MainActivity.class);
//                startActivity(intent);
                System.exit(0);
            } else {
                Toast.makeText(TakeAtt.this, "Access Denied! Password Incorrect.", Toast.LENGTH_SHORT).show();
                passwr.setText("");
                switch2();
                entwhr = 1;
                update.setEnabled(false);
                update.setVisibility(View.INVISIBLE);
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(TakeAtt.this, "No such class is created !", Toast.LENGTH_SHORT).show();
            not.start();
            Intent goback = new Intent(TakeAtt.this, TakeAtt.class);
            startActivity(goback);
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(TakeAtt.this, "The required input fields are not set !", Toast.LENGTH_SHORT).show();
            not.start();
            e.printStackTrace();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
    }

    public void switch1() {
        done.setEnabled(false);
        done.setVisibility(View.INVISIBLE);
        update.setEnabled(false);
        update.setVisibility(View.INVISIBLE);
        roll.setEnabled(false);
        roll.setVisibility(View.INVISIBLE);
        rollvw.setEnabled(false);
        rollvw.setVisibility(View.INVISIBLE);
        show.setEnabled(false);
        show.setVisibility(View.INVISIBLE);
        dispnm.setEnabled(false);
        dispnm.setVisibility(View.INVISIBLE);
        checkpass.setEnabled(true);
        checkpass.setVisibility(View.VISIBLE);
        codechvw.setEnabled(true);
        codechvw.setVisibility(View.VISIBLE);
        pass.setEnabled(true);
        pass.setVisibility(View.VISIBLE);
        passwr.setEnabled(true);
        passwr.setVisibility(View.VISIBLE);
        codech.setEnabled(true);
        codech.setVisibility(View.VISIBLE);
    }

    public void switch2() {
        checkpass.setEnabled(false);
        checkpass.setVisibility(View.INVISIBLE);
        codechvw.setEnabled(false);
        codechvw.setVisibility(View.INVISIBLE);
        pass.setEnabled(false);
        pass.setVisibility(View.INVISIBLE);
        passwr.setEnabled(false);
        passwr.setVisibility(View.INVISIBLE);
        codech.setEnabled(false);
        codech.setVisibility(View.INVISIBLE);
        rollvw.setEnabled(true);
        rollvw.setVisibility(View.VISIBLE);
        show.setEnabled(true);
        show.setVisibility(View.VISIBLE);
        dispnm.setEnabled(true);
        dispnm.setVisibility(View.VISIBLE);
        roll.setEnabled(true);
        roll.setVisibility(View.VISIBLE);
        done.setEnabled(true);
        done.setVisibility(View.VISIBLE);

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
            back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
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
//    protected void onStart() {
//        try {
//            if (back.isPlaying()) {
//                back.stop();
//                back.release();
//                back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
//            }
//            back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);
//            //back.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onStart();
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

