package com.tolgademir.veterinarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "vaccines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // Aşı adı
    private String code;  // Aşı kodu
    private LocalDate protectionStartDate;  // Koruyuculuk başlangıç tarihi
    private LocalDate protectionFinishDate; // Koruyuculuk bitiş tarihi

    // Bir aşı bir hayvana aittir (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;
}