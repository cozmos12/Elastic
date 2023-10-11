package com.example.demo.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapToJson {

    @Autowired
    private ObjectMapper objectMapper;
    public String convert(Map<String,String> details ){
        try {
            String json =objectMapper.writeValueAsString(details);
            System.out.println();
            return json;
        }catch (Exception e){
            e.printStackTrace();
            return "hata"+e.getMessage();
        }

    }
}
