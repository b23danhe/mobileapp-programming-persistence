package com.example.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1; // If this is incremented onUpgrade() will be executed
    private static final String DATABASE_NAME = "Records.db"; // The file name of our database
    private SQLiteDatabase database;
    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public long addRecord(String title, String artist, String year){
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseTables.Record.COLUMN_NAME_TITLE, title);
        values.put(DatabaseTables.Record.COLUMN_NAME_ARTIST, artist);
        values.put(DatabaseTables.Record.COLUMN_NAME_YEAR, year);
        return database.insert(DatabaseTables.Record.TABLE_NAME, null, values);
    }

    // This method is executed only if there is not already a database in the file `Records.db`
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DatabaseTables.SQL_CREATE_TABLE_RECORDS);
    }

    // This method is executed only if the database version has changed, e.g. from 1 to 2
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DatabaseTables.SQL_DELETE_TABLE_RECORDS);
        onCreate(sqLiteDatabase);
    }

}