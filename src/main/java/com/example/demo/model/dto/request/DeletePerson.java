package com.example.demo.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

public final class DeletePerson {
    @JsonProperty("person_id")
    private final List<Long> personIds;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public DeletePerson(List<Long> personIds) {
        this.personIds = Collections.unmodifiableList(personIds);
    }

    public List<Long> getPersonIds() {
        return personIds;
    }
}