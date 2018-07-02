package com.example.harshit.searchbarlv;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // List view
    private ListView lv;
   // Listview Adapter
    ArrayAdapter<GOTChar> adapter;

    // Search EditText
    EditText inputSearch;


    Button but;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but=(Button)findViewById(R.id.search);
        inputSearch = (EditText) findViewById(R.id.inputSearch);



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
             case R.id.history:
                showhistory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    GOTChar gotchar;
    public  void savetohistory(View v){

        String url =    "https://api.got.show/api/characters/"+inputSearch.getText().toString();
        //              https://api.got.show/api/characters/zollo
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //mTextView.setText("Response: " + response.toString());



                            try {
                                String message = response.getString("message");

                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                if(!message.equals("Success")){
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    JSONObject json_data = response.getJSONObject("data");
                                    //int id = json_data.getInt("id");
                                    gotchar = new GOTChar();
                                    gotchar.name = json_data.getString("name");
                                    gotchar.housename = json_data.getString("house");
                                    gotchar.pageRank = json_data.getString("pageRank");
                                    gotchar.books = json_data.getString("books");
                                    try {
                                        gotchar.imageLink = json_data.getString("imageLink");
                                    } catch (Exception exp) {
                                        gotchar.imageLink="-";
                                    }
                                    Toast.makeText(MainActivity.this,
                                            gotchar.toString(), Toast.LENGTH_LONG).show();
                                    Intent i2=new Intent(MainActivity.this,attributes.class);
                                    i2.putExtra("name",gotchar.name);

                                    DatabaseTable db=new DatabaseTable(MainActivity.this);
                                    try {
                                        db.addWord(
                                                gotchar.name,
                                                gotchar.housename,
                                                gotchar.imageLink,
                                                gotchar.pageRank,
                                                gotchar.books);

                                        db.close();
                                    }catch(Exception exp){}

                                    startActivity(i2);
                                    //MyGlobals.members.add(gotchar);
                                }

                            } catch (Exception exp) {
                                Toast.makeText(MainActivity.this, exp.toString(), Toast.LENGTH_SHORT).show();
                            }



                }}, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                } );

        queue.add(jsonObjectRequest);


    }

     public void showhistory (){
        Intent i=new Intent(this,data.class);
        startActivity(i);
     }
}