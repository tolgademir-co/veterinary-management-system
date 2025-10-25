package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.AnimalDto;
import com.tolgademir.veterinarymanagementsystem.model.Animal;
import com.tolgademir.veterinarymanagementsystem.repository.AnimalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;

    public AnimalService(AnimalRepository animalRepository, ModelMapper modelMapper) {
        this.animalRepository = animalRepository;
        this.modelMapper = modelMapper;
    }

    public List<AnimalDto> getAll() {
        return animalRepository.findAll()
                .stream()
                .map(animal -> modelMapper.map(animal, AnimalDto.class))
                .collect(Collectors.toList());
    }

    public AnimalDto getById(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found with ID: " + id));
        return modelMapper.map(animal, AnimalDto.class);
    }

    public AnimalDto create(AnimalDto dto) {
        Animal animal = modelMapper.map(dto, Animal.class);
        return modelMapper.map(animalRepository.save(animal), AnimalDto.class);
    }

    public AnimalDto update(Long id, AnimalDto dto) {
        Animal existing = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found with ID: " + id));

        existing.setName(dto.getName());
        existing.setSpecies(dto.getSpecies());
        existing.setBreed(dto.getBreed());
        existing.setGender(dto.getGender());
        existing.setColour(dto.getColour());
        existing.setDateOfBirth(dto.getDateOfBirth());

        return modelMapper.map(animalRepository.save(existing), AnimalDto.class);
    }

    public void delete(Long id) {
        animalRepository.deleteById(id);
    }
}