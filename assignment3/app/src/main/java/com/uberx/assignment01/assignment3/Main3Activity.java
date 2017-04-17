package com.uberx.assignment01.assignment3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    EditText name = null;
    EditText number = null;
    Button call = null;
    Button back = null;
    EditText msgTxt = null;
    Button msgBtn = null;
    String num = " ";
    String msg = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        name = (EditText)findViewById(R.id.name);
        number = (EditText)findViewById(R.id.number);
        call = (Button)findViewById(R.id.button3);

        back = (Button)findViewById(R.id.button5);
        msgTxt = (EditText)findViewById(R.id.msgArea);
        msgBtn = (Button)findViewById(R.id.msgbtn);

        Intent intent = getIntent();
        String _name = intent.getStringExtra("name");
        String _number = intent.getStringExtra("num");

        name.setText(_name);
        number.setText(_number);

        call.setOnClickListener(this);
        back.setOnClickListener(this);
        msgBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== call.getId()){
            num = number.getText().toString();
            Intent phone = new Intent(Intent.ACTION_CALL);
            phone.setData(Uri.parse("tel:"+num));
            if(ActivityCompat.checkSelfPermission(Main3Activity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                return;
            startActivity(phone);
        }
        if(v.getId()== msgBtn.getId()){
             num = number.getText().toString();
             msg = msgTxt.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num, null, msg, null , null);
                Toast.makeText(getApplicationContext(), "Send Successful", Toast.LENGTH_LONG).show();
        }
        if(v.getId()==back.getId()){
            finish();
        }
    }
}
