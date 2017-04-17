package com.example.hassa.addnewperson;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Sms extends AppCompatActivity {

    Intent I1;
    String str;
    String num;
    SmsManager smsManager =null;
    TextView ed1;
    TextView tv1;
    EditText ed2;
    Button b1;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Overri de
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        smsManager = SmsManager.getDefault();
        ed1 = (TextView) findViewById(R.id.editText2);
        I1 = getIntent();
        ed1.setText(I1.getStringExtra("phone"));
        ed2 = (EditText) findViewById(R.id.editText4);
        b1 = (Button) findViewById(R.id.button);
        tv1 = (TextView) findViewById(R.id.textView3);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(new MyReceiver(), filter);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.SEND_SMS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//
                String phoneno = ed1.getText().toString();
                String message = ed2.getText().toString();

         /*Failed to used SMS MANAGER API */

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneno, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    Log.e("YOUR_APP_LOG_TAG", "I got an error", e);
                }
            /*  Succeed in using SMS Manager API */
            }
        });

    }
    public class MyReceiver extends BroadcastReceiver
    {
        public MyReceiver(){ }
        @Override
        public void onReceive(Context context, Intent intent)
        {
// TODO: This method is called when the BroadcastReceiver is receiving
// an Intent broadcast.
            Bundle bundle = intent.getExtras();
            SmsMessage[] recievedMsgs = null;
            if (bundle != null)
            {
                Object[] pdus = (Object[]) bundle.get("pdus");
                recievedMsgs = new SmsMessage[pdus.length];
                for (int i=0; i < pdus.length; i++)
                {
                    recievedMsgs[i] = SmsMessage.createFromPdu(((byte[]) pdus[i]));
                    num = "SMS from" + recievedMsgs[i].getOriginatingAddress();
                    str =  recievedMsgs[i].getMessageBody().toString();
                }
            }
            Toast.makeText(Sms.this,num, Toast.LENGTH_SHORT).show();
            tv1.setText(str);
//throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
