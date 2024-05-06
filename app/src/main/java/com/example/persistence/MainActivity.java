package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private EditText titleInput, artistInput, yearInput;
    private Button readButton, writeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readButton = findViewById(R.id.readButton);
        writeButton = findViewById(R.id.writeButton);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                titleInput = findViewById(R.id.titleField);
                artistInput = findViewById(R.id.artistField);
                yearInput = findViewById(R.id.yearField);

                String title = titleInput.getText().toString();
                String artist = artistInput.getText().toString();
                String year = yearInput.getText().toString();

                databaseHelper.addRecord(title, artist, year);
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private List<Record> getRecords() {
        Cursor cursor = database.query(DatabaseTables.Record.TABLE_NAME, null, null, null, null, null, null);
        List<Record> records = new ArrayList<>();
        while (cursor.moveToNext()) {
            Record record = new Record(
                    cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_ARTIST)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_YEAR))
            );
            records.add(record);
        }
        cursor.close();
        return records;
    }
}
