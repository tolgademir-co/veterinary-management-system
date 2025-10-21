package com.tolgademir.veterinarymanagementsystem.service;

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

    // Tüm aşı kayıtlarını getir
    public List<Vaccine> getAll() {
        return vaccineRepository.findAll();
    }

    // ID ile getir
    public Vaccine getById(Long id) {
        return vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with id: " + id));
    }

    // Belirli bir hayvana ait aşı kayıtlarını getir
    public List<Vaccine> getByAnimalId(Long animalId) {
        return vaccineRepository.findByAnimalId(animalId);
    }

    // Tarih aralığına göre koruyuculuk bitiş tarihine göre filtreleme
    public List<Vaccine> getByProtectionFinishDateRange(LocalDate start, LocalDate end) {
        return vaccineRepository.findByProtectionFinishDateBetween(start, end);
    }

    // Yeni aşı kaydet (koruyuculuk kontrolü ile)
    public Vaccine create(Vaccine vaccine) {

        boolean existsActive = vaccineRepository.existsByAnimalIdAndNameAndCodeAndProtectionFinishDateAfter(
                vaccine.getAnimal().getId(),
                vaccine.getName(),
                vaccine.getCode(),
                LocalDate.now()
        );

        if (existsActive) {
            throw new RuntimeException("This vaccine is still active for the animal. Cannot add a new one.");
        }

        return vaccineRepository.save(vaccine);
    }

    // Güncelle
    public Vaccine update(Long id, Vaccine updatedVaccine) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with id: " + id));

        vaccine.setName(updatedVaccine.getName());
        vaccine.setCode(updatedVaccine.getCode());
        vaccine.setProtectionStartDate(updatedVaccine.getProtectionStartDate());
        vaccine.setProtectionFinishDate(updatedVaccine.getProtectionFinishDate());
        vaccine.setAnimal(updatedVaccine.getAnimal());

        return vaccineRepository.save(vaccine);
    }

    // Sil
    public void delete(Long id) {
        if (!vaccineRepository.existsById(id)) {
            throw new RuntimeException("Vaccine not found with id: " + id);
        }
        vaccineRepository.deleteById(id);
    }
}