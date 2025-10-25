package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.AvailableDateDto;
import com.tolgademir.veterinarymanagementsystem.model.AvailableDate;
import com.tolgademir.veterinarymanagementsystem.model.Doctor;
import com.tolgademir.veterinarymanagementsystem.repository.AvailableDateRepository;
import com.tolgademir.veterinarymanagementsystem.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvailableDateService {

    private final AvailableDateRepository availableDateRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public AvailableDateService(AvailableDateRepository availableDateRepository,
                                DoctorRepository doctorRepository,
                                ModelMapper modelMapper) {
        this.availableDateRepository = availableDateRepository;
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    public List<AvailableDateDto> getAll() {
        return availableDateRepository.findAll()
                .stream()
                .map(date -> modelMapper.map(date, AvailableDateDto.class))
                .collect(Collectors.toList());
    }

    public AvailableDateDto getById(Long id) {
        AvailableDate availableDate = availableDateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Available date not found with ID: " + id));
        return modelMapper.map(availableDate, AvailableDateDto.class);
    }

    public AvailableDateDto create(AvailableDateDto dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        AvailableDate availableDate = new AvailableDate();
        availableDate.setAvailableDate(dto.getAvailableDate());
        availableDate.setDoctor(doctor);

        return modelMapper.map(availableDateRepository.save(availableDate), AvailableDateDto.class);
    }

    public AvailableDateDto update(Long id, AvailableDateDto dto) {
        AvailableDate existing = availableDateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Available date not found with ID: " + id));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));

        existing.setAvailableDate(dto.getAvailableDate());
        existing.setDoctor(doctor);

        return modelMapper.map(availableDateRepository.save(existing), AvailableDateDto.class);
    }

    public void delete(Long id) {
        availableDateRepository.deleteById(id);
    }
}