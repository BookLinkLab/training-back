package com.booklink.trainingback.model;


import com.booklink.trainingback.dto.CreateAuthorDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String nationality;

    private String dateOfBirth;


    public static Author from(CreateAuthorDto dto) {
        return Author.builder()
                .name(dto.getName())
                .nationality(dto.getNationality())
                .dateOfBirth(dto.getDateOfBirth())
                .build();
    }

}
