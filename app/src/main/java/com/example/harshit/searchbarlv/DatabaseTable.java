package com.example.harshit.searchbarlv;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DatabaseTable extends SQLiteOpenHelper {

    private static final String TAG = "db";

    //The columns we'll include in the dictionary table
    public static final String COL_WORD = "WORD";
    public static final String COL_housename= "housename";
    public static final String COL_imageLink= "imageLink";
    public static final String COL_pageRank= "pageRank";
    public static final String COL_books = "books";

    //

    private static final String DATABASE_NAME = "DICTIONARY";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final int DATABASE_VERSION = 1;


    private final Context mHelperContext;
    private SQLiteDatabase mDatabase;
    private static final String FTS_TABLE_CREATE =
            "CREATE TABLE " + FTS_VIRTUAL_TABLE +
                    " ( " +
                    COL_WORD + " TEXT PRIMARY KEY, " +
                    COL_housename + " TEXT,"+
                    COL_imageLink + " TEXT,"+
                    COL_pageRank + " TEXT,"+
                    COL_books + " TEXT"
                    +")";

    public DatabaseTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mHelperContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mDatabase = db;
        mDatabase.execSQL(FTS_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
        onCreate(db);
    }

    public long addWord(String word,
                        String housename,
                        String imageLink,
                        String pageRank,
                        String books
    )
    {
        SQLiteDatabase mDatabase = getWritableDatabase();

        ContentValues initialValues = new ContentValues();
        initialValues.put(COL_WORD, word);
        initialValues.put(COL_housename, housename);
        initialValues.put(COL_imageLink, imageLink);
        initialValues.put(COL_pageRank, pageRank);
        initialValues.put(COL_books, books);


        return mDatabase.insert(
                FTS_VIRTUAL_TABLE, null, initialValues);
    }

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = COL_WORD + " LIKE ?";
        String[] selectionArgs = new String[] {"%"+query+"%"};

        return query(selection, selectionArgs, columns);
    }

    public Cursor getAllWords() {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(getReadableDatabase(),
                null, null, null, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

}
