package com.example.saleappv1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        String message = "Xin chào Trường Đại học Văn Hiến";

        model.addAttribute("message", message);

        return "products";
    }
}