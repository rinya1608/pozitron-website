package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.pozitron.pbe.domain.*;
import ru.pozitron.pbe.repository.CartRepository;
import ru.pozitron.pbe.repository.OrderProductRepository;
import ru.pozitron.pbe.repository.ProductRepository;
import ru.pozitron.pbe.repository.UserRepository;
import ru.pozitron.pbe.service.CartService;

import java.time.LocalDateTime;
import java.util.Set;

@Controller
public class CartController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    @GetMapping("/cart")
    public String cartProductList(@AuthenticationPrincipal User user, Model model){
        Set<OrderProduct> products;
        if (user.getCart() != null){
            products = user.getCart().getOrderProducts();
            model.addAttribute("orderProducts",products);
        }
        else model.addAttribute("messageCartIsEmpty","В корзине нету товаров");
        return "cartPage";
    }
    @PostMapping(value = {"/products/{category}","/product/{category}/{product}"})
    @ResponseBody
    public ResponseEntity<?> addProductToCart(@AuthenticationPrincipal User user,
                                              @RequestBody ProductCriteria productCriteria
                                              ){
        Product product = productRepository.findByName(productCriteria.getName());
        Cart cart;
        OrderProduct orderProduct;

        if (user.getCart() == null){
            cart = new Cart(LocalDateTime.now());
            user.setCart(cart);
        }
        else cart = user.getCart();
        if (!CartService.productContains(cart,product)){
            orderProduct = new OrderProduct(product,productCriteria.getQuantity());
            cart.getOrderProducts().add(orderProduct);

        }
        else {
            orderProduct = CartService.findOrderProductByProduct(cart,product);
            orderProduct.setQuantity(productCriteria.getQuantity());
        }


        orderProductRepository.save(orderProduct);
        cartRepository.save(cart);
        userRepository.save(user);
        //return "redirect:/products/"+category;
        return ResponseEntity.ok("добавлено");
    }
}
