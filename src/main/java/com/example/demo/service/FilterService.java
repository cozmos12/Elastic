package com.example.demo.service;

import com.example.demo.elasticSearch.entitiy.ProductSearchResult;
import com.example.demo.elasticSearch.mapper.FilterMapper;
import com.example.demo.elasticSearch.service.ElasticService;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.converter.ListToJson;
import com.example.demo.util.converter.ListToMap;
import com.example.demo.util.converter.MapToJson;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilterService {
    private final ProductRepository productService;
    private final CategoryService categoryService;
    private final CategoryDetService categoryDetService;
    private final ProductDetService productDetService;
    private final ListToJson listToJson;

    private final MapToJson mapToJson;
    private final ListToMap listToMap;

    private final ElasticService elasticService;


    public FilterService(ProductRepository productService, CategoryService categoryService, CategoryDetService categoryDetService, ProductDetService productDetService, ListToJson listToJson, MapToJson mapToJson, ListToMap listToMap, ElasticService elasticService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.categoryDetService = categoryDetService;
        this.productDetService = productDetService;
        this.listToJson = listToJson;
        this.mapToJson = mapToJson;
        this.listToMap = listToMap;
        this.elasticService = elasticService;
    }


    public ProductSearchResult saveElastic(int productId, int categoryId){
        List<String> categoryParentNameList=new ArrayList<>();
        categoryParentNameList=categoryService.findCategoryWithParents(categoryId);
        String categoryName = listToJson.ListToJson(categoryParentNameList);

        List<String> categoryDetailsName=new ArrayList<>();
        categoryDetailsName=categoryDetService.findAllCategoryDetailsName(categoryId);

        List<String>categoryDetailsValue = productDetService.findAllDetailsValue(productId);

        Map<String ,Object> detailsMap = listToMap.convert(categoryDetailsName,categoryDetailsValue);

        Product product=productService.findById(productId).get();

        String name=product.getName();
        Date date=product.getLastUpdated();
        String imageUrl=product.getImageUrl();
        int price=product.getPrice();
        String location=product.getLocation();



       ProductSearchResult productSearchResult = FilterMapper.toFilter(productId,name,date,location,categoryName,detailsMap,price);
      return   elasticService.save(productSearchResult);

    }

}
