package com.example.demo.model.dto.request;

import java.util.List;

public final class UpdatePerson extends UpdateEntity {

    public UpdatePerson(List<Integer> personIds, List<Integer> songIds) {
        super(personIds, songIds);
    }
}
