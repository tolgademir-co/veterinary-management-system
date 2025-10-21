package com.tolgademir.veterinarymanagementsystem.controller;

import com.tolgademir.veterinarymanagementsystem.model.AvailableDate;
import com.tolgademir.veterinarymanagementsystem.service.AvailableDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/available-dates")
@RequiredArgsConstructor
public class AvailableDateController {

    private final AvailableDateService availableDateService;

    @GetMapping
    public ResponseEntity<List<AvailableDate>> getAll() {
        return ResponseEntity.ok(availableDateService.getAll());
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AvailableDate>> getByDoctor(@PathVariable Long doctorId) {
        return ResponseEntity.ok(availableDateService.getByDoctor(doctorId));
    }

    @PostMapping
    public ResponseEntity<AvailableDate> create(@RequestBody AvailableDate availableDate) {
        return ResponseEntity.ok(availableDateService.create(availableDate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        availableDateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}