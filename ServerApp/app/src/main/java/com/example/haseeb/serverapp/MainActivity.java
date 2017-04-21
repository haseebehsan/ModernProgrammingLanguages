package com.example.haseeb.serverapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        try {
             myServer = new ServerSocket(9898);
            while(true) {
                conn = myServer.accept();
                in = conn.getInputStream();
                input = new BufferedReader(new InputStreamReader(in));

                tv1 = (TextView)findViewById(R.id.tv1);
                tv1.setText(input.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
