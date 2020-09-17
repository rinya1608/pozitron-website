package ru.pozitron.pbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pozitron.pbe.domain.*;
import ru.pozitron.pbe.repository.*;
import ru.pozitron.pbe.service.CartService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CartController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ClientInfoRepository clientInfoRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private CartService cartService;

    @GetMapping("/cart")
    public String ProductList(@AuthenticationPrincipal User user, Model model){
        user = userRepository.getOne(user.getId());
        Cart cart = user.getCartInProcess();

        if (cart != null){
            model.addAttribute("cart",cart);
        }

        else model.addAttribute("messageCartIsEmpty","В корзине нету товаров");
        return "cartPage";
    }
    @PostMapping(value={"/","/cart/add"})
    @ResponseBody
    public ResponseEntity<?> addProductToCart(@AuthenticationPrincipal User user,
                                              @RequestBody ProductCriteria productCriteria
                                              ){
        user = userRepository.getOne(user.getId());
        Cart cart;
        OrderProduct orderProduct;
        Product product = productRepository.getOne(productCriteria.getId());

        if (user.getCartInProcess() == null){
            cart = new Cart(LocalDateTime.now());
            cart.setUser(user);
            user.getCarts().add(cart);
            cartRepository.save(cart);
        }

        cart = user.getCartInProcess();

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
        return ResponseEntity.ok(user.getCartInProcess().getCostAllProducts());
    }
    @DeleteMapping("/cart/del/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteProductFromCart(@AuthenticationPrincipal User user,@PathVariable Long id){
        user = userRepository.getOne(user.getId());
        Cart cart = user.getCartInProcess();
        cart.getOrderProducts().removeIf(orderProduct -> orderProduct.getId().equals(id));
        cartRepository.save(cart);
        return ResponseEntity.ok(cart.getCostAllProducts());
    }
    @GetMapping("/cart/registration")
    public String viewOrderPage(@AuthenticationPrincipal User user,Model model){
        model.addAttribute("user",user);
        return "orderPage";
    }
    @PostMapping("/cart/registration")
    public String registrationCart(@AuthenticationPrincipal User user,
                                   @Valid ClientInfo clientInfo,
                                   BindingResult bindingResult,
                                   Model model){

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("validErrors",errors.values());
            model.addAttribute("user",user);
            return "orderPage";
        }
        user = userRepository.getOne(user.getId());
        Cart cart = user.getCartInProcess();
        cart.setClientInfo(clientInfo);
        cart.setStatus(CartStatus.NOT_CONFIRMED);
        clientInfoRepository.save(clientInfo);
        cartRepository.save(cart);
        return "redirect:/";
    }
    @GetMapping("/orders")
    public String viewUserOrderList(@AuthenticationPrincipal User user,Model model){
        ArrayList<Cart> carts = cartRepository.findByUserAndStatusIn(user,Arrays.asList(CartStatus.NOT_CONFIRMED,
                CartStatus.CONFIRMED,
                CartStatus.CANCELED
        ));
        model.addAttribute("carts",carts);
        return "userOrderList";
    }
    @GetMapping("/admin/carts")
    public String viewAdminOrderList(Model model){
        ArrayList<Cart> carts = cartRepository.findByStatusIn(Arrays.asList(CartStatus.NOT_CONFIRMED,
                CartStatus.CONFIRMED,
                CartStatus.CANCELED
                ));
        model.addAttribute("carts",carts);
        return "adminOrderList";
    }

    @PostMapping("admin/carts/confirm")
    public ResponseEntity<?> confirmOrder(@RequestBody CartCriteria cartCriteria){
        Cart cart = cartRepository.getOne(cartCriteria.getId());
        cart.setStatus(CartStatus.CONFIRMED);

        Map<Product, Integer> productAndQuantityMap = cart.getOrderProducts()
                .stream()
                .collect(Collectors.toMap(OrderProduct::getProduct, OrderProduct::getQuantity));

        for (Map.Entry<Product, Integer> entry : productAndQuantityMap.entrySet()) {
            entry.getKey().setQuantity(entry.getKey().getQuantity() - entry.getValue());
        }

        productRepository.saveAll(productAndQuantityMap.keySet());
        cartRepository.save(cart);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("admin/carts/del/{cartId}")
    public ResponseEntity<?> delOrder(@PathVariable Long cartId){
        cartRepository.deleteById(cartId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("admin/carts/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody CartCriteria cartCriteria){
        Cart cart = cartRepository.getOne(cartCriteria.getId());
        cart.setStatus(CartStatus.CANCELED);

        Map<Product, Integer> productAndQuantityMap = cart.getOrderProducts()
                .stream()
                .collect(Collectors.toMap(OrderProduct::getProduct, OrderProduct::getQuantity));

        for (Map.Entry<Product, Integer> entry : productAndQuantityMap.entrySet()) {
            entry.getKey().setQuantity(entry.getKey().getQuantity() + entry.getValue());
        }

        productRepository.saveAll(productAndQuantityMap.keySet());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
