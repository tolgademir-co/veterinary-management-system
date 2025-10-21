package com.tolgademir.veterinarymanagementsystem.service;

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

    // Tüm müsait günleri getir
    public List<AvailableDate> getAll() {
        return availableDateRepository.findAll();
    }

    // Doktora göre müsait günleri getir
    public List<AvailableDate> getByDoctor(Long doctorId) {
        return availableDateRepository.findByDoctorId(doctorId);
    }

    // Yeni müsait gün ekle
    public AvailableDate create(AvailableDate availableDate) {
        boolean exists = availableDateRepository.existsByDoctorIdAndAvailableDate(
                availableDate.getDoctor().getId(),
                availableDate.getAvailableDate()
        );
        if (exists) {
            throw new RuntimeException("This date already exists for the doctor.");
        }
        return availableDateRepository.save(availableDate);
    }

    // Silme
    public void delete(Long id) {
        if (!availableDateRepository.existsById(id)) {
            throw new RuntimeException("Available date not found with id: " + id);
        }
        availableDateRepository.deleteById(id);
    }
}