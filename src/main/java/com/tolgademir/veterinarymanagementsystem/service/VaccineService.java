package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.VaccineDto;
import com.tolgademir.veterinarymanagementsystem.exception.BusinessRuleException;
import com.tolgademir.veterinarymanagementsystem.exception.DuplicateRecordException;
import com.tolgademir.veterinarymanagementsystem.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with ID: " + id));
        return modelMapper.map(vaccine, VaccineDto.class);
    }

    // ------------------------------------------------------------
    // CREATE VACCINE (with protection period and duplicate check)
    // ------------------------------------------------------------
    public VaccineDto create(VaccineDto dto) {
        Animal animal = animalRepository.findById(dto.getAnimalId())
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with ID: " + dto.getAnimalId()));

        // Aynı kodlu aşı zaten varsa hata fırlat
        boolean isDuplicate = vaccineRepository.existsByAnimalIdAndNameAndCodeAndProtectionFinishDateAfter(
                dto.getAnimalId(), dto.getName(), dto.getCode(), dto.getProtectionStartDate());
        if (isDuplicate) {
            throw new DuplicateRecordException("Bu aşı kodu veya adıyla koruyuculuğu devam eden bir kayıt zaten mevcut!");
        }

        // Koruyuculuk kontrolü
        boolean isProtected = vaccineRepository.existsByAnimalIdAndNameAndProtectionFinishDateAfter(
                dto.getAnimalId(), dto.getName(), dto.getProtectionStartDate());
        if (isProtected) {
            throw new BusinessRuleException("Bu hayvan için aynı isimli aşının koruyuculuk süresi hâlâ devam ediyor!");
        }

        Vaccine vaccine = new Vaccine();
        vaccine.setCode(dto.getCode());
        vaccine.setName(dto.getName());
        vaccine.setProtectionStartDate(dto.getProtectionStartDate());
        vaccine.setProtectionFinishDate(dto.getProtectionFinishDate());
        vaccine.setAnimal(animal);

        return modelMapper.map(vaccineRepository.save(vaccine), VaccineDto.class);
    }

    public VaccineDto update(Long id, VaccineDto dto) {
        Vaccine existing = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with ID: " + id));

        existing.setCode(dto.getCode());
        existing.setName(dto.getName());
        existing.setProtectionStartDate(dto.getProtectionStartDate());
        existing.setProtectionFinishDate(dto.getProtectionFinishDate());

        if (dto.getAnimalId() != null) {
            Animal animal = animalRepository.findById(dto.getAnimalId())
                    .orElseThrow(() -> new ResourceNotFoundException("Animal not found with ID: " + dto.getAnimalId()));
            existing.setAnimal(animal);
        }

        return modelMapper.map(vaccineRepository.save(existing), VaccineDto.class);
    }

    public void delete(Long id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vaccine not found with ID: " + id));
        vaccineRepository.delete(vaccine);
    }
}