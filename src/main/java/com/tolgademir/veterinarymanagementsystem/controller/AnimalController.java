package com.tolgademir.veterinarymanagementsystem.controller;

import com.tolgademir.veterinarymanagementsystem.dto.AnimalDto;
import com.tolgademir.veterinarymanagementsystem.service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalController {

    private final AnimalService animalService;
    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<List<AnimalDto>> getAll() {
        return ResponseEntity.ok(animalService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AnimalDto> create(@RequestBody AnimalDto dto) {
        return ResponseEntity.ok(animalService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalDto> update(@PathVariable Long id, @RequestBody AnimalDto dto) {
        return ResponseEntity.ok(animalService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        animalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}