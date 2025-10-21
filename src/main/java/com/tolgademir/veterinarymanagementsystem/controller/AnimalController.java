package com.tolgademir.veterinarymanagementsystem.controller;

import com.tolgademir.veterinarymanagementsystem.model.Animal;
import com.tolgademir.veterinarymanagementsystem.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping
    public ResponseEntity<List<Animal>> getAll() {
        return ResponseEntity.ok(animalService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getById(id).orElse(null));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Animal>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(animalService.searchByName(name));
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<Animal>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(animalService.getByCustomer(customerId));
    }

    @PostMapping
    public ResponseEntity<Animal> create(@RequestBody Animal animal) {
        return ResponseEntity.ok(animalService.create(animal));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Animal> update(@PathVariable Long id, @RequestBody Animal animal) {
        return ResponseEntity.ok(animalService.update(id, animal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}