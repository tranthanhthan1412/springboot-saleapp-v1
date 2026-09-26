package com.example.saleappv1.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.example.saleappv1.model.Product;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class ProductService {

    private final ObjectMapper objectMapper;

    public ProductService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Product> getProducts() {
        try {
            InputStream inputStream = new ClassPathResource("data/products.json").getInputStream();

            return objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Product>>() {}
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}