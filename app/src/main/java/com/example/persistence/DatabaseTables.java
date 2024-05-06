package com.example.persistence;

public class DatabaseTables {
    static class Record {

        static final String TABLE_NAME = "records";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_ARTIST = "artist";
        static final String COLUMN_NAME_YEAR = "year";

    }

    static final String SQL_CREATE_TABLE_RECORDS =
            // "CREATE TABLE records (id INTEGER PRIMARY KEY, title TEXT, artist TEXT, year INT)"
            "CREATE TABLE " + Record.TABLE_NAME + " (" +
                    Record.COLUMN_NAME_ID + " INTEGER PRIMARY KEY," +
                    Record.COLUMN_NAME_TITLE + " TEXT, " +
                    Record.COLUMN_NAME_ARTIST + " TEXT," +
                    Record.COLUMN_NAME_YEAR + " INT)";

    static final String SQL_DELETE_TABLE_RECORDS =
            // "DROP TABLE IF EXISTS records"
            "DROP TABLE IF EXISTS " + Record.TABLE_NAME;
}
