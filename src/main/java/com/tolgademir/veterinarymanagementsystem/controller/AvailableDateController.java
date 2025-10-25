package com.tolgademir.veterinarymanagementsystem.controller;

import com.tolgademir.veterinarymanagementsystem.dto.AvailableDateDto;
import com.tolgademir.veterinarymanagementsystem.service.AvailableDateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/available-dates")
public class AvailableDateController {

    private final AvailableDateService availableDateService;

    public AvailableDateController(AvailableDateService availableDateService) {
        this.availableDateService = availableDateService;
    }

    @GetMapping
    public ResponseEntity<List<AvailableDateDto>> getAll() {
        return ResponseEntity.ok(availableDateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailableDateDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(availableDateService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AvailableDateDto> create(@RequestBody AvailableDateDto dto) {
        return ResponseEntity.ok(availableDateService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailableDateDto> update(@PathVariable Long id, @RequestBody AvailableDateDto dto) {
        return ResponseEntity.ok(availableDateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        availableDateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}