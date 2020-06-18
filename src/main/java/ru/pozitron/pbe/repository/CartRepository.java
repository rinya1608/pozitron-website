package ru.pozitron.pbe.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pozitron.pbe.domain.Cart;

public interface CartRepository extends CrudRepository<Cart, Long> {

}
