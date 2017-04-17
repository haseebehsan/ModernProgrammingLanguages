package com.example.abuzar.assignment_5sender_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{
     public String num1 ="";
    private String From;
    private String mess;
    String na;
    String num;
    ListView Cl;
    Cursor C1;
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> number = new ArrayList<String>();
    private static final int REQ_CODE = 123;
    ArrayAdapter<String> myadapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.SEND_SMS},1);
        try {


                IntentFilter filter = new IntentFilter();
                filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
                registerReceiver(new MyReceiver(), filter);



            C1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "");
            C1.moveToFirst();
            Cl = (ListView) findViewById(R.id.Contact_list);
            load_contacts();
            myadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,name);
            Cl.setAdapter(myadapter);
            Cl.setOnItemClickListener(this);

        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    protected void load_contacts(){
        while (C1.moveToNext()){
            na= C1.getString(C1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            num=C1.getString(C1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name.add(na);
            number.add(num);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        na = name.get(position);
        num= number.get(position);
        msg_send(na,num);
    }
    public void msg_send(String name , String number){
        num1= number;
        try {
           final  Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(num1, null, "Send GPS", null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                }
            }, 10000);




        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again later!" + e.getMessage(),
                    Toast.LENGTH_LONG).show();

        }
    }

    public class MyReceiver extends BroadcastReceiver
    {
        // Get the object of SmsManager
        final SmsManager sms = SmsManager.getDefault();




        public void onReceive(Context context, Intent intent) {

            // Retrieves a map of extended data from the intent.
            final Bundle bundle = intent.getExtras();

            try {

                if (bundle != null) {

                    final Object[] pdusObj = (Object[]) bundle.get("pdus");

                    for (int i = 0; i < pdusObj.length; i++) {

                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        From = phoneNumber;
                        mess = currentMessage.getDisplayMessageBody();

                        // Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                        // Show Alert
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context,
                                "senderNum: "+ From + ", message: " + mess, duration);
                        toast.show();

                    } // end for loop
                   // if(From.equals(num)){
                        Map_intent(mess);
                   // }
                   // else{
                        Toast toast = Toast.makeText(context,
                                "senderNum: "+ From + ", message: " + mess, Toast.LENGTH_LONG);
                        toast.show();
                 //   }

                }  // bundle is null

            } catch (Exception e) {
                //  Log.e("SmsReceiver", "Exception smsReceiver" bnbhj  +e);
                Toast toast = Toast.makeText(context,
                        "Error exception receive message " +e, Toast.LENGTH_LONG);

            }
        }
    }
    public void Map_intent(String mess){
//Uri location = Uri.parse("geo:37.422219,-122.08364?z=14");
        Uri location = Uri.parse("geo:"+mess);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }

}

