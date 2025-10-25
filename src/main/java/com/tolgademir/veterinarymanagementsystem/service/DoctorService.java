package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.DoctorDto;
import com.tolgademir.veterinarymanagementsystem.model.Doctor;
import com.tolgademir.veterinarymanagementsystem.repository.DoctorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public DoctorService(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    public List<DoctorDto> getAll() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDto.class))
                .collect(Collectors.toList());
    }

    public DoctorDto getById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
        return modelMapper.map(doctor, DoctorDto.class);
    }

    public DoctorDto create(DoctorDto dto) {
        Doctor doctor = modelMapper.map(dto, Doctor.class);
        return modelMapper.map(doctorRepository.save(doctor), DoctorDto.class);
    }

    public DoctorDto update(Long id, DoctorDto dto) {
        Doctor existing = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setPhone(dto.getPhone());
        existing.setMail(dto.getMail());
        existing.setCity(dto.getCity());

        return modelMapper.map(doctorRepository.save(existing), DoctorDto.class);
    }

    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }
}