package com.example.harshit.searchbarlv;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class data extends AppCompatActivity {
    DatabaseTable db = new DatabaseTable(this);
    ListView lvdata;
    ArrayAdapter<String> adapterd;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        lvdata = (ListView) findViewById(R.id.lvdata);

        Cursor c = db.getAllWords();
        ArrayList<String> items = new ArrayList<String>();
        if (c.moveToFirst()) {
            do {
                String data = c.getString(c.getColumnIndex("WORD"));
                items.add(data);
            } while (c.moveToNext());
        }


            //process Cursor and display results
             adapterd = new ArrayAdapter<String>(data.this,
                                            android.R.layout.simple_list_item_1,items);
                                    lvdata.setAdapter(adapterd);
        lvdata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                String name = (String)lvdata.getItemAtPosition(position);


                Intent i3=new Intent(data.this,attributes.class);
                Bundle extras=new Bundle();
                extras.putString("name",name);

                i3.putExtras(extras);
                startActivity(i3);


            }
        });

    }
        }