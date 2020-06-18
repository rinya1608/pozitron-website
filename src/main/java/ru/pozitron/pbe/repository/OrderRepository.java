package ru.pozitron.pbe.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pozitron.pbe.domain.Order;


public interface OrderRepository extends CrudRepository<Order, Long> {
    Iterable<Order> findAllById(Iterable<Long> iterable);
}
