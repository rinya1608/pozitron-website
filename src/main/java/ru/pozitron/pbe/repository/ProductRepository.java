package ru.pozitron.pbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.pozitron.pbe.domain.Category;
import ru.pozitron.pbe.domain.Product;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory(Category category, Pageable pageable);
    Page<Product> findByNameLike(String name, Pageable pageable);
    ArrayList<Product> findAllByDiscountPercentNotNull();
    Page<Product> findAll(Pageable pageable);
}
