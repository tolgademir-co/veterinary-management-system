package com.tolgademir.veterinarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDate;

    // Bir randevu bir hayvana aittir
    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    // Bir randevu bir doktora aittir
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
}