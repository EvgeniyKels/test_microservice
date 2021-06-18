package com.example.demo.controller;

import java.util.Map;

interface IResponseBuilder {
    IResponseBuilder addTimestamp();
    <K> IResponseBuilder addBodyMap(Map<String, K> map);
    <K> IResponseBuilder addToBodyObject(K obj);
    <K> IResponseBuilder addSingleObject(String fieldName, K obj);
    ResBody build();
}
