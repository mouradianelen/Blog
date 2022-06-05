package com.example.blog.repository;

import com.example.blog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByTitle(String title);

    @Query(value = "select * from blog.category b where b.title=:title",
            nativeQuery = true)
    Category findByTitle(String title);
}
