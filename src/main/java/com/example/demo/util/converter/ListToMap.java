package com.example.demo.util.converter;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ListToMap {

    public Map<String, Object> convert(List<String> categoryNames,List<String>productValues){
        Map<String, Object> details = new HashMap<String, Object>();
        for(int i=0;i<categoryNames.size();i++){
            details.put(categoryNames.get(i),productValues.get(i));
        }

        return details;
    }



}
