package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.domain.Product;
import ru.pozitron.pbe.repository.CategoryRepository;
import ru.pozitron.pbe.repository.ProductRepository;

import org.springframework.data.domain.Pageable;

@Controller
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("products/{category}")
    public String productList(@PathVariable Category category,
                              Model model,
                              @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC,size = 1) Pageable pageable){
        Page<Product> page;
        page = productRepository.findByCategory(category,pageable);
        Iterable<Category> mainCategories = categoryRepository.findAllByParentCategory(null);
        model.addAttribute("mainCategories",mainCategories);
        model.addAttribute("page",page);
        model.addAttribute("category",category);
        model.addAttribute("url","products/");
        return "productList";
    }

    @GetMapping("product/add")
    public String productAdd(Model model){
        Iterable<Product> products = productRepository.findAll();
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        return "productAdd";
    }
    @PostMapping("product/add")
    public String productSave(@RequestParam String productName,
                             @RequestParam String description,
                             @RequestParam Double price,
                             @RequestParam Double count,
                             @RequestParam String unit,
                             @RequestParam("categoryId")  Category category,
                              Model model){

        Product product = new Product(productName,description,price,count,unit,category);
        productRepository.save(product);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "productAdd";
    }
}
