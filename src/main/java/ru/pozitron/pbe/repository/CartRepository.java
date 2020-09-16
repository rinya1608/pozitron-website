package ru.pozitron.pbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pozitron.pbe.domain.Cart;
import ru.pozitron.pbe.domain.CartStatus;
import ru.pozitron.pbe.domain.User;

import java.util.ArrayList;
import java.util.Collection;


public interface CartRepository extends JpaRepository<Cart, Long> {
    ArrayList<Cart> findByStatusIn(Collection<CartStatus> status);
    ArrayList<Cart> findByUserAndStatusIn(User user, Collection<CartStatus> status);
}
