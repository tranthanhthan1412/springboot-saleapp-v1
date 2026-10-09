package com.example.saleappv1.service;

import java.util.List;

import com.example.saleappv1.models.Category;
import com.example.saleappv1.models.Product;
import com.example.saleappv1.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(
            ProductRepository productRepository,
            CategoryService categoryService) {

        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Không tìm thấy sản phẩm có ID: " + id
                ));
    }

    public void addProduct(Product product) {
        Category category = categoryService.getCategoryById(
                product.getCategory().getId()
        );

        product.setId(null);
        product.setName(product.getName().trim());
        product.setCategory(category);

        productRepository.save(product);
    }

    public void updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);

        Category category = categoryService.getCategoryById(
                product.getCategory().getId()
        );

        existingProduct.setName(product.getName().trim());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(category);

        productRepository.save(existingProduct);
    }

    public void deleteProductById(Long id) {
        Product product = getProductById(id);

        productRepository.delete(product);
    }
}