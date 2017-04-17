package com.example.assignment01.twoactivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent i = getIntent();
//The second parameter below is the default string returned if the value is not there.
        String txtData = i.getExtras().getString("txtData","");
        TextView txtInput2 = (TextView)findViewById(R.id.textView2);
        txtInput2.setText(txtData);
    }
}
