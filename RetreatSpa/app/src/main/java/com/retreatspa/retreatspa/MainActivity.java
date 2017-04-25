package com.retreatspa.retreatspa;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();





        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        about = (TextView)findViewById(R.id.about);
        about.setText("Relaxing home service spa in kuwait since oct,2015. We provide the best relaxing service with the best quality . The spa and its staff working hard to gurantee your satisifcation . Our service are all tyes of massage and hair spa , also we do morrocan bath . The staff are professional in their job and well prepared to give you the best service . Our service can be delivred to all areas in kuwait from khairan to jahra . We gurantee the level of the service \n\n");


//
//        File root = Environment.getExternalStorageDirectory();
//        ImageView IV = (ImageView) findViewById(R.id."image view");
//        Bitmap bMap = BitmapFactory.decodeFile(root+"/images/01.jpg");
//        IV.setImageBitmap(bMap);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic8);
//        ImageView img1 = (ImageView) findViewById(R.id.imageView);
//        img1.setImageBitmap(bitmap);
//
//        ImageView img2 = (ImageView) findViewById(R.id.imageView2);
//        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic7);
//        img2.setImageBitmap(bitmap);
//
//        ImageView img3 = (ImageView) findViewById(R.id.imageView3);
//        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic6);
//        img3.setImageBitmap(bitmap);
//
//        ImageView img4 = (ImageView) findViewById(R.id.imageView4);
//        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic5);
//        img4.setImageBitmap(bitmap);
//
//        ImageView img5 = (ImageView) findViewById(R.id.imageView5);
//        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic4);
//        img5.setImageBitmap(bitmap);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.e("tag1", "click handler");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_payment) {
            Log.e("tag1", "nav_paymeent click");

                Uri uri = Uri.parse("https://www.myfatoorah.com/31012017185313275962828168"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_about) {
            Log.e("tag1", "nav_review about");

        }  else if (id == R.id.nav_review) {
            Log.e("tag1", "nav_review click");
            Intent in = new Intent(getBaseContext(), Review.class);
            startActivity(in);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
