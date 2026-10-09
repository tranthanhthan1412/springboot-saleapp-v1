package com.example.saleappv1.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 200, message = "Tên sản phẩm tối đa 200 ký tự")
    @Column(nullable = false, length = 200)
    private String name;

    @NotNull(message = "Vui lòng nhập giá")
    @DecimalMin(value = "0.0", message = "Giá không được âm")
    @Digits(
        integer = 13,
        fraction = 2,
        message = "Giá tối đa 13 chữ số và 2 số thập phân"
    )
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Size(max = 2000, message = "Mô tả tối đa 2000 ký tự")
    @Column(length = 2000)
    private String description;

    @NotNull(message = "Vui lòng chọn danh mục")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}