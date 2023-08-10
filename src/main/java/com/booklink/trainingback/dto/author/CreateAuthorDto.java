package com.booklink.trainingback.dto.author;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAuthorDto {
    private String name;
    private String nationality;
    private LocalDate dateOfBirth;
}
