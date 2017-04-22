package com.example.niazi.recieverapp;

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
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationmanager;
    double longitude, latitude;
    SmsManager sms;
    static String msg;
    String RecPhoneNum;
    Location location;
    TextView t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sms = SmsManager.getDefault();
        t1 = (TextView) findViewById(R.id.textView);
        t2 = (TextView) findViewById(R.id.textView2);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(new MyReceiver(), filter);

        locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria cri = new Criteria();

        String provider = locationmanager.getBestProvider(cri, false);

        if (provider != null & !provider.equals(""))

        {

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
            Location location = locationmanager.getLastKnownLocation(provider);

            locationmanager.requestLocationUpdates(provider,2000,1,this);

            if(location!=null)

            {

                onLocationChanged(location);

            }

            else{

                Toast.makeText(getApplicationContext(),"location not found",Toast.LENGTH_LONG ).show();

            }

        }

        else

        {

            Toast.makeText(getApplicationContext(),"Provider is null",Toast.LENGTH_LONG).show();

        }

    }



    @Override
    public void onLocationChanged(Location location) {
        msg = location.getLatitude()+","+location.getLongitude();
        t2.setText(msg);
        double altitude = location.getAltitude();
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        float speed = location.getSpeed();
        float bearing = location.getBearing();
        float accuracy = location.getAccuracy(); //in meters
        long time = location.getTime();
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
    public class MyReceiver extends BroadcastReceiver {

        public MyReceiver(){    }
        @Override public void onReceive(Context context, Intent intent) {
            String str="";
            t1.setText("Message Recieved...");
            Bundle bundle = intent.getExtras();
            SmsMessage[] recievedMsgs = null;
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                recievedMsgs = new SmsMessage[pdus.length];
                for (int i=0; i < pdus.length; i++) {
                    recievedMsgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    RecPhoneNum = recievedMsgs[i].getOriginatingAddress();
                    str+= recievedMsgs[i].getMessageBody().toString();
                }
            }
            System.out.println("========>"+RecPhoneNum);
            if(str.equals("Send GPS")){
                sms.sendTextMessage(RecPhoneNum, null, "geo:"+msg+"?z=14", null, null);
            }
        }
    }
}
