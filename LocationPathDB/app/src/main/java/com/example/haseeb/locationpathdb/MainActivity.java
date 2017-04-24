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

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
  
    Intent intent;
    Button showdata, showloc;
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





        intent = new Intent(this,backgroundTask.class);
        showloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openOrCreateDatabase("Location", MODE_PRIVATE, null);
                Cursor cursor = db.rawQuery("select * from LocationHistory", null);
                String s1="";
                if (cursor.moveToFirst()) {
                    do {
                        s1 = s1 +"\n"+ cursor.getString(cursor.getColumnIndex("location"));
                    } while (cursor.moveToNext());
                }
                tv1 = (TextView)findViewById(R.id.tv1);
                tv1.setText(s1);
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
