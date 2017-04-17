package com.example.admin.phonebookalpha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_addContact;
    Button btn_inbox;
    Button btn_sendMessage;
    Button btn_call;

    EditText txt_name;
    EditText txt_number;
    EditText txt_message;

    //  ListView listview_contact;

    static ArrayList<String> message;
    //  ArrayAdapter<String> listAdapter;

    Cursor phone;


    String name = "";
    String phoneNumber = "";

    ArrayList<String> namelist = new ArrayList<String>();
    ArrayList<String> numberlist = new ArrayList<String>();

    static  String str="";

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(new MyReciever(), filter);

        message= new ArrayList<String>();

        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_number = (EditText) findViewById(R.id.txt_number);
        txt_message = (EditText) findViewById(R.id.txt_message);

        btn_sendMessage = (Button) findViewById(R.id.btn_sendMessage);
        btn_addContact = (Button) findViewById(R.id.btn_addContact);
        btn_inbox = (Button) findViewById(R.id.btn_inbox);
        btn_call = (Button) findViewById(R.id.btn_call);

        final ListView listview_contact = (ListView) findViewById(R.id.contactList);

        phone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");


        while (phone.moveToNext()) {
            name = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            namelist.add(name);
            numberlist.add(phoneNumber);

        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, namelist);
        listview_contact.setAdapter(adapter);

        phone.moveToNext();


        btn_addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                namelist.add(namelist.size(), txt_name.getText().toString());
                numberlist.add(numberlist.size(), txt_number.getText().toString());

                listview_contact.setAdapter(adapter);
            }

            ;
        });

        btn_call.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:" + txt_number.getText().toString()));
                try {
                    startActivity(call);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_sendMessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(txt_number.getText().toString(), null, txt_message.getText().toString(), null, null);
                Toast.makeText(getApplicationContext(), "SMS sent", Toast.LENGTH_SHORT).show();

            }
        });


        btn_inbox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                intent.putStringArrayListExtra("inbox", (ArrayList<String>) message);
                startActivity(intent);
            }
        });

        listview_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                        txt_name.setText(namelist.get(position));
                                                        txt_number.setText(numberlist.get(position));
                                                    }
                                                }

        );




    }


    class MyReciever extends BroadcastReceiver {



        //  public MyReceiver(){ }
        @Override
        public void onReceive(Context context, Intent intent) {
// TODO: This method is called when the BroadcastReceiver is receiving // an Intent broadcast.
            Bundle bundle = intent.getExtras();
            SmsMessage[] recievedMsgs = null;
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus"); recievedMsgs = new SmsMessage[pdus.length];
                for (int i=0; i < pdus.length; i++) {
                    recievedMsgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]); str += "SMS from " + recievedMsgs[i].getOriginatingAddress() +
                            " :" + recievedMsgs[i].getMessageBody().toString(); }
            }

            message.add(str);
            //     Toast.makeText(context, str, Toast.LENGTH_SHORT).show(); tv1.setText(str);
//throw new UnsupportedOperationException("Not yet implemented");
        } }



}
