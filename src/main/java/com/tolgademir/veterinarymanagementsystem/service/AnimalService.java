package com.tolgademir.veterinarymanagementsystem.service;

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

    // Tüm hayvanları listele
    public List<Animal> getAll() {
        return animalRepository.findAll();
    }

    // ID’ye göre hayvan getir
    public Optional<Animal> getById(Long id) {
        return animalRepository.findById(id);
    }

    // İsme göre filtreleme
    public List<Animal> searchByName(String name) {
        return animalRepository.findByNameContainingIgnoreCase(name);
    }

    // Müşteri ID’sine göre hayvan listeleme
    public List<Animal> getByCustomer(Long customerId) {
        return animalRepository.findByCustomerId(customerId);
    }

    // Yeni hayvan ekle
    public Animal create(Animal animal) {
        return animalRepository.save(animal);
    }

    // Güncelle
    public Animal update(Long id, Animal updatedAnimal) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found with id: " + id));
        animal.setName(updatedAnimal.getName());
        animal.setSpecies(updatedAnimal.getSpecies());
        animal.setBreed(updatedAnimal.getBreed());
        animal.setGender(updatedAnimal.getGender());
        animal.setColour(updatedAnimal.getColour());
        animal.setDateOfBirth(updatedAnimal.getDateOfBirth());
        return animalRepository.save(animal);
    }

    // Sil
    public void delete(Long id) {
        if (!animalRepository.existsById(id)) {
            throw new RuntimeException("Animal not found with id: " + id);
        }
        animalRepository.deleteById(id);
    }
}