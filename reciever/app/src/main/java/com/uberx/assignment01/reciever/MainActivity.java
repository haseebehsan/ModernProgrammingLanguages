package com.uberx.assignment01.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import static com.uberx.assignment01.reciever.MainActivity.tv1;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        smsManager = SmsManager.getDefault();
         tv1 = (TextView) findViewById(R.id.tv1);
        
        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(new MyReceiver(), filter);

    }
}

class MyReceiver extends BroadcastReceiver
{
    public MyReceiver(){ }
    @Override
    public void onReceive(Context context, Intent intent)
    {
// TODO: This method is called when the BroadcastReceiver is receiving
// an Intent broadcast.
        Bundle bundle = intent.getExtras();
        SmsMessage[] recievedMsgs = null;
       // String str = "adder ";
        if (bundle != null)
        {
            Object[] pdus = (Object[]) bundle.get("pdus");
            recievedMsgs = new SmsMessage[pdus.length];
            for (int i=0; i < pdus.length; i++)
            {
                recievedMsgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += "SMS from " + recievedMsgs[i].getOriginatingAddress() +
                        " :" + recievedMsgs[i].getMessageBody().toString();
            }
        }
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

        tv1.setText(str);

//throw new UnsupportedOperationException("Not yet implemented");
    }
}
