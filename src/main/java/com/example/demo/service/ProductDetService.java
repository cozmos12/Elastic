package com.example.demo.service;

import com.example.demo.entity.CategoryDetails;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductsDetails;
import com.example.demo.repository.CategoryDetRepository;
import com.example.demo.repository.ProductDetRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDetService {

private final ProductDetRepository productDetRepository;
private final ProductRepository productRepository;

private final CategoryDetRepository categoryDetRepository;


    public ProductDetService(ProductDetRepository productDetRepository, ProductRepository productRepository, CategoryDetRepository categoryDetRepository) {
        this.productDetRepository = productDetRepository;
        this.productRepository = productRepository;
        this.categoryDetRepository = categoryDetRepository;

    }


    public List<ProductsDetails> findAll(int id) {
        return productDetRepository.findByProduct_Id(id);
    }

    public List<String> findAllDetailsValue(int id) {
        List<ProductsDetails>productsDetails=productDetRepository.findByProduct_Id(id);
        List<String> productsDetailsValues=new ArrayList<String>();
        for(ProductsDetails productDetail:productsDetails ){
            productsDetailsValues.add(productDetail.getValue());
        }
        return productsDetailsValues;
    }




    public List<ProductsDetails> save(List<String> values, int categoryId, int productId) {
        List<Integer> categoryDetailsIds = categoryDetRepository.findIdsByCategoryId(categoryId);
        List<ProductsDetails> productsDetailsList = new ArrayList<>();

        for (int i = 0; i < categoryDetailsIds.size(); i++) {
            String value = values.get(i);
            int categoryDetailsId = categoryDetailsIds.get(i);

            CategoryDetails categoryDetails = categoryDetRepository.getById(categoryDetailsId);

            ProductsDetails productsDetails = new ProductsDetails();
            productsDetails.setValue(value);
            productsDetails.setProduct(productRepository.findById(productId).get());
            productsDetails.setCategoryDetails(categoryDetails);




            productsDetailsList.add(productsDetails);
        }
        productDetRepository.saveAll(productsDetailsList);


        return productsDetailsList;

    }




}
