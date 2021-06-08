package com.example.demo.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

final class ResBody {
    @JsonProperty("timestamp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime timeStamp;
    @JsonProperty("result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> body;

    private ResBody() {}

    private ResBody(LocalDateTime localDateTime, Map<String, Object> resBody) {
        this.timeStamp = localDateTime;
        this.body = resBody;
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
            return this;
        }

        @Override
        public <K> IResponseBuilder addToBodyObject(K obj) {
            if (this.body == null) {
                body = new LinkedHashMap<>();
            }
            body.putAll(new ObjectMapper().convertValue(obj, Map.class));
            return this;
        }

        @Override
        public <K> IResponseBuilder addSingleObject(String fieldName, K obj) {
            body.put(fieldName, obj);
            return this;
        }

        @Override
        public ResBody build() {
            return new ResBody(this.timeStamp, this.body);
        }
    }
}
