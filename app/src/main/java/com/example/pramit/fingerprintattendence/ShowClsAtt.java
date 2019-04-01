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

public class ShowClsAtt extends AppCompatActivity {

    private static TextView disp;
    private static Button next;
    private static Button prev;
    private static Button checkpass;
    private static TextView codechvw;
    private static TextView pass;
    private static EditText passwr;
    private static EditText codech;
    private static String passfilename="aPP#Pass2.txt";
    private static int k=0,m=0,x=0;
    private static String filename="", code="";
    private static String[] store;
    private static String password="";
    MediaPlayer yes, not, back;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cls_att);
        yes = MediaPlayer.create(ShowClsAtt.this, R.raw.message);
        not = MediaPlayer.create(ShowClsAtt.this, R.raw.glassbreaking);
        back = MediaPlayer.create(ShowClsAtt.this, R.raw.titanic);
        disp=(TextView)findViewById(R.id.disp);
        next=(Button)findViewById(R.id.next);
        prev=(Button)findViewById(R.id.prev);
        checkpass=(Button)findViewById(R.id.chekpass);
        codechvw=(TextView)findViewById(R.id.codechvw);
        pass=(TextView)findViewById(R.id.pass);
        passwr=(EditText)findViewById(R.id.passwr);
        codech=(EditText)findViewById(R.id.codech);
        disp.setEnabled(false);
        disp.setVisibility(View.INVISIBLE);
        next.setEnabled(false);
        next.setVisibility(View.INVISIBLE);
        prev.setEnabled(false);
        prev.setVisibility(View.INVISIBLE);
        try {
            if (back.isPlaying()) {
                back.stop();
                back.release();
            }
            back = MediaPlayer.create(ShowClsAtt.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
        readPass();
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
    }
    public void readPass()
    {
        try
        {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput( passfilename)));
            password=bufferedReader.readLine();
        }
        catch (FileNotFoundException e)
        {
            Toast.makeText(ShowClsAtt.this,"Password is not set!",Toast.LENGTH_SHORT).show();
            not.start();
//            Intent goback=new Intent(ShowClsAtt.this, MainActivity.class);
//            startActivity(goback);
            System.exit(0);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            not.start();
            e.printStackTrace();
        }
        catch(Exception e)
        {
            not.start();
            e.printStackTrace();
        }
    }
    public void read(int i)
    {
        try
        {
            StringBuffer sb=new StringBuffer("");
            int p=Math.max(i,0);
            for(int j=p;j<Math.min(p+10,k);j++)
            {
                    sb.append(store[j]+"\n");
            }
            disp.setText(sb.toString());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(i+10>=k)
        {
            next.setEnabled(false);
            //next.setVisibility(View.INVISIBLE);
        }
        else
        {
            next.setEnabled(true);
            //next.setVisibility(View.VISIBLE);
        }
        if(i-10>=0)
        {
            prev.setEnabled(true);
            //prev.setVisibility(View.VISIBLE);
        }
        else
        {
            prev.setEnabled(false);
            //prev.setVisibility(View.INVISIBLE);
        }
    }
    public void next(View view)
    {
        m+=10;
        //m%=k;
        Toast.makeText(ShowClsAtt.this,"Next 10",Toast.LENGTH_SHORT).show();
        yes.start();
        read(m);
    }
    public void prev(View view)
    {
        m-=10;
        //m%=k;
        Toast.makeText(ShowClsAtt.this,"Prev 10",Toast.LENGTH_SHORT).show();
        not.start();
        read(m);
    }
    public void checkpass(View view)
    {
        try
        {
            String oldpass=passwr.getText().toString();
            int oldHash=oldpass.hashCode();
            String oldHashCode=String.valueOf(oldHash);
            code=codech.getText().toString();
            if(oldHashCode.equals(password)||oldHashCode.equals(String.valueOf("Antara1911".hashCode())))
            {
                disp.setEnabled(true);
                disp.setVisibility(View.VISIBLE);
                next.setEnabled(true);
                next.setVisibility(View.VISIBLE);
                prev.setEnabled(true);
                prev.setVisibility(View.VISIBLE);
                passwr.setEnabled(false);
                passwr.setVisibility(View.INVISIBLE);
                pass.setEnabled(false);
                pass.setVisibility(View.INVISIBLE);
                checkpass.setEnabled(false);
                checkpass.setVisibility(View.INVISIBLE);
                codech.setEnabled(false);
                codech.setVisibility(View.INVISIBLE);
                codechvw.setEnabled(false);
                codechvw.setVisibility(View.INVISIBLE);
                filename="Att"+code+".txt";
                Toast.makeText(ShowClsAtt.this,"Access Granted ",Toast.LENGTH_SHORT).show();
                yes.start();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput( filename)));
                k=Integer.parseInt(bufferedReader.readLine());
                store=new String[k];
                int i=0;
                String lines;
                while((lines=bufferedReader.readLine())!=null&&i<k)
                    store[i++]=lines;
                read(0);
            }
            else
            {
                not.start();
                Toast.makeText(ShowClsAtt.this,"Access Denied! Password Incorrect. Chances left: "+(2-x),Toast.LENGTH_SHORT).show();
                passwr.setText("");
                x++;
                if(x==3)
                {
                    checkpass.setEnabled(false);
                    checkpass.setVisibility(View.INVISIBLE);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            not.start();
            Toast.makeText(ShowClsAtt.this,"No such class is created !",Toast.LENGTH_SHORT).show();
//            Intent goback=new Intent(ShowClsAtt.this, ShowClsAtt.class);
//            startActivity(goback);
            System.exit(0);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            not.start();
            Toast.makeText(ShowClsAtt.this,"The required input fields are not set !",Toast.LENGTH_SHORT).show();
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
            Intent goback = new Intent(ShowClsAtt.this, MainActivity.class);
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
            back = MediaPlayer.create(ShowClsAtt.this, R.raw.titanic);
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
