package com.example.demo.elasticSearch.service;


import com.example.demo.elasticSearch.entitiy.ProductSearchResult;
import com.example.demo.elasticSearch.entitiy.SearchResult;
import com.example.demo.elasticSearch.mapper.FilterMapper;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.CollectionUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class ElasticService {
    private final ElasticsearchOperations elasticsearchTemplate;

    private final RestHighLevelClient client;

    public ElasticService(final ElasticsearchOperations elasticsearchTemplate, RestHighLevelClient client) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.client = client;
    }


    public ProductSearchResult save(ProductSearchResult productSearchResult){
        IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(productSearchResult.getId())).withObject(productSearchResult).build();
        elasticsearchTemplate.index(indexQuery, IndexCoordinates.of("product"));

        return productSearchResult;
    }

    public SearchResult getFiltersCategoryName(String value) throws IOException{
        //List<Filter> filters= elasticRepository.findByCategoryNameLike(value);
        SearchRequest searchRequest = new SearchRequest("product");
        searchRequest.source()
                .query(QueryBuilders.wildcardQuery("categoryName.keyword", "*"+value+"*"))
                .aggregation(
                        AggregationBuilders.terms("category_counts").field("categoryName.keyword")
                );

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        Terms terms = response.getAggregations().get("category_counts");
        Map<String, Long> categoryCounts = new HashMap<>();

        for (Terms.Bucket bucket : terms.getBuckets()) {
            String[] categories = bucket.getKeyAsString().replace("[", "").replace("]", "").split(",");
            for (String category : categories) {
                String cleanCategory = category.trim();
                categoryCounts.put(cleanCategory, categoryCounts.getOrDefault(cleanCategory, 0L) + bucket.getDocCount());
            }
        }


        SearchHit[] searchHits = response.getHits().getHits();

        List<ProductSearchResult> productSearchResults = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            ProductSearchResult searchResult = FilterMapper.toFilter(searchHit);
            productSearchResults.add(searchResult);
        }

        return new SearchResult(categoryCounts, productSearchResults);
    }

    public SearchResponse getFullDetails(String categoryName, Map<String, Object> details, int price) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        boolQuery.must(QueryBuilders.wildcardQuery("categoryName.keyword", "*" + categoryName + "*"));

        for (Map.Entry<String, Object> entry : details.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue().toString();
            boolQuery.must(QueryBuilders.matchPhraseQuery("details." + fieldName, value));
        }


        NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(
                "details",
                boolQuery,
                ScoreMode.Avg
        );
        searchSourceBuilder.query(nestedQuery);

        RangeQueryBuilder priceRangeQuery = QueryBuilders.rangeQuery("price")
                .from(100)
                .to(price);
        boolQuery.must(priceRangeQuery);

        searchSourceBuilder.query(boolQuery);
        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }






    public  SearchResponse price() throws IOException {
        SearchRequest searchRequest=new SearchRequest("product");
        RangeQueryBuilder priceFilter = QueryBuilders.rangeQuery("price")
                .gte(100)
                .lte(6000);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(priceFilter);

        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest,RequestOptions.DEFAULT);
        return response;




    }public SearchResponse filterByCategory(String categoryName) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        String wildcardCategoryName = "*" + categoryName + "*";
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("categoryName.keyword", wildcardCategoryName);

        searchSourceBuilder.query(queryBuilder);

        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
    public SearchResponse filterByName(String name) throws IOException {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        if (!StringUtils.isEmpty(name)) {
            QueryStringQueryBuilder queryStringQuery = QueryBuilders.queryStringQuery(name);
            queryStringQuery.defaultOperator(Operator.AND);
            queryStringQuery.fuzziness(Fuzziness.AUTO);

            searchSourceBuilder.query(queryStringQuery);
        }

        searchRequest.source(searchSourceBuilder);

        return client.search(searchRequest, RequestOptions.DEFAULT);
    }





/*
    public Map<String,Long> getCategoryCounts() throws IOException {
        SearchRequest searchRequest = new SearchRequest("filters");
        searchRequest.source()
                .query(QueryBuilders.matchAllQuery())
                .aggregation(
                        AggregationBuilders.terms("category_counts").field("categoryName")
                );

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        Terms terms = response.getAggregations().get("category_counts");
        Map<String, Long> categoryCounts = new HashMap<>();

        // Iterate through the buckets and combine categories as needed
        for (Terms.Bucket bucket : terms.getBuckets()) {
            String[] categories = bucket.getKeyAsString().replace("[", "").replace("]", "").split(",");
            for (String category : categories) {
                String cleanCategory = category.trim();
                categoryCounts.put(cleanCategory, categoryCounts.getOrDefault(cleanCategory, 0L) + bucket.getDocCount());
            }
        }

        return categoryCounts;
    }

*/

}
