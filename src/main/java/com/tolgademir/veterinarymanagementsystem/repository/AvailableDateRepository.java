package com.tolgademir.veterinarymanagementsystem.repository;

import com.tolgademir.veterinarymanagementsystem.model.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {

    // Doktora göre tarih listesi
    List<AvailableDate> findByDoctorId(Long doctorId);

    // Doktor + tarih kontrolü (müsait günü var mı?)
    boolean existsByDoctorIdAndAvailableDate(Long doctorId, LocalDate date);
}