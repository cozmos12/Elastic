package com.example.demo.elasticSearch.controller;

import com.example.demo.elasticSearch.entitiy.SearchResult;
import com.example.demo.elasticSearch.service.ElasticService;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController

@RequestMapping("/search")
public class ElasticSearchController {
    private final ElasticService elasticService;

    public ElasticSearchController(ElasticService elasticService) {
        this.elasticService = elasticService;
    }



    @GetMapping("/category/{value}")
    public ResponseEntity<SearchResult> filterCategoryName(@PathVariable String value) throws IOException {
        SearchResult productSearchResults =elasticService.getFiltersCategoryName(value);
        return ResponseEntity.ok(productSearchResults);
    }
    @GetMapping("/{value}")
    public SearchResponse filterCategory(@PathVariable String value) throws IOException {
        SearchResponse searchResponse=elasticService.filterByCategory(value);
        return searchResponse;

    }

    @GetMapping("name/{name}")
    public SearchResponse filterName(@PathVariable String name) throws IOException {
        SearchResponse searchResponse=elasticService.filterByName(name);
        return searchResponse;

    }

    @GetMapping("price")
    public SearchResponse filterprice() throws IOException {
        SearchResponse searchResponse=elasticService.price();
        return searchResponse;

    }
/*
    @GetMapping("get/{categoryName}/{price}")
    public SearchResponse getfullfilters(@PathVariable String categoryName, @RequestBody Map<String, Object> details, @PathVariable int price) throws IOException {
        SearchResponse searchResponse=elasticService.getfullfilters(categoryName,details,price);
        return searchResponse;
    }

 */

    @GetMapping("details/{categoryName}/{price}")
    public SearchResponse getfulldetails(@PathVariable String categoryName, @RequestBody Map<String, Object> details,@PathVariable int price) throws IOException {
        SearchResponse searchResponse=elasticService.getFullDetails(categoryName,details,price);
        return searchResponse;
    }
/*
    @GetMapping("/value")
    public ResponseEntity<Map<String, Long>> getValues() throws IOException {
        return ResponseEntity.ok(elasticService.getCategoryCounts());
    }

*/
}
