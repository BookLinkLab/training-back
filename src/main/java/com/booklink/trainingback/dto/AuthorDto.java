package com.booklink.trainingback.dto;

import com.booklink.trainingback.model.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {
    private Long id;
    private String name;
    private String nationality;
    private String dateOfBirth;

    public static AuthorDto from(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .nationality(author.getNationality())
                .dateOfBirth(author.getDateOfBirth())
                .build();
    }
}
