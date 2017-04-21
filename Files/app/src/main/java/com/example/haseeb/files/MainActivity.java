package com.example.haseeb.files;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    TextView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PrintStream output = new PrintStream(openFileOutput("out.txt", MODE_PRIVATE));
            output.println("text1");
            output.println("text2");
            output.close();

            Scanner sc = new Scanner(openFileInput("out.txt"));

            String val = "";
            while(sc.hasNextLine()){
                val+= sc.nextLine();
            }

            v = (TextView)findViewById(R.id.tv1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        
    }
}
