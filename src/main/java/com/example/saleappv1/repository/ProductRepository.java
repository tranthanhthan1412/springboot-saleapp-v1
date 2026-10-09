package com.example.saleappv1.repository;

import com.example.saleappv1.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

    boolean existsByCategory_Id(Long categoryId);
}