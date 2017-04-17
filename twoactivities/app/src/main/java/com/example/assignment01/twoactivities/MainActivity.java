package com.example.assignment01.twoactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),Main2Activity.class);

//Set the Data to pass
                EditText txtInput = (EditText) findViewById(R.id.editText);
                String txtData = txtInput.getText().toString();
                i.putExtra("txtData", txtData);

                startActivity(i);
            }
        });
    }
}
