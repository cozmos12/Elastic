package com.example.demo.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListToJson {

    @Autowired
    private ObjectMapper objectMapper;

    public String ListToJson(List<String> category) {
        try {
            String categoryJson=objectMapper.writeValueAsString(category);
            System.out.println(categoryJson);
            return categoryJson;
        }catch (Exception e) {
            e.printStackTrace();
            return "hata"+e.getMessage();
        }
    }

}
