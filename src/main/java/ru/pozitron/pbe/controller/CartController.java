package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pozitron.pbe.domain.*;
import ru.pozitron.pbe.repository.CartRepository;
import ru.pozitron.pbe.repository.OrderProductRepository;
import ru.pozitron.pbe.repository.ProductRepository;
import ru.pozitron.pbe.repository.UserRepository;
import ru.pozitron.pbe.service.CartService;

import java.time.LocalDateTime;

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
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String ProductList(@AuthenticationPrincipal User user, Model model){

        if (user.getCart() != null){
            model.addAttribute("cart",userRepository.findByUsername(user.getUsername()).getCart());
        }
        else model.addAttribute("messageCartIsEmpty","В корзине нету товаров");
        return "cartPage";
    }
    @PostMapping("/cart/add")
    @ResponseBody
    public ResponseEntity<?> addProductToCart(@AuthenticationPrincipal User user,
                                              @RequestBody ProductCriteria productCriteria
                                              ){
        Cart cart;
        OrderProduct orderProduct;
        Product product = productRepository.getOne(productCriteria.getId());

        if (user.getCart() == null){
            cart = new Cart(LocalDateTime.now());
            cart.setUser(user);
            user.setCart(cart);
            cartRepository.save(cart);
        }

        cart = cartRepository.findByUser(user);

        if (cart.getOrderProducts().size() == 0 || !cartService.productContains(cart,product)){
            System.out.println(!cartService.productContains(cart,product));
            orderProduct = new OrderProduct(product,productCriteria.getQuantity());
            cart.getOrderProducts().add(orderProduct);
            orderProductRepository.save(orderProduct);
        }
        else {
            orderProduct = cartService.findOrderProductByProduct(cart,product);
            orderProduct.setQuantity(productCriteria.getQuantity());
        }





        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/cart/change")
    @ResponseBody
    public ResponseEntity<?> changeProductQuantityInCart(@AuthenticationPrincipal User user,
                                                         @RequestBody ProductCriteria productCriteria){
        user = userRepository.getOne(user.getId());

        OrderProduct orderProduct = orderProductRepository.getOne(productCriteria.getId());
        orderProduct.setQuantity(productCriteria.getQuantity());
        orderProductRepository.save(orderProduct);
        return ResponseEntity.ok(user.getCart().getCostAllProducts());
    }
    @DeleteMapping("/cart/del/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProductFromCart(@AuthenticationPrincipal User user,@PathVariable Long id){
        Cart cart = cartRepository.findByUser(user);
        cart.getOrderProducts().removeIf(orderProduct -> orderProduct.getId().equals(id));
        cartRepository.save(cart);
        return ResponseEntity.ok(cart.getCostAllProducts());
    }
}
