package com.example.demo.controller;

import com.example.demo.config.constants.ServiceMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.config.constants.ControllerConstants.API;

@RestController
@RequestMapping(API)
public class AbstractHttpController {
    private final List<String> twoHundredMessages = List.of(
            ServiceMessages.RESULT.getServiceMessage(),
            ServiceMessages.DELETED.getServiceMessage(),
            ServiceMessages.INSERTED.getServiceMessage(),
            ServiceMessages.NOT_DELETED.getServiceMessage()
    );
    private final List<String> fourHundredMessages = List.of(
            ServiceMessages.WRONG_FORMAT_OF_THE_INPUT_DTO.getServiceMessage(),
            ServiceMessages.PERSON_CANT_BE_NULL.getServiceMessage(),
            ServiceMessages.SONG_LIST_CANT_BE_NULL.getServiceMessage(),
            ServiceMessages.PERSON_CANT_BE_NULL.getServiceMessage(),
            ServiceMessages.PERSON_WITH_PROVIDED_NAME_EXISTS_IN_DB.getServiceMessage(),
            ServiceMessages.SONG_WITH_PROVIDED_NAME_EXISTS_IN_DB.getServiceMessage(),
            ServiceMessages.NOT_DELETED.getServiceMessage()
    );
    private final List<String> serverError = List.of(
            ServiceMessages.NULL_ON_PERSON_INSERT.getServiceMessage(),
            ServiceMessages.NULL_ON_SONG_INSERT.getServiceMessage()
    );
    protected ResponseEntity<ResBody> prepareResponseBody(@NonNull Map<String, ?> resultMap) {
        var stringResult = new ArrayList<>(resultMap.keySet()).get(0);

        if (twoHundredMessages.contains(stringResult)) {
            return ResponseEntity.ok().body(
                    createBody(resultMap)
            );
        } else if (fourHundredMessages.contains(stringResult)) {
            return ResponseEntity.badRequest().body(
                    createBody(resultMap)
            );
        } else if (serverError.contains(stringResult)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    createBody(resultMap)
            );
        }

        return null;
    }

    private ResBody createBody(@NonNull Map<String, ?> resultMap) {
        return new ResBody.ResponseBuilder().addTimestamp().addBodyMap(resultMap).build();
    }
}
