package com.t2xm.utils.valuesConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String mapObjectToJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T mapJsonToObject(String json, Class<T> targetClass) throws JsonProcessingException {
        return objectMapper.readValue(json, targetClass);
    }
}