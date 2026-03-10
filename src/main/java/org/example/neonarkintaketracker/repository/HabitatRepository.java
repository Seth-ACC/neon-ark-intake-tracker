// src/main/java/org/example/neonarkintaketracker/repository/HabitatRepository.java
package org.example.neonarkintaketracker.repository;

import org.example.neonarkintaketracker.entity.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {
    // You get findById, findAll, save, delete, etc. automatically
}