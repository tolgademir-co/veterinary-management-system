package com.tolgademir.veterinarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDateDto {
    private Long id;
    private LocalDate availableDate;
    private Long doctorId;
}