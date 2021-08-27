package com.example.eshopweb.restrepository;

import com.example.eshopweb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = true)
public interface RestCategoryRepository extends JpaRepository<Category, Integer> {
}
