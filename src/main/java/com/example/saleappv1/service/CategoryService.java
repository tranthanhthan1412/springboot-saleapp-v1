package com.example.saleappv1.service;

import java.util.List;

import com.example.saleappv1.models.Category;
import com.example.saleappv1.repository.CategoryRepository;
import com.example.saleappv1.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(
            CategoryRepository categoryRepository,
            ProductRepository productRepository) {

        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Không tìm thấy danh mục có ID: " + id
                ));
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return id != null && categoryRepository.existsById(id);
    }

    public void addCategory(Category category) {
        category.setId(null);
        category.setName(category.getName().trim());

        categoryRepository.save(category);
    }

    public void updateCategory(Long id, Category category) {
        Category existingCategory = getCategoryById(id);

        existingCategory.setName(category.getName().trim());

        categoryRepository.save(existingCategory);
    }

    public void deleteCategoryById(Long id) {
        Category category = getCategoryById(id);

        if (productRepository.existsByCategory_Id(id)) {
            throw new IllegalStateException(
                    "Danh mục đang có sản phẩm. Hãy xóa hoặc chuyển sản phẩm sang danh mục khác trước."
            );
        }

        categoryRepository.delete(category);
        categoryRepository.flush();
    }
}