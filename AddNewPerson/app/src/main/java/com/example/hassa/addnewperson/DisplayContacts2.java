package com.example.hassa.addnewperson;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisplayContacts2 extends AppCompatActivity {

    Intent I1;
    Intent sms;
    Intent call;
    Button sb;
    Button cb;
    EditText ed1;
    EditText ed2;
    String phone;
    String name;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts2);
        I1 = getIntent();
        sms = new Intent(this,Sms.class);
        name = I1.getStringExtra("name");
        phone = I1.getStringExtra("phone");
        ed1 = (EditText) findViewById(R.id.editText3);
        ed2 = (EditText) findViewById(R.id.editText4);
        sb = (Button) findViewById(R.id.button4);
        cb = (Button) findViewById(R.id.button3);
        ed1.setText(name.toString());
        ed2.setText(phone.toString());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.CALL_PHONE};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }


//        class = new Intent(this,call.class);

        sb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sms.putExtra("phone",phone);
                startActivity(sms);
            }
        });
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:"+phone));
                startActivity(call);
            }
        });
    }
}
