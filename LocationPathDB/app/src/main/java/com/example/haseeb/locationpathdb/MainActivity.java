package com.example.haseeb.locationpathdb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
  
    Intent intent, listActivity, mapIntent;
    Button showdata, showloc, runquery, mapshow;
    ArrayList<String> locations;
    TextView tv1;
    SQLiteDatabase db;
    String no = "s_no";
    String name_id="name";
    int val1;
    String val2;
    EditText num, val;
    String s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = openOrCreateDatabase("Teacher", MODE_PRIVATE, null);
        //db.execSQL("create table StudentTab(s_no integer, name varchar(20))");
        num = (EditText)findViewById(R.id.number);
        val = (EditText)findViewById(R.id.name);
        showdata = (Button)findViewById(R.id.showdata);
        showloc = (Button)findViewById(R.id.showloc);
        runquery = (Button)findViewById(R.id.runquery);
        mapshow = (Button)findViewById(R.id.mapshow);
        val1=11;

        val2="Haseeb";

        Cursor cursor = db.rawQuery("select * from StudentTab", null);
        s1="";
        if (cursor.moveToFirst()) {

            Log.e("msg",""+cursor.getPosition() );
            do {
                s1 = s1 +"\n"+ cursor.getString(cursor.getColumnIndex(no))+" "
                        + cursor.getString(cursor.getColumnIndex(name_id));
            } while (cursor.moveToNext());
        }
        tv1 = (TextView)findViewById(R.id.tv1);
        tv1.setText(s1);


        runquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openOrCreateDatabase("Location", MODE_PRIVATE, null);
                String query = val.getText().toString();
                db.execSQL(query);
            }
        });

        mapIntent = new Intent(this, MapsActivity.class);
        intent = new Intent(this,backgroundTask.class);
        listActivity = new Intent(this, FullscreenActivity.class);


        mapshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mapIntent);
            }
        });

        showloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "l";
                db = openOrCreateDatabase("Location", MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("select * from LocationHistory", null);
                locations = new ArrayList<String>();
                String s1="";
                if (cursor.moveToFirst()) {
                    do {
                        data = cursor.getString(cursor.getColumnIndex("location"));
                        locations.add(data);
                        s1 = s1 +"\n"+ data;
                    } while (cursor.moveToNext());
                }
                tv1 = (TextView)findViewById(R.id.tv1);
                tv1.setText(s1);
                listActivity.putStringArrayListExtra("myarray1",locations);
                startActivity(listActivity);
            }
        });

        showdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openOrCreateDatabase("Teacher", MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("select * from StudentTab", null);

                String s1="";
                if (cursor.moveToFirst()) {
                    do {
                        s1 = s1 +"\n"+ cursor.getString(cursor.getColumnIndex(no))+" "
                                + cursor.getString(cursor.getColumnIndex(name_id));
                    } while (cursor.moveToNext());
                }
                tv1 = (TextView)findViewById(R.id.tv1);
                tv1.setText(s1);
            }
        });
        Button addButton = (Button)findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                val2 = val.getText().toString();
                val1 = Integer.parseInt(num.getText().toString());
                db = openOrCreateDatabase("Teacher", MODE_PRIVATE, null);
                db.execSQL("insert into StudentTab(" + no + ","+ name_id + ") values (" +val1 +","+ "'"+val2+"'" + ");");

                Cursor cursor = db.rawQuery("select * from StudentTab", null);
                String s1="";
                if (cursor.moveToFirst()) {
                    do {
                        s1 = s1 +"\n"+ cursor.getString(cursor.getColumnIndex(no))+" "
                                + cursor.getString(cursor.getColumnIndex(name_id));
                    } while (cursor.moveToNext());
                }
                tv1 = (TextView)findViewById(R.id.tv1);
                tv1.setText(s1);

            }
        });

        Button stopbutton = (Button) findViewById(R.id.stopbutton);
        // stopbutton.setText("Stop");
        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });


        Button startbutton = (Button) findViewById(R.id.startbutton);
        //  startbutton.setText("Start");
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });


    }
}
