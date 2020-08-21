package ru.pozitron.pbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.pozitron.pbe.domain.Cart;
import ru.pozitron.pbe.domain.OrderProduct;
import ru.pozitron.pbe.domain.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}
