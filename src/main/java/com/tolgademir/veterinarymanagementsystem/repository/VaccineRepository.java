package com.tolgademir.veterinarymanagementsystem.repository;

import com.tolgademir.veterinarymanagementsystem.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {

    // ------------------------------------------------------------
    // 🐾 Hayvana göre filtreleme
    // Belirli bir hayvana ait tüm aşıları getirir
    // ------------------------------------------------------------
    List<Vaccine> findByAnimalId(Long animalId);

    // ------------------------------------------------------------
    // 📅 Koruyuculuk bitiş tarihine göre filtreleme
    // Belirtilen tarih aralığında biten koruyuculukları getirir
    // ------------------------------------------------------------
    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);

    // ------------------------------------------------------------
    // 🔎 Aynı tür ve koda sahip aşı var mı kontrolü
    // (İsteğe bağlı genel kontrol, örnek: isim + kod bazlı)
    // ------------------------------------------------------------
    boolean existsByAnimalIdAndNameAndCodeAndProtectionFinishDateAfter(
            Long animalId,
            String name,
            String code,
            LocalDate currentDate
    );

    // ------------------------------------------------------------
    // 🚫 Koruyuculuk kontrolü (iş kuralı)
    // Aynı isimli aşının koruyuculuk süresi bitmeden tekrar eklenmesini engeller
    // ------------------------------------------------------------
    boolean existsByAnimalIdAndNameAndProtectionFinishDateAfter(
            Long animalId,
            String name,
            LocalDate protectionStartDate
    );
}