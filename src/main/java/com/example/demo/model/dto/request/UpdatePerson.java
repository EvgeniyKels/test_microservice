package com.example.demo.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Getter
public class UpdatePerson {
    private List<Integer> personIds;
    private List<Integer> songIds;
}
