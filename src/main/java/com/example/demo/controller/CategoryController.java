package com.example.demo.controller;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private  final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }




    @GetMapping("/find/{id}")
    public Category findCategory( @PathVariable  int id){
        return categoryService.find(id);
    }

    @PostMapping("/save")
    public Category save(@RequestBody CategoryDto categoryDto){
        return categoryService.save(categoryDto);
    }

    @GetMapping("/findCategoryWithParents/{id}")
    public List<String> findCategoryWithParents(@PathVariable int id){
        return categoryService.findCategoryWithParents(id);
    }


}
