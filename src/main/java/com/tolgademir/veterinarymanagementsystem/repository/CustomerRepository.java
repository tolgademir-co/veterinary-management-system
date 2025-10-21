package com.tolgademir.veterinarymanagementsystem.repository;

import com.tolgademir.veterinarymanagementsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // İsme göre filtreleme
    List<Customer> findByNameContainingIgnoreCase(String name);
}