package com.tolgademir.veterinarymanagementsystem.repository;

import com.tolgademir.veterinarymanagementsystem.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    // İsme göre filtreleme
    List<Animal> findByNameContainingIgnoreCase(String name);

    // Sahip ID'sine göre hayvanları listeleme
    List<Animal> findByCustomerId(Long customerId);
}