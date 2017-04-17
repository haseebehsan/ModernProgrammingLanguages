package com.uberx.assignment01.assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    EditText name = null;
    EditText num = null;
    Button save = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        name = (EditText) findViewById(R.id.n);
        num = (EditText)findViewById(R.id.num);
        save = (Button)findViewById(R.id.button2);
        save.setOnClickListener(this);
    }
    public void onClick(View v){
        String _name = name.getText().toString();
        String _num = num.getText().toString();
        ContactList cl = Main2Activity.saveContact(_name, _num);
        Intent back = new Intent();
        back.putExtra("Contact_name", cl.name);
        back.putExtra("Contact_num", cl.number);
        setResult(RESULT_OK, back);
        finish();
    }
    static ContactList saveContact(String name, String num){
        ContactList cl = new ContactList(name, num);
        return cl;
    }
    public void onBackPressed(){
        super.onBackPressed();
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
        finish();
    }
}
