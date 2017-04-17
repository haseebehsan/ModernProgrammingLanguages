package me.haseebehsan.me.netflixexcessusagereminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    EditText tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (EditText) findViewById(R.id.editText);

        intent = new Intent(this, TimerService.class);

        Button stopbutton = (Button) findViewById(R.id.stopbutton);
       // stopbutton.setText("Stop");
        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });


        Button startbutton = (Button) findViewById(R.id.startbutton);
      //  startbutton.setText("Start");
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });


        IntentFilter filter = new IntentFilter();
        filter.addAction("back");
        registerReceiver(new ReceiverClassName(), filter);

    }

    public void setTv(String val){
        tv.setText(val);
    }



    private class ReceiverClassName extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent
                intent) {

            Bundle b = intent.getExtras();
            String msg = b.getString("msg");
            tv.setText(msg);
            Toast.makeText(getApplicationContext(),msg , Toast.LENGTH_SHORT).show();
            Log.d("tag1", msg);
// handle the received broadcast message
        //    ...
        }
    }

}

