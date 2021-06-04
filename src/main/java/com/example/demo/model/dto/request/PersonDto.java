package com.example.demo.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonDto {
    private Long personId;
    @JsonProperty("person_name")
    @NotNull
    private String personName;
}
