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
import java.io.PrintWriter;
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
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    Log.e("Exception", "In the try");
                    // conn = myServer.accept();
                    while(!Thread.currentThread().isInterrupted()) {
                        Log.e("Exception", "In the loop");
                        conn = myServer.accept();
                        Log.e("Exception", "after acceptance");
                        CommunicationThread commThread = new CommunicationThread(conn);
                        new Thread(commThread).start();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch(Exception e){
                    e.printStackTrace();
                    Log.e("Exception", "General Exception"+e.toString());
                }
            }
        });

        thread.start();



    }

    class CommunicationThread implements Runnable {

        private Socket clientSocket;
        private BufferedReader input;
        private String messageSend;

        public CommunicationThread(Socket clientSocket) {
            this.clientSocket = clientSocket;

            try {
                this.setInput(new BufferedReader(new InputStreamReader(
                        this.clientSocket.getInputStream())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {




                tv1 = (TextView)findViewById(R.id.tv1);
                try {
                    tv1.setText(input.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("Exception", "after setuopo");
                /*
                 * try { String read = input.readLine();
                 * updateConversationHandler.post(new updateUIThread(read)); }
                 * catch (IOException e) { e.printStackTrace(); }
                 */
            }
        }

        public BufferedReader getInput() {
            return input;
        }

        public void setInput(BufferedReader input) {
            this.input = input;
        }
    }
}
