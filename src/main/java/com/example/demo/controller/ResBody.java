package com.example.demo.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

final class ResBody {
    @JsonProperty("timestamp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime timeStamp;
    @JsonProperty("result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Map<String, Object> body;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    private ResBody(LocalDateTime localDateTime, Map<String, Object> resBody) {
        this.timeStamp = localDateTime;
        this.body = Collections.unmodifiableMap(resBody);
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public static class ResponseBuilder implements IResponseBuilder{

        private LocalDateTime timeStamp;
        private Map<String, Object> body;

        @Override
        public IResponseBuilder addTimestamp() {
            timeStamp = LocalDateTime.now();
            return this;
        }

        @Override
        public <K> IResponseBuilder addBodyMap(@NonNull Map<String, K> map) {
            if (this.body == null) {
                body = new LinkedHashMap<>();
            }
            body.putAll(map);
            body = Collections.unmodifiableMap(body);
            return this;
        }

        @Override
        public <K> IResponseBuilder addToBodyObject(K obj) {
            if (this.body == null) {
                body = new LinkedHashMap<>();
            }
            body.putAll(new ObjectMapper().convertValue(obj, Map.class));
            body = Collections.unmodifiableMap(body);
            return this;
        }

        @Override
        public <K> IResponseBuilder addSingleObject(String fieldName, K obj) {
            if (this.body == null) {
                body = new LinkedHashMap<>();
                body.put(fieldName, obj);
                body = Collections.unmodifiableMap(body);
            } else {
                Map<String, Object> newBody = new LinkedHashMap<>(body);
                newBody.put(fieldName, obj);
                body = Collections.unmodifiableMap(newBody);
            }
            return this;
        }

        @Override
        public ResBody build() {
            return new ResBody(this.timeStamp, this.body);
        }
    }
}
