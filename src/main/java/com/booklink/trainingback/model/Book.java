package com.booklink.trainingback.model;

import com.booklink.trainingback.dto.CreateBookDto;
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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long isbn;

    private String title;

    private String publishDate;

    @ManyToMany
    private List<Author> authors;

    public static Book from(CreateBookDto bookDto, List<Author> authors) {
        return Book.builder()
                .isbn(bookDto.getIsbn())
                .title(bookDto.getTitle())
                .publishDate(bookDto.getPublishDate())
                .authors(authors)
                .build();
    }
}
