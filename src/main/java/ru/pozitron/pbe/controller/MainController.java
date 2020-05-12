package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.repository.CategoryRepository;

@Controller
public class MainController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/")
    public String viewCategory(Model model){
        Iterable<Category> mainCategories = categoryRepository.findAllByParentCategory(null);
        model.addAttribute("mainCategories",mainCategories);
        return "index";
    }


}
