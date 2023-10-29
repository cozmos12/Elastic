package com.example.demo.mapper;

import com.example.demo.dto.ProductDto;
import com.example.demo.entitiy.Product;

public class Mapper {

    public static Product getProduct(ProductDto productDto){
        Product product=new Product();
        product.setId(productDto.getId());
        return product;
    }
}
