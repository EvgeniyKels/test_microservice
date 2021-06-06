package com.example.demo.controller;

import com.example.demo.config.constants.ServiceMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

import static com.example.demo.config.constants.ControllerConstants.API;

@RestController
@RequestMapping(API)
public class AbstractHttpController {
    protected ResponseEntity<ResBody> prepareResponseBody(@NonNull Map<String, ?> resultMap) {
        var stringResult = new ArrayList<>(resultMap.keySet()).get(0);

        if (stringResult.equals(ServiceMessages.RESULT.getServiceMessage())) {
            return ResponseEntity.ok().body(
                    new ResBody.ResponseBuilder().addTimestamp().addBodyMap(resultMap).build()
            );
        } else if (stringResult.equals(ServiceMessages.DELETED.getServiceMessage())) {

        }

        return null;
    }
}
