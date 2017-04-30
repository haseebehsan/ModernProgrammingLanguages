package com.example.haseeb.maps101;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

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

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        LatLng hostel1 = new LatLng(33.65703349, 73.15764405);
        LatLng uni1 = new LatLng(33.64984507, 73.15515965);
        LatLng uni2 = new LatLng(33.64977565, 73.15510393);

//        PolylineOptions poly = new PolylineOptions();
//        PolylineOptions poly2 = new PolylineOptions();
//        poly.add(hostel1, uni1);
//        poly2.add(hostel1, uni2);
//        mMap.addPolyline(poly);
//        mMap.addPolyline(poly2);

        PolylineOptions poly = new PolylineOptions();
        poly.add(hostel1, uni1);
        poly.add(hostel1, uni2);
        poly.width(1);
        poly.color(Color.BLUE);
        mMap.addPolyline(poly);





        mMap.addMarker(new MarkerOptions().position(hostel1).title("hostel"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hostel1));
    }
}
