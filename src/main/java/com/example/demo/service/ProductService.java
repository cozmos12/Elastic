package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.elasticSearch.entitiy.ProductSearchResult;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final FilterService filterService;
    private final ProductDetService productDetService;





    public ProductService(ProductRepository productRepository, CategoryService categoryService, FilterService filterService, ProductDetService productDetService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;


        this.filterService = filterService;
        this.productDetService = productDetService;
    }



    public List<Product> findAll() {
            return productRepository.findAll();
    }

    public Product find(int id){
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }



    public ProductSearchResult save(ProductDto productDto) {
            Category category= categoryService.find(productDto.getCategoryId());

            if(category!=null){
                Product product=  ProductMapper.toProduct(productDto,category);
                productRepository.save(product);
                productDetService.save(productDto.getValues(),productDto.getCategoryId(),productDto.getId());
                return filterService.saveElastic(productDto.getId(),productDto.getCategoryId());

            }else{
                return null;
            }


    }

    public Product update(ProductDto productDto) {
        Optional<Product> optionalProduct = productRepository.findById(productDto.getId());
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();

            List<Field> fields = Arrays.asList(productDto.getClass().getDeclaredFields());
            fields.forEach(field -> {
                field.setAccessible(true);
                try {
                    Object value = field.get(productDto);
                    if (value != null) {
                        Field productField = existingProduct.getClass().getDeclaredField(field.getName());
                        productField.setAccessible(true);
                        productField.set(existingProduct, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            return productRepository.save(existingProduct);
        }
        return null;
    }
















}
