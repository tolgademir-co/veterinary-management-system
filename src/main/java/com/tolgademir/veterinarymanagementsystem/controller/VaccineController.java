package com.tolgademir.veterinarymanagementsystem.controller;

import com.tolgademir.veterinarymanagementsystem.dto.VaccineDto;
import com.tolgademir.veterinarymanagementsystem.service.VaccineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vaccines")
public class VaccineController {

    private final VaccineService vaccineService;

    public VaccineController(VaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }

    @GetMapping
    public ResponseEntity<List<VaccineDto>> getAll() {
        return ResponseEntity.ok(vaccineService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VaccineDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.getById(id));
    }

    @PostMapping
    public ResponseEntity<VaccineDto> create(@RequestBody VaccineDto dto) {
        return ResponseEntity.ok(vaccineService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VaccineDto> update(@PathVariable Long id, @RequestBody VaccineDto dto) {
        return ResponseEntity.ok(vaccineService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vaccineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}