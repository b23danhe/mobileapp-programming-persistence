package com.example.persistence;

public class Record {

    private long id;
    private String title;
    private String artist;
    private int year;

    public Record (long rid, String rTitle, String rArtist, int rYear){
        id = rid;
        title = rTitle;
        artist = rArtist;
        year = Integer.parseInt(Integer.toString(rYear));
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

}
