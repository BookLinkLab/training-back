package com.booklink.trainingback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAuthorDto {
    private String name;
    private String nationality;
    private String dateOfBirth;
}
