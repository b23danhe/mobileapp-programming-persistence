package com.example.persistence;

public class DatabaseTables {
    static class Records {

        static final String TABLE_NAME = "records";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_ARTIST = "artist";
        static final String COLUMN_NAME_YEAR = "year";

    }

    static final String SQL_CREATE_TABLE_RECORDS =
            // "CREATE TABLE records (id TEXT PRIMARY KEY, artist TEXT, year INT)"
            "CREATE TABLE " + Records.TABLE_NAME + " (" +
                    Records.COLUMN_NAME_TITLE + " TEXT PRIMARY KEY," +
                    Records.COLUMN_NAME_ARTIST + " TEXT," +
                    Records.COLUMN_NAME_YEAR + " INT)";

    static final String SQL_DELETE_TABLE_RECORDS =
            // "DROP TABLE IF EXISTS records"
            "DROP TABLE IF EXISTS " + Records.TABLE_NAME;
}
