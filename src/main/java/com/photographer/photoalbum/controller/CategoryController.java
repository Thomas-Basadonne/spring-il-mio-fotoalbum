package com.photographer.photoalbum.controller;

import com.photographer.photoalbum.model.Category;
import com.photographer.photoalbum.model.Photo;
import com.photographer.photoalbum.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("categoryObj", new Category());
        return "categories/index";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Integer categoryId, Model model) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("categoryObj", category.get());
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "categories/index";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("categoryObj") Category category, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Category> categoryList = categoryRepository.findAll();
            model.addAttribute("categoryList", categoryList);
            return "categories/index";
        }
        categoryRepository.save(category);
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Category category = categoryOptional.get();
        for (Photo photo : category.getPhotos()) {
            photo.getCategories().remove(category);
        }
        categoryRepository.deleteById(id);
        return "redirect:/categories";
    }
}
