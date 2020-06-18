package ru.pozitron.pbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.domain.Product;

import org.springframework.data.domain.Pageable;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);
    Product findByName(String name);
    Page<Product> findAll(Pageable pageable);
}
