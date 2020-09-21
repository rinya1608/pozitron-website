package ru.pozitron.pbe.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.repository.CategoryRepository;

@Controller
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String categoryList(Model model){
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "categoryList";
    }
    @PostMapping("/category/edit")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addCategory(@RequestParam(required = false) String childCategoryName,
                              @RequestParam(required = false,value = "categoryId") Category parentCategory,
                              @RequestParam(required = false) String categoryName,
                              Model model)
    {
        Category category;
        if (childCategoryName != null){
            category = new Category(childCategoryName,parentCategory);
            categoryRepository.save(category);
        }
        else if (categoryName != null){
            category = new Category(categoryName);
            categoryRepository.save(category);
        }

        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "categoryList";
    }

    @GetMapping("category/{parentCategory}")
    public String subcategoryList(@PathVariable Category parentCategory, Model model){
        Iterable<Category> categories = categoryRepository.findAllByParentCategory(parentCategory);
        model.addAttribute("categories",categories);
        model.addAttribute("parentCategory",parentCategory);
        return "subcategoryList";
    }
}
