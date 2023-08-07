package com.booklink.trainingback.model;

import com.booklink.trainingback.dto.CreateAuthorDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    List<Book> books;

    public static Author from(CreateAuthorDto createAuthorDto) {
        return Author.builder()
                .name(createAuthorDto.getName())
                .nationality(createAuthorDto.getNationality())
                .dateOfBirth(createAuthorDto.getDateOfBirth())
                .books(new ArrayList<>())
                .build();
    }
}
