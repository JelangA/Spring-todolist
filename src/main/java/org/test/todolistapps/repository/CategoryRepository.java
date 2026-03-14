package org.test.todolistapps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.todolistapps.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
