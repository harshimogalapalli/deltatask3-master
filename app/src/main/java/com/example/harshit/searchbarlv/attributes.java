package com.example.harshit.searchbarlv;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class attributes extends AppCompatActivity {
    TextView name,house,pageRank,books;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attributes);
        name=(TextView)findViewById(R.id.name);
        house=(TextView)findViewById(R.id.house);
        pageRank=(TextView)findViewById(R.id.pageRank);
        books=(TextView)findViewById(R.id.books);
        image = (ImageView) findViewById(R.id.image);

        DatabaseTable db=new DatabaseTable(attributes.this);

        try {
//            Cursor gotchar = db.getWordMatches(
//                    this.getIntent().getStringExtra("name"),
//                    new String[]{"WORD", "housename", "imageLink", "pageRank", "books"});

            String query="select * from fts where word like ?";
            Cursor gotchar = db.getReadableDatabase().rawQuery(
                    query,
                    new String[]{this.getIntent().getStringExtra("name")});

            gotchar.moveToFirst();

            if(gotchar!=null) {
                //GOTChar gotchar=(GOTChar)(getSerializableExtra("gotchar"));
                try {
                    String imglink=gotchar.getString(2);
                    if(!imglink.equals("-"))
                        Picasso.with(this).load(
                            "https://api.got.show" +imglink).into(image);
                }
                catch(Exception exp) {

                }
//        Picasso.with(this)
//                .load(gotchar.imageLink)
//                .resize(50, 50)
//                .centerCrop()
//                .into(image);
//     //   Picasso.with(this).load(gotchar.imageLink).into(image);
                name.setText(gotchar.getString(0));
                house.setText("House---" + gotchar.getString(1));
                pageRank.setText("pageRank---" + gotchar.getString(3));
                books.setText("Books---" + gotchar.getString(gotchar.getColumnIndex("books")));
            }
            else
                Toast.makeText(this, "gotchar is null", Toast.LENGTH_SHORT).show();

        }
        catch(Exception exp) {
            exp.printStackTrace();
            Toast.makeText(this, exp.toString(), Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
