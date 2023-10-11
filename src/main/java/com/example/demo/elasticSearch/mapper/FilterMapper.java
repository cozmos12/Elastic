package com.example.demo.elasticSearch.mapper;


import com.example.demo.elasticSearch.entitiy.ProductSearchResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.SearchHit;

import java.util.Date;
import java.util.Map;

public class FilterMapper {


    public static ProductSearchResult toFilter(SearchHit searchHit) {
        ObjectMapper objectMapper = new ObjectMapper();

        // Jackson yapılandırması
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

        Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();

        ProductSearchResult productSearchResult = objectMapper.convertValue(sourceAsMap, ProductSearchResult.class);
        productSearchResult.setId(Integer.parseInt(searchHit.getId()));

        return productSearchResult;
    }
    public static ProductSearchResult toFilter(int productId, String name, Date date, String location, String categoryName, Map<String ,Object> detailsMap, int price){
        ProductSearchResult productSearchResult =new ProductSearchResult();
        productSearchResult.setId(productId);
        productSearchResult.setName(name);
        productSearchResult.setDetails(detailsMap);
        productSearchResult.setLocation(location);
        productSearchResult.setLastDate(date);
        productSearchResult.setCategoryName(categoryName);
        productSearchResult.setPrice(price);
        return productSearchResult;
    }
}
