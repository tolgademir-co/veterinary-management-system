package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.AnimalDto;
import com.tolgademir.veterinarymanagementsystem.model.Animal;
import com.tolgademir.veterinarymanagementsystem.model.Customer;
import com.tolgademir.veterinarymanagementsystem.repository.AnimalRepository;
import com.tolgademir.veterinarymanagementsystem.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public AnimalService(AnimalRepository animalRepository,
                         CustomerRepository customerRepository,
                         ModelMapper modelMapper) {
        this.animalRepository = animalRepository;
        this.customerRepository = customerRepository;
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
        // 🔹 1. Customer'ı ID ile bul
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + dto.getCustomerId()));

        // 🔹 2. Animal nesnesini oluştur ve Customer ile ilişkilendir
        Animal animal = new Animal();
        animal.setName(dto.getName());
        animal.setSpecies(dto.getSpecies());
        animal.setBreed(dto.getBreed());
        animal.setGender(dto.getGender());
        animal.setColour(dto.getColour());
        animal.setDateOfBirth(dto.getDateOfBirth());
        animal.setCustomer(customer);

        // 🔹 3. Kaydet ve DTO olarak döndür
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

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + dto.getCustomerId()));
            existing.setCustomer(customer);
        }

        return modelMapper.map(animalRepository.save(existing), AnimalDto.class);
    }

    public void delete(Long id) {
        animalRepository.deleteById(id);
    }
}