package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.exception.DuplicateRecordException;
import com.tolgademir.veterinarymanagementsystem.exception.ResourceNotFoundException;
import com.tolgademir.veterinarymanagementsystem.model.AvailableDate;
import com.tolgademir.veterinarymanagementsystem.repository.AvailableDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableDateService {

    private final AvailableDateRepository availableDateRepository;

    public List<AvailableDate> getAll() {
        return availableDateRepository.findAll();
    }

    public List<AvailableDate> getByDoctor(Long doctorId) {
        return availableDateRepository.findByDoctorId(doctorId);
    }

    public AvailableDate create(AvailableDate availableDate) {
        boolean exists = availableDateRepository.existsByDoctorIdAndAvailableDate(
                availableDate.getDoctor().getId(),
                availableDate.getAvailableDate()
        );

        if (exists) {
            throw new DuplicateRecordException("This date is already registered for the doctor.");
        }

        return availableDateRepository.save(availableDate);
    }

    public void delete(Long id) {
        if (!availableDateRepository.existsById(id)) {
            throw new ResourceNotFoundException("Available date not found with id: " + id);
        }
        availableDateRepository.deleteById(id);
    }
}