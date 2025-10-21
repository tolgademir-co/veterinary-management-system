package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.model.Appointment;
import com.tolgademir.veterinarymanagementsystem.repository.AppointmentRepository;
import com.tolgademir.veterinarymanagementsystem.repository.AvailableDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailableDateRepository availableDateRepository;

    // Tüm randevuları getir
    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    // ID ile getir
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
    }

    // Randevu oluştur (iş kuralları dahil)
    public Appointment create(Appointment appointment) {

        Long doctorId = appointment.getDoctor().getId();
        LocalDate appointmentDay = appointment.getAppointmentDate().toLocalDate();
        LocalDateTime appointmentDateTime = appointment.getAppointmentDate();

        // 1️⃣ Doktor o gün çalışıyor mu kontrolü
        boolean isAvailableDay = availableDateRepository
                .existsByDoctorIdAndAvailableDate(doctorId, appointmentDay);

        if (!isAvailableDay) {
            throw new RuntimeException("Doctor is not available on this date.");
        }

        // 2️⃣ Aynı saatte başka randevusu var mı kontrolü
        boolean hasConflict = appointmentRepository
                .existsByDoctorIdAndAppointmentDate(doctorId, appointmentDateTime);

        if (hasConflict) {
            throw new RuntimeException("Doctor already has an appointment at this time.");
        }

        // 3️⃣ Her şey uygunsa randevuyu kaydet
        return appointmentRepository.save(appointment);
    }

    // Güncelle
    public Appointment update(Long id, Appointment updatedAppointment) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        appointment.setDoctor(updatedAppointment.getDoctor());
        appointment.setAnimal(updatedAppointment.getAnimal());

        return appointmentRepository.save(appointment);
    }

    // Sil
    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    // Doktora göre tarih aralığı filtreleme
    public List<Appointment> getByDoctorAndDateRange(Long doctorId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    // Hayvana göre tarih aralığı filtreleme
    public List<Appointment> getByAnimalAndDateRange(Long animalId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAnimalIdAndAppointmentDateBetween(animalId, start, end);
    }
}