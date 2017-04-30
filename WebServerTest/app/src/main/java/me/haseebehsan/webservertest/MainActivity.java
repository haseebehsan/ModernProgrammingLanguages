package me.haseebehsan.webservertest;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    MyTasl request;
    TextView roleField;
    EditText ed1 = null;
    EditText ed2 = null;
    TextView tv = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        tv = (TextView) findViewById(R.id.textView5);
//        roleField = (TextView) findViewById(R.id.roleField);
        //request = new MyTasl();



    }

    public void onclick(View view){

//        String[] params = {ed1.getText().toString(), ed2.getText().toString()};
        new MyTasl().execute(ed1.getText().toString(), ed2.getText().toString());

    }

    public class MyTasl extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            String Complete_line="";
            try {
                String username = params[0];
                String password = params[1];

                URL url = new URL("http://poocho.azurewebsites.net/servertesting/Test2.php");
                URLConnection con = url.openConnection();

                Log.e("msg", "1"+username+" "+password);
                String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                        data+="&"+ URLEncoder.encode("password", "UTF-8") +  "=" + URLEncoder.encode(password, "UTF-8") ;
//                String data2 = URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                con.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(data);
                Log.e("msg", "2"+username+" "+password);
//                wr.write(data2);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                Log.e("msg", "3"+username+" "+password);
                String line = null;
                Complete_line = "";
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    Complete_line = Complete_line + line;
                    break;
                }
                Log.e("msg", "1"+Complete_line);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Complete_line;
        }
        @Override
        protected void onPostExecute(String result){
            tv.setText(result);
        }
    }
}

