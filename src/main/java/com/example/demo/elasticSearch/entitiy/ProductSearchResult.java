package com.example.demo.elasticSearch.entitiy;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductSearchResult {
    @Id
    private int id;
     private String name;
    @Field(name="lastDate",type = FieldType.Date)
    private Date lastDate;
    @Field(name="location",type = FieldType.Text)
    private String location;
    @Field(name="categoryName",type = FieldType.Keyword)
    private String categoryName;
    @Field(name="details",type = FieldType.Keyword)
    private Map<String, Object> details;
    @Field(name="price",type = FieldType.Integer)
    private Integer price;


}
