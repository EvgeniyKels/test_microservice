package com.example.demo.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class SongDto {
    @JsonProperty("song_id")
    private final Long songId;
    @JsonProperty("song_name")
    private final String songName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SongDto(Long songId, String songName) {
        this.songId = songId;
        this.songName = songName;
    }

    public Long getSongId() {
        return songId;
    }

    public String getSongName() {
        return songName;
    }
}
