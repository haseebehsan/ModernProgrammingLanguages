package com.techneck.haseebehsan.app1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button button;
    Button button2;
    Button button3;
    Button button4;
    TextView textview;
    int count = 10;
    int score = 0;
    private int x, y, z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.num1);
        button2= (Button)findViewById(R.id.num2);
        button3= (Button)findViewById(R.id.num3);
        button4= (Button)findViewById(R.id.next);
        textview = (TextView)findViewById(R.id.textView2);

        generate();
        View.OnClickListener listen = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // click handling codeat
                Button ob = (Button)view;
                int value = Integer.parseInt((String) ob.getText());
                count--;
                if(isPrime(value)){
                    score++;

                    Toast.makeText(MainActivity.this, "Prime",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Not Prime",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                ending();
                generate();
            }
        };

        button.setOnClickListener(listen);
        button2.setOnClickListener(listen);
        button3.setOnClickListener(listen);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if(!isPrime(x) && !isPrime(y) && !isPrime(z)){
                    score++;
                    Toast.makeText(MainActivity.this, "right",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Wrong",
                            Toast.LENGTH_SHORT).show();
                }

                ending();
                generate();
            }
        });
    }

    private void generate(){

         this.x = (int)(Math.random() *100);
         this.y = (int)(Math.random() *100);
         this.z = (int)(Math.random() *100);

        button.setText(""+x);
        button2.setText(""+y);
        button3.setText(""+z);


    }

    private boolean isPrime(int num) {
        if (num < 2){ return false;}
        if (num == 2){ return true;}
        if (num % 2 == 0){ return false;}
        for (int i = 3; i * i <= num; i += 2) {
            if (num % i == 0) {return false;}
        }
        return true;
    }

    private void ending(){
        if(count<=0){
            textview.setText(""+score);
            score = 0;
            count = 10;
        }

    }

}
