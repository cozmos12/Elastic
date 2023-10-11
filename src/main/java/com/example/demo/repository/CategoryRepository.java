package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;


public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.id = :categoryId")
    int getProductCountByCategoryId(@Param("categoryId") int categoryId);

}
