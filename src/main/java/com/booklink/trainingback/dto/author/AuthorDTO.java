package com.booklink.trainingback.dto.author;

import com.booklink.trainingback.model.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {
    private Long id;
    private String name;
    private String nationality;
    private Date dateOfBirth;

    public static AuthorDTO from(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .dateOfBirth(author.getDateOfBirth())
                .nationality(author.getNationality())
                .build();
    }

}
