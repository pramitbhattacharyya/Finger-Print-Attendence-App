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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddNew extends AppCompatActivity
{

    private static Button store;
    private static Button read_data;
    private static Button next;
    private static Button prev;
    private static EditText rn_ed;
    private static TextView rn_vw;
    private static EditText nm_ed;
    private static TextView nm_vw;
    private static EditText ia_ed;
    private static TextView ia_vw;
    private static TextView read;
    private static String[] pack;
    private static Object[][] arr;
    private static int k=0, num=0,p=0,wentback=0,m=0;
    MediaPlayer yes, not, back;
    public static String code="",filename="",text="null";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_add);
        next=(Button)findViewById(R.id.next);
        prev=(Button)findViewById(R.id.prev);
        rn_ed = (EditText) findViewById(R.id.rn_ed);
        rn_vw = (TextView) findViewById(R.id.rn_vw);
        nm_ed = (EditText) findViewById(R.id.nm_ed);
        nm_vw = (TextView) findViewById(R.id.nm_vw);
        ia_ed = (EditText) findViewById(R.id.ia_ed);
        ia_vw = (TextView) findViewById(R.id.ia__vw);
        store = (Button) findViewById(R.id.store);
        read_data=(Button)findViewById(R.id.readdata);
        next.setEnabled(false);
        next.setVisibility(View.INVISIBLE);
        prev.setEnabled(false);
        prev.setVisibility(View.INVISIBLE);
        read_data.setEnabled(false);
        read_data.setVisibility(View.INVISIBLE);
        read=(TextView)findViewById(R.id.read);
        read.setEnabled(false);
        read.setVisibility(View.INVISIBLE);
        yes = MediaPlayer.create(AddNew.this, R.raw.message);
        not = MediaPlayer.create(AddNew.this, R.raw.glassbreaking);
        back = MediaPlayer.create(AddNew.this, R.raw.titanic);
        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        filename="Att"+code+".txt";
        num = intent.getIntExtra("num", 50);
        try {
            if (back.isPlaying()) {
                back.stop();
                back.release();
            }
            back = MediaPlayer.create(AddNew.this, R.raw.titanic);
            //back.start();
        } catch (Exception e) {
            not.start();
            e.printStackTrace();
        }
        Change();
    }
    public void addValue(View view)
    {
        try
        {
            arr[k][2] = Integer.parseInt(ia_ed.getText().toString());
            arr[k][1] = nm_ed.getText().toString();
            arr[k][0] = rn_ed.getText().toString();
            int u=unique(arr[k][0]);
            if(u==-1) {
                yes.start();
                text = arr[k][0] + " " + arr[k][1] + " " + arr[k][2];
                Toast.makeText(AddNew.this, text, Toast.LENGTH_SHORT).show();
                k++;
                rn_ed.setText("");
                nm_ed.setText("");
                ia_ed.setText("");
                //k=(k+1)%num;
                if (k == p) {
                    store.setEnabled(false);
                    store.setVisibility(View.INVISIBLE);
                    rn_ed.setEnabled(false);
                    rn_ed.setVisibility(View.INVISIBLE);
                    rn_vw.setEnabled(false);
                    rn_vw.setVisibility(View.INVISIBLE);
                    ia_ed.setEnabled(false);
                    ia_ed.setVisibility(View.INVISIBLE);
                    ia_vw.setEnabled(false);
                    ia_vw.setVisibility(View.INVISIBLE);
                    nm_ed.setEnabled(false);
                    nm_ed.setVisibility(View.INVISIBLE);
                    nm_vw.setEnabled(false);
                    nm_vw.setVisibility(View.INVISIBLE);
                    write();
                    read_data.setVisibility(View.VISIBLE);
                    read_data.setEnabled(true);
                }
            }
            else
            {
                not.start();
                String s=arr[u][0]+" "+arr[u][1]+" "+arr[u][2];
                Toast.makeText(AddNew.this,"This roll no is already added ! "+s, Toast.LENGTH_LONG).show();
                rn_ed.setText("");
                nm_ed.setText("");
                ia_ed.setText("");
            }
        }
        catch(Exception e)
        {
            not.start();
            Toast.makeText(AddNew.this,"The required input fields are not filled.",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
//            Intent intent = new Intent(FirstAdd.this, FirstAdd.class);
//            startActivity(intent);
            //System.exit(0) ;
        }
    }
    public int unique(Object s)
    {
        for(int i=0;i<k;i++)
            if(arr[i][0].equals(s))
                return i;
        return -1;
    }
    public void write()
    {

        StringBuffer sb=new StringBuffer(p+"\n");
        for(int i=0;i<p;i++)
            sb.append(arr[i][0]+" "+arr[i][1]+" "+arr[i][2]+"\n");
        try {
            yes.start();
            FileOutputStream fileOutputStream= openFileOutput(filename,MODE_PRIVATE);//openFileOutput(filename,MODE_PRIVATE);
            fileOutputStream.write(sb.toString().getBytes());
            fileOutputStream.close();
            Toast.makeText(AddNew.this, "Class Code: " + code + " Strength added : "+ num, Toast.LENGTH_LONG).show();
        }
        catch (FileNotFoundException e)
        {
            not.start();
            Toast.makeText(AddNew.this,"No such class is created !",Toast.LENGTH_SHORT).show();
//            Intent goback=new Intent(AddNew.this, CreateNew.class);
//            startActivity(goback);
            System.exit(0);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            not.start();
            e.printStackTrace();

        }
    }
    public void read2(int i)
    {
        try
        {
            StringBuffer sb=new StringBuffer("");
            int p=Math.max(i,0);
            for(int j=p;j<Math.min(p+10,k);j++)
            {
                sb.append(pack[j]+"\n");
            }
            read.setText(sb.toString());
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
        Toast.makeText(AddNew.this,"Next 10",Toast.LENGTH_SHORT).show();
        yes.start();
        read2(m);
    }
    public void prev(View view)
    {
        m-=10;
        //m%=k;
        Toast.makeText(AddNew.this,"Prev 10",Toast.LENGTH_SHORT).show();
        not.start();
        read2(m);
    }
    public void read(View view)
    {

        read_data.setEnabled(false);
        read_data.setVisibility(View.INVISIBLE);
        read.setEnabled(true);
        read.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        prev.setVisibility(View.VISIBLE);
        try
        {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput( filename)));
            k=Integer.parseInt(bufferedReader.readLine());
            pack=new String[k];
            int i=0;
            String lines;
            while((lines=bufferedReader.readLine())!=null&&i<k)
                pack[i++]=lines;
            yes.start();
            read2(0);
            wentback=1;
        }
        catch (FileNotFoundException e)
        {
            not.start();
            Toast.makeText(AddNew.this,"No such class is created !",Toast.LENGTH_SHORT).show();
//            Intent goback=new Intent(AddNew.this, CreateNew.class);
//            startActivity(goback);
            System.exit(0);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            not.start();
            e.printStackTrace();
        }
    }
    public void Change()
    {
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput( filename)));
            p = Integer.parseInt(bufferedReader.readLine());
            p += num;
            arr = new Object[p][3];
            String lines;
            char c;
            int c1=0,c2=0;
            while((lines=bufferedReader.readLine())!=null &&k<p)
            {
                for(int j=0;j<lines.length();j++)
                {
                    c=lines.charAt(j);
                    if(c==' ')
                    {
                        c1=j;
                        break;
                    }
                }
                for(int j=lines.length()-1;j>-1;j--)
                {
                    c=lines.charAt(j);
                    if(c==' ')
                    {
                        c2=j;
                        break;
                    }
                }
                arr[k][0]=lines.substring(0,c1);
                arr[k][1]=lines.substring(c1+1,c2);
                arr[k][2]=lines.substring(c2+1);
                k++;
            }
        }
        catch (FileNotFoundException e)
        {
            not.start();
            Toast.makeText(AddNew.this,"No such class is created ! First create a class.",Toast.LENGTH_LONG).show();
//            Intent goback=new Intent(AddNew.this, MainActivity.class);
//            startActivity(goback);
            System.exit(0);
            e.printStackTrace();
        }
        catch (IOException e) {
            not.start();
            e.printStackTrace();
        }
    }
    public void onBackPressed () {
        try {
            if (back.isPlaying()) {
                back.stop();
            }
                if(wentback==1)
                    System.exit(0);
                else
                    Toast.makeText(AddNew.this,"The given entries are still not saved.",Toast.LENGTH_LONG).show();
                //back.release();
                //back = MediaPlayer.create(TakeAtt.this, R.raw.titanic);

            //back.release();
            //System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            not.start();
//            Intent goback = new Intent(ShowMyAtt.this, MainActivity.class);
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
            back = MediaPlayer.create(AddNew.this, R.raw.titanic);
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