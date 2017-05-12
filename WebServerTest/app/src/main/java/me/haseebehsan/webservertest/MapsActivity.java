package me.haseebehsan.webservertest;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    double longitude, latitude;
    private LocationManager locationmanager;
    Location location, prevLocation;
    LocationListener locListner;
    Marker prev;
    // constant
    public static final long NOTIFY_INTERVAL = 4 * 1000; // 10 seconds
    private Handler mHandler = new Handler();
    private GoogleMap mMap;

    private Timer mTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new MapsActivity.TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }


    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    new MyTasl().execute("haseeb");
                    Log.e("map", "req");

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



                URL url = new URL("http://poocho.azurewebsites.net/servertesting/getlocation.php");
                URLConnection con = url.openConnection();

                Log.e("msg", "1"+username+" "+location);
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");

//                String data2 = URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(data);
                //Log.e("msg", "2"+username+" "+location);
//                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                //Log.e("msg", "3"+username+" "+location);
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
            String[] inputs = result.split(",");
            String[] longlat = inputs[1].split(" ");
            LatLng sydney = new LatLng(Double.parseDouble(longlat[0]), Double.parseDouble(longlat[1]));
            if(prev != null){
                PolylineOptions poly = new PolylineOptions();
                poly.add(prev.getPosition(), sydney);
                poly.color(Color.BLUE);
                mMap.addPolyline(poly);
                prev.remove();
            }
            else{
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10.0f));
            }
            prev = mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        }
    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
