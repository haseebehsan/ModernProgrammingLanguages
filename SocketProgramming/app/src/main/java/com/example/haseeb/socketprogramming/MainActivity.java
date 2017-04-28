package com.example.haseeb.socketprogramming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.SocketHandler;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText edit;
    String msg = "echo";
    String serverIP = "192.168.43.149";
    Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        edit = (EditText)findViewById(R.id.editText);



        thread = new Thread(new myThread());
        thread.start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = edit.getText().toString();
                //thread.stop();
                new Thread(new myThread()).start();
            }
        });

    }

        class myThread implements Runnable{

            @Override
            public void run() {
                try {
                    InetAddress serverAddr =  InetAddress.getByName(serverIP);

                    Socket socket = new Socket(serverAddr, 9898);

                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())),
                            true);
                    out.println(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }


}