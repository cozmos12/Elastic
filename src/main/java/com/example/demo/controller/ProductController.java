package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    //@Autowired
    private final ProductService productService;



    @GetMapping("/findAll")
    public List<Product> getAllProducts(){
        return productService.findAll();
    }



    @PostMapping("/save")
    public void save(@RequestBody ProductDto productDto){
        productService.save(productDto);
    }




    @PatchMapping("/update")
    public Product update(@RequestBody ProductDto product){
        return productService.update(product);
    }
}
