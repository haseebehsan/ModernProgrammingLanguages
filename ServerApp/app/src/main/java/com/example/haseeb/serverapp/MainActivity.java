package com.example.haseeb.serverapp;

import android.Manifest;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    ServerSocket myServer;
    InputStream in;
    BufferedReader input;
    TextView tv1;
    Socket conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
       // ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE},1);

        try {
             myServer = new ServerSocket(9898);
            Log.e("Exception", "In the try");
           // conn = myServer.accept();
            while(true) {
                Log.e("Exception", "In the loop");
                conn = myServer.accept();
                in = conn.getInputStream();
                input = new BufferedReader(new InputStreamReader(in));

                tv1 = (TextView)findViewById(R.id.tv1);
                tv1.setText(input.readLine());
                Log.e("Exception", "after setuopo");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("Exception", "General Exception"+e.toString());
        }



    }
}
