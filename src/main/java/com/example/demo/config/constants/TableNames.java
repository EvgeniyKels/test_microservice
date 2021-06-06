package com.example.demo.config.constants;

import lombok.Getter;

@Getter
public final class TableNames {
    public static final String SONG_TABLE = "songs";
    public static final String PERSON_TABLE = "people";
    public static final String SONGS_PEOPLE_TABLE = "songs_people";
    public static final String PERSON_ID = "person_id";
    public static final String SONG_ID = "song_id";
    public static final String SONG_NAME = "song_name";
    public static final String PERSON_NAME = "person_name";

    private TableNames() {}
}