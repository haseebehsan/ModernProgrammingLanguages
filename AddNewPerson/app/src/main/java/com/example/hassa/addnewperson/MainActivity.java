package com.example.hassa.addnewperson;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final  int PERMISSION_REQUEST_CODE =123 ;
    EditText ed1;
    EditText ed2;
    ListView l1;
    Button b1;
    Button b2;
    Intent I1;
    ArrayList<String> myArray;
    ArrayList<String> myArray2;
    ArrayAdapter<String> myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_DENIED) {

                Log.d("permission", "permission denied to SEND_SMS - requesting it");
                String[] permissions = {Manifest.permission.READ_CONTACTS};

                requestPermissions(permissions, PERMISSION_REQUEST_CODE);

            }
        }
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        l1 =  (ListView) findViewById(R.id.list1);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        I1 = new Intent(this,DisplayContacts.class);

        myArray = new ArrayList<String>();
        myArray2 = new ArrayList<String>();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,myArray);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,myArray2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myArray.add(ed1.getText().toString());
                myArray2.add(ed2.getText().toString());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                I1.putStringArrayListExtra("myarray1",myArray);
                I1.putStringArrayListExtra("myarray2",myArray2);
                startActivity(I1);
            }
        });

    }
}
