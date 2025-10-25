package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.VaccineDto;
import com.tolgademir.veterinarymanagementsystem.model.Animal;
import com.tolgademir.veterinarymanagementsystem.model.Vaccine;
import com.tolgademir.veterinarymanagementsystem.repository.AnimalRepository;
import com.tolgademir.veterinarymanagementsystem.repository.VaccineRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineService {

    private final VaccineRepository vaccineRepository;
    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;

    public VaccineService(VaccineRepository vaccineRepository,
                          AnimalRepository animalRepository,
                          ModelMapper modelMapper) {
        this.vaccineRepository = vaccineRepository;
        this.animalRepository = animalRepository;
        this.modelMapper = modelMapper;
    }

    public List<VaccineDto> getAll() {
        return vaccineRepository.findAll()
                .stream()
                .map(vaccine -> modelMapper.map(vaccine, VaccineDto.class))
                .collect(Collectors.toList());
    }

    public VaccineDto getById(Long id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with ID: " + id));
        return modelMapper.map(vaccine, VaccineDto.class);
    }

    public VaccineDto create(VaccineDto dto) {
        // 🔹 1. İlişkili hayvanı bul
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new RuntimeException("Animal not found with ID: " + dto.getAnimalId()));

        // 🔹 2. Vaccine nesnesini oluştur ve ilişkilendir
        Vaccine vaccine = new Vaccine();
        vaccine.setCode(dto.getCode());
        vaccine.setName(dto.getName());
        vaccine.setProtectionStartDate(dto.getProtectionStartDate());
        vaccine.setProtectionFinishDate(dto.getProtectionFinishDate());
        vaccine.setAnimal(animal); // 🔥 ilişki burada kuruldu

        // 🔹 3. Kaydet ve DTO olarak döndür
        return modelMapper.map(vaccineRepository.save(vaccine), VaccineDto.class);
    }

    public VaccineDto update(Long id, VaccineDto dto) {
        Vaccine existing = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found with ID: " + id));

        existing.setCode(dto.getCode());
        existing.setName(dto.getName());
        existing.setProtectionStartDate(dto.getProtectionStartDate());
        existing.setProtectionFinishDate(dto.getProtectionFinishDate());

        if (dto.getAnimalId() != null) {
            Animal animal = animalRepository.findById(dto.getAnimalId())
                    .orElseThrow(() -> new RuntimeException("Animal not found with ID: " + dto.getAnimalId()));
            existing.setAnimal(animal);
        }

        return modelMapper.map(vaccineRepository.save(existing), VaccineDto.class);
    }

    public void delete(Long id) {
        vaccineRepository.deleteById(id);
    }
}