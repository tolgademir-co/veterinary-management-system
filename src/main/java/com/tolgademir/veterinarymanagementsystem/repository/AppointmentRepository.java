package com.tolgademir.veterinarymanagementsystem.repository;

import com.tolgademir.veterinarymanagementsystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // ------------------------------------------------------------
    // 📅 Doktora ve tarih aralığına göre filtreleme
    // Belirli bir doktorun belirtilen tarih aralığındaki randevularını getirir
    // ------------------------------------------------------------
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    // ------------------------------------------------------------
    // 🐾 Hayvana ve tarih aralığına göre filtreleme
    // Belirli bir hayvanın belirtilen tarih aralığındaki randevularını getirir
    // ------------------------------------------------------------
    List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, LocalDateTime start, LocalDateTime end);

    // ------------------------------------------------------------
    // ⛔ Doktorun o tarih ve saatte başka randevusu var mı?
    // Randevu çakışma kontrolü için kullanılır
    // ------------------------------------------------------------
    boolean existsByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);
}