package org.klozevitz.phat_store_mvc_java_311.controllers;

import lombok.RequiredArgsConstructor;
import org.klozevitz.phat_store_mvc_java_311.dao.services.CategoryService;
import org.klozevitz.phat_store_mvc_java_311.model.entities.stock.dto.CategoryDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ViewController {
    private final CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        List<CategoryDTO> categories = categoryService.findAll().stream()
                .map(category -> CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .toList();

        model.addAttribute("categories", categories);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "ui/pages/index";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "ui/pages/login";
    }
    @GetMapping("/registration")
    public String registrationPage() {
        return "ui/pages/registration";
    }
}
