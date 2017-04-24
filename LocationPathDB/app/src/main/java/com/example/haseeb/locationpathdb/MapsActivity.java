package com.example.haseeb.locationpathdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.FloatRange;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    ArrayList<String> locations;
    SQLiteDatabase db;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        String data = "l";
        db = openOrCreateDatabase("Location", MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("select * from LocationHistory", null);
        locations = new ArrayList<String>();
        String s1="";
        if (cursor.moveToFirst()) {
            do {
                data = cursor.getString(cursor.getColumnIndex("location"));
                locations.add(data);
                s1 = s1 +"\n"+ data;
            } while (cursor.moveToNext());
        }
        String current = "";
        String next = "";
        String[] currentlonglat = null;
        String[] nextlonglat = null;
        float x1, x2 = 0, y1, y2 = 0;
        final ArrayList<String> loc = locations;
        PolylineOptions poly = new PolylineOptions();
        for(int i = 0 ; i < locations.size()-1; i++){
                current = loc.get(i);
                next = loc.get(i+1);
             currentlonglat= current.split(" ");
             nextlonglat= next.split(" ");
            x1 = Float.parseFloat(currentlonglat[0]);
            y1 = Float.parseFloat(currentlonglat[1]);
            x2 = Float.parseFloat(nextlonglat[0]);
            y2 = Float.parseFloat(nextlonglat[1]);

            poly.add(new LatLng(x1, y1), new LatLng(x2, y2));
           // String str = "geo:"+currentlonglat[0]+","+currentlonglat[1]+"?z=100";

        }
        poly.color(Color.BLUE);
        poly.width(5);
        mMap.addPolyline(poly);
        

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(x2, y2)));
    }
}
