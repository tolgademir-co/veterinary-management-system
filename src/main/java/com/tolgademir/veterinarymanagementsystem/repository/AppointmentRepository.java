package com.tolgademir.veterinarymanagementsystem.repository;

import com.tolgademir.veterinarymanagementsystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Doktora ve tarih aralığına göre filtreleme
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    // Hayvana ve tarih aralığına göre filtreleme
    List<Appointment> findByAnimalIdAndAppointmentDateBetween(Long animalId, LocalDateTime start, LocalDateTime end);

    // Doktorun o saatte başka randevusu var mı?
    boolean existsByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime dateTime);
}