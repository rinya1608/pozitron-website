package ru.pozitron.pbe.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pozitron.pbe.domain.Category;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    Iterable<Category> findAllByParentCategory(Category parentCategory);
}
