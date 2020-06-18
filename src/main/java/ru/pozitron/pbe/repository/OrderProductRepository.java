package ru.pozitron.pbe.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pozitron.pbe.domain.OrderProduct;

public interface OrderProductRepository extends CrudRepository<OrderProduct, Long> {
}
