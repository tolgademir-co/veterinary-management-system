package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.exception.ResourceNotFoundException;
import com.tolgademir.veterinarymanagementsystem.model.Animal;
import com.tolgademir.veterinarymanagementsystem.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    public List<Animal> getAll() {
        return animalRepository.findAll();
    }

    public Optional<Animal> getById(Long id) {
        return animalRepository.findById(id);
    }

    public List<Animal> searchByName(String name) {
        return animalRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Animal> getByCustomer(Long customerId) {
        return animalRepository.findByCustomerId(customerId);
    }

    public Animal create(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal update(Long id, Animal updatedAnimal) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not found with id: " + id));

        animal.setName(updatedAnimal.getName());
        animal.setSpecies(updatedAnimal.getSpecies());
        animal.setBreed(updatedAnimal.getBreed());
        animal.setGender(updatedAnimal.getGender());
        animal.setColour(updatedAnimal.getColour());
        animal.setDateOfBirth(updatedAnimal.getDateOfBirth());

        return animalRepository.save(animal);
    }

    public void delete(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Animal not found with id: " + id);
        }
        animalRepository.deleteById(id);
    }
}