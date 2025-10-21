package com.tolgademir.veterinarymanagementsystem.repository;

import com.tolgademir.veterinarymanagementsystem.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    // Hayvana göre filtreleme
    List<Vaccine> findByAnimalId(Long animalId);

    // Koruyuculuk bitiş tarihine göre filtreleme
    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);

    // Aynı tür aşı var mı kontrolü
    boolean existsByAnimalIdAndNameAndCodeAndProtectionFinishDateAfter(Long animalId, String name, String code, LocalDate currentDate);
}