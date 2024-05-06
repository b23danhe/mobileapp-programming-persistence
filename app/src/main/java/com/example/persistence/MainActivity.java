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

        //nrInput = findViewById(R.id.nrField);
        titleInput = findViewById(R.id.titleField);
        artistInput = findViewById(R.id.artistField);
        yearInput = findViewById(R.id.yearField);

        readButton = findViewById(R.id.readButton);
        writeButton = findViewById(R.id.writeButton);

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();

        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long inserting = databaseHelper.addRecord(
                        titleInput.getText().toString(),
                        artistInput.getText().toString(),
                        yearInput.getText().length());
                Toast.makeText(MainActivity.this, "Thank you for contributing, enjoy!", Toast.LENGTH_SHORT).show();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {

        });

    }
}
