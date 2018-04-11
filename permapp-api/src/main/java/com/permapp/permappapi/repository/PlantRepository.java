package com.permapp.permappapi.repository;

import com.permapp.permappapi.entity.Plant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlantRepository extends JpaRepository<Plant, Long> {

  Optional<Plant> findByName(String name);

  @Query("SELECT p FROM Plant p "
      + " WHERE p.name = ?1 "
      + " AND p.id != ?2 ")
  Optional<Plant> findByNameExceptId(String name, Long id);
}
