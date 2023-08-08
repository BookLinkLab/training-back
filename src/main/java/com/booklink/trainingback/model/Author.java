package com.booklink.trainingback.model;

import com.booklink.trainingback.dto.author.CreateAuthorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
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

    private Date dateOfBirth;

    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();


    public static Author from(CreateAuthorDTO authorDTO) {
        return Author.builder()
                .name(authorDTO.getName())
                .dateOfBirth(authorDTO.getDateOfBirth())
                .nationality(authorDTO.getNationality())
                .build();
    }
}

