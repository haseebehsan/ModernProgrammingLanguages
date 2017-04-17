package com.example.hassa.addnewperson;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayContacts extends AppCompatActivity {

    Cursor phones;
    Intent I1;
    Intent I2;
    ListView l1;
    String name;
    String PhoneNumber;
    ArrayList<String> list;
    ArrayList<String> list2;
    ArrayList<String> list3;
    ArrayList<String> list4;
    ArrayAdapter<String> myAdapter;
    final static int PERMISSION_REQUEST_CODE=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);
        list2 = new ArrayList<String>();
        I1 = getIntent();
        I2 = new Intent(this,DisplayContacts2.class);
        list = I1.getStringArrayListExtra("myarray1");
        list2 = I1.getStringArrayListExtra("myarray2");
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null,null);
        phones.moveToFirst();
        while(phones.moveToNext()) {
            name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            PhoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            list.add(name);
            list2.add(PhoneNumber);
        }
        phones.close();
//




        myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        l1 = (ListView) findViewById(R.id.list1);
        l1.setAdapter(myAdapter);
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                I2.putExtra("name",list.get(i));
                I2.putExtra("phone",list2.get(i));
                startActivity(I2);

            }
        });

    }
}
