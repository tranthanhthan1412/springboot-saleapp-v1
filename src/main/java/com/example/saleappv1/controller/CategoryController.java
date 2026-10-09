package com.example.saleappv1.controller;

import com.example.saleappv1.models.Category;
import com.example.saleappv1.service.CategoryService;

import jakarta.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
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
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute(
                "categories",
                categoryService.getAllCategories()
        );

        return "categories/categories-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());

        return "categories/add-category";
    }

    @PostMapping("/add")
    public String addCategory(
            @Valid @ModelAttribute("category") Category category,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "categories/add-category";
        }

        categoryService.addCategory(category);

        redirectAttributes.addFlashAttribute(
                "success",
                "Thêm danh mục thành công!"
        );

        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "category",
                categoryService.getCategoryById(id)
        );

        return "categories/update-category";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(
            @PathVariable Long id,
            @Valid @ModelAttribute("category") Category category,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        category.setId(id);

        if (result.hasErrors()) {
            return "categories/update-category";
        }

        categoryService.updateCategory(id, category);

        redirectAttributes.addFlashAttribute(
                "success",
                "Cập nhật danh mục thành công!"
        );

        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            categoryService.deleteCategoryById(id);

            redirectAttributes.addFlashAttribute(
                    "success",
                    "Xóa danh mục thành công!"
            );

        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    e.getMessage()
            );

        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "Không thể xóa danh mục đang được sử dụng."
            );
        }

        return "redirect:/categories";
    }
}