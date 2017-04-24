package com.example.niazi.quiz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView lst;
    Cursor phones;
    ArrayList<String> PhName = new ArrayList<String>();
    ArrayList<String> PhNo = new ArrayList<String>();
    ArrayAdapter<String> myAdapter;
    SmsManager sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(new MyReceiver(), filter);

        lst= (ListView) findViewById(R.id.listView);
        reloadList();
        lst.setOnItemClickListener(this);
        sms = SmsManager.getDefault();


        reloadList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String contactNo = PhNo.get(position);
        sms.sendTextMessage(contactNo,null,"Send GPS",null,null);
    }
    public void reloadList(){
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        phones.moveToFirst();
        String name, phoneNumber;

        while (phones.moveToNext()) {

            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            PhName.add(name);
            PhNo.add(phoneNumber);
            myAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, PhName);

            lst.setAdapter(myAdapter);

        }
    }

    public class MyReceiver extends BroadcastReceiver {

        public MyReceiver(){    }
        @Override public void onReceive(Context context, Intent intent) {
            String str="";
            Bundle bundle = intent.getExtras();
            SmsMessage[] recievedMsgs = null;
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                recievedMsgs = new SmsMessage[pdus.length];
                for (int i=0; i < pdus.length; i++) {
                    recievedMsgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    str+= recievedMsgs[i].getMessageBody().toString();
                }
            }
                Toast.makeText(context, str, Toast.LENGTH_LONG).show();
                System.out.println("Called!!!!");
                if(str.contains("geo")&&!str.contains("null")) {
                    System.out.println("geo found:"+str);
                    //System.out.println("=====================================:LatLong" + latitude + " ////: longitude" + longitude);
                    Uri location = Uri.parse(str);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                    startActivity(mapIntent);
                }
            else{
                    System.out.println("Not Found:"+str);
                }
            }

        }
    }

