package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.repository.CategoryRepository;

@Controller
@ControllerAdvice
public class MainController {
    @Autowired
    CategoryRepository categoryRepository;
    @ModelAttribute("mainCategories")
    public Iterable<Category> Category(){
        return categoryRepository.findAllByParentCategory(null);
    }

    @GetMapping("/")
    public String main(){
        return "index";
    }
}
