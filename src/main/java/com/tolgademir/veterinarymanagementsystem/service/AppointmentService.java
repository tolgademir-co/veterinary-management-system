package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.AppointmentDto;
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

    public List<AppointmentDto> getAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(app -> modelMapper.map(app, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    public AppointmentDto getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    public AppointmentDto create(AppointmentDto dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal not found with ID: " + dto.getAnimalId()));

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setDoctor(doctor);
        appointment.setAnimal(animal);

        return modelMapper.map(appointmentRepository.save(appointment), AppointmentDto.class);
    }

    public AppointmentDto update(Long id, AppointmentDto dto) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

        existing.setAppointmentDate(dto.getAppointmentDate());

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal not found with ID: " + dto.getAnimalId()));

        existing.setDoctor(doctor);
        existing.setAnimal(animal);

        return modelMapper.map(appointmentRepository.save(existing), AppointmentDto.class);
    }

    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }
}