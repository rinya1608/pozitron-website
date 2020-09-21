package ru.pozitron.pbe.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.domain.Product;
import ru.pozitron.pbe.repository.CategoryRepository;
import ru.pozitron.pbe.repository.ProductRepository;
import ru.pozitron.pbe.service.ProductService;

import java.util.ArrayList;

@Controller
@ControllerAdvice
public class MainController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    public MainController(CategoryRepository categoryRepository, ProductRepository productRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @ModelAttribute("mainCategories")
    public Iterable<Category> Category(){
        return categoryRepository.findAllByParentCategory(null);
    }

    @GetMapping("/search")
    public String viewSearchList(@RequestParam(required = false) String productName,
                                 Model model,
                                 @PageableDefault(sort = {"name"},direction = Sort.Direction.DESC,size = 3) Pageable pageable){
        Page<Product> page;
        page = productRepository.findByNameLike("%" + productName + "%",pageable);
        model.addAttribute("page",page);
        model.addAttribute("url","/search");
        return "SearchList";
    }
    @PostMapping("search")
    public String searchProduct(@RequestParam String name){
        return "redirect:/search?productName=" + name;
    }

    @GetMapping("/")
    public String main(Model model){
        ArrayList<Product> products = productService.productsByDiscountWithLimit(productRepository.findAllByDiscountPercentNotNull(),9);
        model.addAttribute("products",products);
        return "index";
    }
}
