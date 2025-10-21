package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.model.Doctor;
import com.tolgademir.veterinarymanagementsystem.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    // Tüm doktorları getir
    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    // ID ile doktor getir
    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    // Yeni doktor oluştur
    public Doctor create(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Güncelleme
    public Doctor update(Long id, Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        doctor.setName(updatedDoctor.getName());
        doctor.setPhone(updatedDoctor.getPhone());
        doctor.setMail(updatedDoctor.getMail());
        doctor.setAddress(updatedDoctor.getAddress());
        doctor.setCity(updatedDoctor.getCity());

        return doctorRepository.save(doctor);
    }

    // Silme
    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found with id: " + id);
        }
        doctorRepository.deleteById(id);
    }
}