package com.example.demo.model.dto.request;

import java.util.List;

public final class UpdateSong extends UpdateEntity {

    public UpdateSong(List<Integer> personIds, List<Integer> songIds) {
        super(personIds, songIds);
    }
}
