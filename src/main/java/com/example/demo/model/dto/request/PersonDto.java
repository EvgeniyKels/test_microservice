package com.example.demo.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public final class PersonDto {
    @JsonProperty("person_id")
    private final Long personId;
    @JsonProperty("person_name")
    @NotNull
    private final String personName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PersonDto(Long personId, @NotNull String personName) {
        this.personId = personId;
        this.personName = personName;
    }

    public Long getPersonId() {
        return personId;
    }

    public String getPersonName() {
        return personName;
    }
}
