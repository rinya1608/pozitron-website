package ru.pozitron.pbe.service;

import org.springframework.stereotype.Service;
import ru.pozitron.pbe.domain.Cart;
import ru.pozitron.pbe.domain.OrderProduct;
import ru.pozitron.pbe.domain.Product;

@Service
public class CartService {
    public static boolean productContains(Cart cart, Product product){
        if (cart.getOrderProducts().size() == 0) return false;
        return cart.getOrderProducts().
                stream().
                anyMatch(orderProduct -> orderProduct.getProduct().equals(product));
    }
    public static OrderProduct findOrderProductByProduct(Cart cart,Product product){
        return cart.getOrderProducts().
                stream().
                filter(orderProduct -> orderProduct.getProduct().equals(product)).
                findFirst().get();
    }
}
