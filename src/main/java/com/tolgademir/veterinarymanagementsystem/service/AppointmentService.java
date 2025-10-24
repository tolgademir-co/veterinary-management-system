package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.exception.BusinessRuleException;
import com.tolgademir.veterinarymanagementsystem.exception.ResourceNotFoundException;
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

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }

    public Appointment create(Appointment appointment) {
        Long doctorId = appointment.getDoctor().getId();
        LocalDate appointmentDay = appointment.getAppointmentDate().toLocalDate();
        LocalDateTime appointmentDateTime = appointment.getAppointmentDate();

        // Doktor o gün çalışıyor mu?
        boolean isAvailableDay = availableDateRepository
                .existsByDoctorIdAndAvailableDate(doctorId, appointmentDay);
        if (!isAvailableDay) {
            throw new BusinessRuleException("Doctor is not available on this date.");
        }

        // Aynı saatte başka randevu var mı?
        boolean hasConflict = appointmentRepository
                .existsByDoctorIdAndAppointmentDate(doctorId, appointmentDateTime);
        if (hasConflict) {
            throw new BusinessRuleException("Doctor already has an appointment at this time.");
        }

        // Uygunluk sağlandı → kayıt oluştur
        return appointmentRepository.save(appointment);
    }

    public Appointment update(Long id, Appointment updatedAppointment) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));

        appointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        appointment.setDoctor(updatedAppointment.getDoctor());
        appointment.setAnimal(updatedAppointment.getAnimal());

        return appointmentRepository.save(appointment);
    }

    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getByDoctorAndDateRange(Long doctorId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByDoctorIdAndAppointmentDateBetween(doctorId, start, end);
    }

    public List<Appointment> getByAnimalAndDateRange(Long animalId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByAnimalIdAndAppointmentDateBetween(animalId, start, end);
    }
}