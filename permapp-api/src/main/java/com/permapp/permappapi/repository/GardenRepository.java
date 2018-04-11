package com.permapp.permappapi.repository;

import com.permapp.permappapi.entity.Garden;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GardenRepository extends JpaRepository<Garden, Long> {

  Optional<Garden> findByName(String name);

  @Query("SELECT g FROM Garden g "
      + " WHERE g.name = ?1 "
      + " AND g.id != ?2 ")
  Optional<Garden> findByNameExceptId(String name, Long id);
}
