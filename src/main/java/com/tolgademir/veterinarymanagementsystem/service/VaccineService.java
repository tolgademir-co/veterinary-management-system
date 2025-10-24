package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.exception.DuplicateRecordException;
import com.tolgademir.veterinarymanagementsystem.exception.ResourceNotFoundException;
import com.tolgademir.veterinarymanagementsystem.model.Vaccine;
import com.tolgademir.veterinarymanagementsystem.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;

    public List<Vaccine> getAll() {
        return vaccineRepository.findAll();
    }

    public Vaccine getById(Long id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with id: " + id));
    }

    public List<Vaccine> getByAnimalId(Long animalId) {
        return vaccineRepository.findByAnimalId(animalId);
    }

    public List<Vaccine> getByProtectionFinishDateRange(LocalDate start, LocalDate end) {
        return vaccineRepository.findByProtectionFinishDateBetween(start, end);
    }

    public Vaccine create(Vaccine vaccine) {
        boolean existsActive = vaccineRepository.existsByAnimalIdAndNameAndCodeAndProtectionFinishDateAfter(
                vaccine.getAnimal().getId(),
                vaccine.getName(),
                vaccine.getCode(),
                LocalDate.now()
        );

        if (existsActive) {
            throw new DuplicateRecordException("An active vaccine of this type already exists for the animal.");
        }

        return vaccineRepository.save(vaccine);
    }

    public Vaccine update(Long id, Vaccine updatedVaccine) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with id: " + id));

        vaccine.setName(updatedVaccine.getName());
        vaccine.setCode(updatedVaccine.getCode());
        vaccine.setProtectionStartDate(updatedVaccine.getProtectionStartDate());
        vaccine.setProtectionFinishDate(updatedVaccine.getProtectionFinishDate());
        vaccine.setAnimal(updatedVaccine.getAnimal());

        return vaccineRepository.save(vaccine);
    }

    public void delete(Long id) {
        if (!vaccineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vaccine not found with id: " + id);
        }
        vaccineRepository.deleteById(id);
    }
}