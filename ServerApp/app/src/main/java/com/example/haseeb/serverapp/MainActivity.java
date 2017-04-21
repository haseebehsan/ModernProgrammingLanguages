package com.example.haseeb.serverapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {
    ServerSocket myServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
             myServer = new ServerSocket(9898);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
