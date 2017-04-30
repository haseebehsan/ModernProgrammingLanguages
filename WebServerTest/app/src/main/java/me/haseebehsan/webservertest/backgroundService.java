package me.haseebehsan.webservertest;


import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.widget.Toast;

public class backgroundService extends Service  {
    SQLiteDatabase db;
    double longitude, latitude;
    private LocationManager locationmanager;
    Location location, prevLocation;
    LocationListener locListner;
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
        db = openOrCreateDatabase("Location", MODE_PRIVATE, null);
        ////db.execSQL("create table LocationHistory( location varchar(20))");
        locListner = new myLocation();
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
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

                    locationmanager.requestLocationUpdates(provider, 2000, 1, locListner);

                    if(location!=null)

                    {

                        locListner.onLocationChanged(location);

                    }

                    else{

                        Toast.makeText(getApplicationContext(),"location not found",Toast.LENGTH_LONG ).show();

                    }


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


    public class MyTasl extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String Complete_line="";
            try {
                String username = params[0];
                String location = params[1];


                URL url = new URL("http://poocho.azurewebsites.net/servertesting/updatelocation.php");
                URLConnection con = url.openConnection();

                Log.e("msg", "1"+username+" "+location);
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data+="&"+ URLEncoder.encode("location", "UTF-8") +  "=" + URLEncoder.encode(location, "UTF-8") ;
                data+="&"+ URLEncoder.encode("timestamp", "UTF-8") +  "=" + URLEncoder.encode(new Date().toString(), "UTF-8") ;
//                String data2 = URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(data);
                Log.e("msg", "2"+username+" "+location);
//                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                Log.e("msg", "3"+username+" "+location);
                String line = null;
                Complete_line = "";
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    Complete_line = Complete_line + line;
                    break;
                }
                Log.e("msg", "1"+Complete_line);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Complete_line;
        }
        @Override
        protected void onPostExecute(String result){
            Log.e("db", "record added"+result);
        }
        }


    public class myLocation implements LocationListener{

        @Override
        public void onLocationChanged (Location location){
            Float distance = Float.valueOf(0);
            String msg = location.getLatitude()+" "+location.getLongitude();
            if(prevLocation != null){

                distance = location.distanceTo(prevLocation);


                if(distance>1){
                   // db.execSQL("insert into LocationHistory(location) values ('"+msg+" "+distance+"');");
                    new MyTasl().execute("haseeb", msg);
                    Log.e("db", "record accessed"+msg);
                    Toast.makeText(getApplicationContext(), "added: "+msg+" "+distance, Toast.LENGTH_SHORT).show();
                }
                msg+= " "+distance;
            }

            //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            Log.e("loc", msg);




            prevLocation = location;

        }

        @Override
        public void onStatusChanged (String provider,int status, Bundle extras){

        }

        @Override
        public void onProviderEnabled (String provider){

        }

        @Override
        public void onProviderDisabled (String provider){

        }

       /* private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }
*/
    }
}
