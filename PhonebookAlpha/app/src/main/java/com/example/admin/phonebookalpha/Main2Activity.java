package com.example.admin.phonebookalpha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    ListView inbox=null;
    static ArrayList<String> inbox_list=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        inbox=(ListView)findViewById(R.id.view_msglist);
        Intent intent =getIntent();
        inbox_list=intent.getStringArrayListExtra("inbox");
        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, inbox_list);
        inbox.setAdapter(adapter);
        inbox.invalidateViews();
    }
}