package com.example.haseeb.locationpathdb;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import java.util.Timer;
import java.util.TimerTask;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;

public class backgroundTask extends Service implements LocationListener {

    double longitude, latitude;
    private LocationManager locationmanager;
    Location location;
    // constant
    public static final long NOTIFY_INTERVAL = 4 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mTimer.cancel();
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast

                    locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Criteria cri = new Criteria();
                    String provider = locationmanager.getBestProvider(cri, false);
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

                    locationmanager.requestLocationUpdates(provider, 2000, 1, this);
                    Log.e("tag1", "in run");

                    String message = "";

                    // getApplicationContext();
                    Intent done = new Intent();
                    done.setAction("back");
                    done.putExtra("msg", message);
                    sendBroadcast(done);

                    //Toast.makeText(getApplicationContext(), "-", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

        @Override
        public void onLocationChanged(Location location) {

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

       /* private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }
*/

}