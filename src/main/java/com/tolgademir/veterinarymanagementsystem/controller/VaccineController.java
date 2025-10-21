package com.tolgademir.veterinarymanagementsystem.controller;

import com.tolgademir.veterinarymanagementsystem.model.Vaccine;
import com.tolgademir.veterinarymanagementsystem.service.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    @GetMapping
    public ResponseEntity<List<Vaccine>> getAll() {
        return ResponseEntity.ok(vaccineService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaccine> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.getById(id));
    }

    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<Vaccine>> getByAnimalId(@PathVariable Long animalId) {
        return ResponseEntity.ok(vaccineService.getByAnimalId(animalId));
    }

    @GetMapping("/protection")
    public ResponseEntity<List<Vaccine>> getByProtectionFinishDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {
        return ResponseEntity.ok(vaccineService.getByProtectionFinishDateRange(start, end));
    }

    @PostMapping
    public ResponseEntity<Vaccine> create(@RequestBody Vaccine vaccine) {
        return ResponseEntity.ok(vaccineService.create(vaccine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vaccine> update(@PathVariable Long id, @RequestBody Vaccine vaccine) {
        return ResponseEntity.ok(vaccineService.update(id, vaccine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vaccineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}