package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private EditText titleInput, artistInput, yearInput;
    private TextView listRecords;
    private Button readButton, writeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the layout id to each EditText
        titleInput = findViewById(R.id.titleField);
        artistInput = findViewById(R.id.artistField);
        yearInput = findViewById(R.id.yearField);

        // Set the layout id to the TextView
        listRecords = findViewById(R.id.showRecords);

        // Set the layout id to each Button
        readButton = findViewById(R.id.readButton);
        writeButton = findViewById(R.id.writeButton);

        // Create instance of DatabaseHelper and declare database field and instantiate with
        // the returned value from .getWritableDatabase()
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        // addRecords() method is called when writeButton is pressed
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRecords();
            }
        });

        // Shows a list of the content in the database when readButton is pressed
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add all records in the database to a list
                List<Record> records = getRecords();

                // Put each row in the table to a StringBuilder that later can be used
                // with a toString() method to make the list visible in the TextView
                StringBuilder recordList = new StringBuilder();
                for (Record record : records) {
                    recordList.append(record.getId()).append(". ")
                            .append(record.getArtist()).append(" - ")
                            .append(record.getTitle()).append(" (")
                            .append(record.getYear()).append(")\n");
                }

                // Shows the recordList in the TextView
                listRecords.setText(recordList.toString());
            }
        });


    }

    private void addRecords() {

        String title = titleInput.getText().toString().trim();
        String artist = artistInput.getText().toString().trim();
        String yearString = yearInput.getText().toString().trim();

        if (title.isEmpty() || artist.isEmpty() || yearString.isEmpty()) {
            // Shows an error if any field is empty
            Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {

            int year = Integer.parseInt(yearString);

            // CLear fields in EditText
            titleInput.setText("");
            artistInput.setText("");
            yearInput.setText("");

            // Add records to database
            databaseHelper.addRecord(title, artist, year);

            Toast.makeText(MainActivity.this, "Album added to database!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "Please enter a valid year", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Record> getRecords() {
        List<Record> records = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query(DatabaseTables.Record.TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Record record = new Record(
                            cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_ID)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_TITLE)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_ARTIST)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Record.COLUMN_NAME_YEAR))
                    );
                    records.add(record);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("Marazp", "Error reading records", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        Toast.makeText(MainActivity.this, "Successfully read database!", Toast.LENGTH_SHORT).show();
        return records;
    }
}
