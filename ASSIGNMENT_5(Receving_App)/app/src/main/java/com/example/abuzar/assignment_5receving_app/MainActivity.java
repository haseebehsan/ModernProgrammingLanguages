package com.example.abuzar.assignment_5receving_app;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView lat;
    TextView Long;

    EditText fro;
    EditText Messg;

    protected String From;
    protected String Message;

    private LocationManager locationManager;
    private String provider;
    Location location;

    double lati;
    double longi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lat = (TextView) findViewById(R.id.lattitude);
        Long = (TextView) findViewById(R.id.textView5);

        fro = (EditText) findViewById(R.id.msg);
        Messg = (EditText) findViewById(R.id.msg1);
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.SEND_SMS},1);

        try {


            IntentFilter filter = new IntentFilter();
            filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            registerReceiver(new MyReceiver(), filter);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, this);

        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onLocationChanged(Location location) {
    lati= location.getLatitude();
        longi= location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

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
                        Message = currentMessage.getDisplayMessageBody();

                        // Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);


                        // Show Alert
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context,
                                "senderNum: "+ From + ", message: " + Message, duration);
                        toast.show();

                    } // end for loop
                    fro.setText(From);
                    Messg.setText(Message);

                    fro.setEnabled(false);
                    Messg.setEnabled(false);

                    if(Message.equals("Send GPS")){

    setcordinates(From);
                    }
                    else{
                        Toast toast = Toast.makeText(context,
                                "senderNum: "+ From + ", message: " + Message, Toast.LENGTH_LONG);
                        toast.show();
                    }

                }  // bundle is null

            } catch (Exception e) {
                //  Log.e("SmsReceiver", "Exception smsReceiver" bnbhj  +e);
                Toast toast = Toast.makeText(context,
                        "Error exception receive message " +e, Toast.LENGTH_LONG);

            }
        }
    }

    public void setcordinates(String number){
        Long.setText(""+longi);
        lat.setText(""+lati);
From=number;
        final String cordinates = ""+lati+","+longi+"?z=15";






        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(From, null, cordinates, null, null);
                Toast.makeText(getApplicationContext(), "SMS Sent!",
                        Toast.LENGTH_LONG).show();
            }
        }, 10000);

    }

}
