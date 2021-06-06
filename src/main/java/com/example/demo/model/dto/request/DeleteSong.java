package com.example.demo.model.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class DeleteSong {
    List<Long> songIds;
}