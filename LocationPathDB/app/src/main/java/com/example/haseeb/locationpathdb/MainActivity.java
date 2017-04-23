package com.example.haseeb.locationpathdb;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SQLiteDatabase db = openOrCreateDatabase("Teacher", MODE_PRIVATE, null);
        db.execSQL("create table StudentTab(s_no integer, name varchar(20))");
        String no="s_no";
        int val1=10;
        String name_id="name";
        String val2="Mubeen";
        db.execSQL("insert into StudentTab(" + no + ","+ name_id + ") values (" +val1 +","+ "'"+val2+"'" + ");");
        Cursor cursor = db.rawQuery("select * from StudentTab", null);
        String s1="";
        if (cursor.moveToFirst()) {
            do {
                s1 = s1 +" "+ cursor.getString(cursor.getColumnIndex(no))+" "
                        + cursor.getString(cursor.getColumnIndex(name_id));
            } while (cursor.moveToNext());
        }
        tv1 = (TextView)findViewById(R.id.tv1);
        tv1.setText(s1);
        



        intent = new Intent(this,backgroundTask.class);

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
