package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.domain.Product;
import ru.pozitron.pbe.repository.CategoryRepository;
import ru.pozitron.pbe.repository.ProductRepository;
import ru.pozitron.pbe.service.ProductService;

import java.math.BigDecimal;

@Controller
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;
    @Value("${upload.path}")
    private String uploadPath;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    @GetMapping("products/{category}")
    public String viewProductList(@PathVariable Category category,
                              Model model,
                              @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC,size = 3) Pageable pageable){
        Page<Product> page;
        page = productRepository.findByCategory(category,pageable);
        model.addAttribute("page",page);
        model.addAttribute("category",category);
        model.addAttribute("url","products/");
        return "productList";
    }
    @GetMapping("product/{category}/{product}")
    public String viewProductPage(@PathVariable Product product,Model model){
        model.addAttribute("product",product);
        return "productPage";
    }

    @GetMapping("/product/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String productAdd(Model model){
        Iterable<Product> products = productRepository.findAll();
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        return "productAdd";
    }
    @PostMapping("/product/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String productSave(@RequestParam String productName,
                              @RequestParam(required = false) String description,
                              @RequestParam BigDecimal price,
                              @RequestParam(required = false) Integer discountPercent,
                              @RequestParam Integer quantity,
                              @RequestParam String unit,
                              @RequestParam("categoryId")  Category category,
                              @RequestParam("file") MultipartFile file,
                              Model model){

        Product product = new Product(productName,description,price,quantity,unit,category);
        if (!file.isEmpty()) {
            product.setFilename(productService.uploadFile(file,uploadPath));
        }
        if (discountPercent != null && discountPercent != 0){
            product.setDiscountPercent(discountPercent);
            product.setPriceWithDiscount(productService.calculatePriceWithDiscount(price,discountPercent));
        }
        productRepository.save(product);
        Iterable<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories",categories);
        return "productAdd";
    }
}
