package com.example.saleappv1.repository;

import com.example.saleappv1.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {
} 