package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.AppointmentDto;
import com.tolgademir.veterinarymanagementsystem.exception.BusinessRuleException;
import com.tolgademir.veterinarymanagementsystem.exception.ResourceNotFoundException;
import com.tolgademir.veterinarymanagementsystem.model.Animal;
import com.tolgademir.veterinarymanagementsystem.model.Appointment;
import com.tolgademir.veterinarymanagementsystem.model.Doctor;
import com.tolgademir.veterinarymanagementsystem.repository.AnimalRepository;
import com.tolgademir.veterinarymanagementsystem.repository.AppointmentRepository;
import com.tolgademir.veterinarymanagementsystem.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository,
                              AnimalRepository animalRepository,
                              ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.animalRepository = animalRepository;
        this.modelMapper = modelMapper;
    }

    // ------------------------------------------------------------
    // GET ALL APPOINTMENTS
    // ------------------------------------------------------------
    public List<AppointmentDto> getAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(app -> modelMapper.map(app, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    // ------------------------------------------------------------
    // GET APPOINTMENT BY ID
    // ------------------------------------------------------------
    public AppointmentDto getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    // ------------------------------------------------------------
    // CREATE APPOINTMENT (with conflict check)
    // ------------------------------------------------------------
    public AppointmentDto create(AppointmentDto dto) {

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + dto.getDoctorId()));

        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with ID: " + dto.getAnimalId()));

        // Randevu çakışma kontrolü
        boolean isConflict = appointmentRepository.existsByDoctorIdAndAppointmentDate(
                dto.getDoctorId(), dto.getAppointmentDate());
        if (isConflict) {
            throw new BusinessRuleException("Bu doktorun bu tarih ve saatte başka bir randevusu bulunmaktadır!");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);

        return modelMapper.map(appointmentRepository.save(appointment), AppointmentDto.class);
    }

    // ------------------------------------------------------------
    // UPDATE APPOINTMENT
    // ------------------------------------------------------------
    public AppointmentDto update(Long id, AppointmentDto dto) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + dto.getDoctorId()));

        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with ID: " + dto.getAnimalId()));

        boolean isConflict = appointmentRepository.existsByDoctorIdAndAppointmentDate(
                dto.getDoctorId(), dto.getAppointmentDate());
        if (isConflict && !existing.getId().equals(id)) {
            throw new BusinessRuleException("Bu doktorun bu tarih ve saatte başka bir randevusu bulunmaktadır!");
        }

        existing.setAppointmentDate(dto.getAppointmentDate());
        existing.setDoctor(doctor);
        existing.setAnimal(animal);

        return modelMapper.map(appointmentRepository.save(existing), AppointmentDto.class);
    }

    // ------------------------------------------------------------
    // DELETE APPOINTMENT
    // ------------------------------------------------------------
    public void delete(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + id));
        appointmentRepository.delete(appointment);
    }
}