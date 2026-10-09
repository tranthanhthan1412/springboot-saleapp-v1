package com.example.saleappv1.controller;

import com.example.saleappv1.models.Product;
import com.example.saleappv1.service.CategoryService;
import com.example.saleappv1.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(
            ProductService productService,
            CategoryService categoryService) {

        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute(
                "products",
                productService.getAllProducts()
        );

        return "products/products-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());

        loadCategories(model);

        return "products/add-product";
    }

    @PostMapping("/add")
    public String addProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        checkCategory(product, result);

        if (result.hasErrors()) {
            loadCategories(model);

            return "products/add-product";
        }

        productService.addProduct(product);

        redirectAttributes.addFlashAttribute(
                "success",
                "Thêm sản phẩm thành công!"
        );

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "product",
                productService.getProductById(id)
        );

        loadCategories(model);

        return "products/update-product";
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        product.setId(id);

        checkCategory(product, result);

        if (result.hasErrors()) {
            loadCategories(model);

            return "products/update-product";
        }

        productService.updateProduct(id, product);

        redirectAttributes.addFlashAttribute(
                "success",
                "Cập nhật sản phẩm thành công!"
        );

        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        productService.deleteProductById(id);

        redirectAttributes.addFlashAttribute(
                "success",
                "Xóa sản phẩm thành công!"
        );

        return "redirect:/products";
    }

    private void loadCategories(Model model) {
        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );
    }

    private void checkCategory(
            Product product,
            BindingResult result) {

        if (result.hasFieldErrors("category")
                || result.hasFieldErrors("category.id")) {
            return;
        }

        if (product.getCategory() == null
                || product.getCategory().getId() == null) {

            result.rejectValue(
                    "category",
                    "category.required",
                    "Vui lòng chọn danh mục"
            );

            return;
        }

        if (!categoryService.existsById(
                product.getCategory().getId())) {

            result.rejectValue(
                    "category",
                    "category.invalid",
                    "Danh mục không tồn tại"
            );
        }
    }
}