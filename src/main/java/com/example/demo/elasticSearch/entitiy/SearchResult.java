package com.example.demo.elasticSearch.entitiy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResult {

    private final Map<String, Long> categoryCounts;
    private final List<ProductSearchResult> productSearchResults;

    public SearchResult(Map<String, Long> categoryCounts, List<ProductSearchResult> productSearchResults) {
        this.categoryCounts = categoryCounts;
        this.productSearchResults = productSearchResults;
    }

    public Map<String, Long> getCategoryCounts() {
        return categoryCounts;
    }

    public List<ProductSearchResult> getProductSearchResults() {
        return productSearchResults;
    }
}
