package com.uberx.assignment01.assignment3;


import android.content.Intent;
import android.database.Cursor;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.SortedSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn = null;
    ListView listv = null;
    Cursor crs;
    ArrayAdapter<ContactList> myAdapter;
    private static final int REQ_CODE = 123;
    ArrayList<ContactList> list = new ArrayList<ContactList>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btn = (Button) findViewById(R.id.button);
        listv = (ListView) findViewById(R.id.list_v);
        crs = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        crs.moveToFirst();
        int count = crs.getCount();
        String name;
        String phoneNumber;
        for (int i = 0; i < count-1; i++) {
            crs.moveToNext();
            name = crs.getString(crs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = crs.getString(crs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactList cl = new ContactList(name, phoneNumber);
            list.add(cl);
        }
        listv.setOnItemClickListener( new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> list, View row, int index, long rowID)
            {
                ContactList con = (ContactList) list.getItemAtPosition(index);
                gotoFunction(con);
            } } );
        myAdapter = new ArrayAdapter<ContactList>(this, android.R.layout.simple_list_item_1, list);
        listv.setAdapter(myAdapter);
        btn.setOnClickListener(this);
    }
    public void gotoFunction(ContactList cl){

        Intent go = new Intent(this, Main3Activity.class);
        go.putExtra("name", cl.name);
        go.putExtra("num", cl.number);
        startActivity(go);
    }
    public void onClick(View v) {
        Intent addcon = new Intent(this, Main2Activity.class);
        startActivityForResult(addcon, REQ_CODE);
    }
    protected void onActivityResult(int requestCode,  int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_CODE)
        {
            String name = intent.getStringExtra("Contact_name");
            String number = intent.getStringExtra("Contact_num");
            ContactList newContact = new ContactList(name, number);
            list.add(newContact);
            myAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Number Saved Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
