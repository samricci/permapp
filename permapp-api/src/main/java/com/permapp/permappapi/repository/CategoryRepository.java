package com.permapp.permappapi.repository;

import com.permapp.permappapi.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByName(String name);

  @Query("SELECT c FROM Category c "
      + " WHERE c.name = ?1 "
      + " AND c.id != ?2 ")
  Optional<Category> findByNameExceptId(String name, Long id);
}
