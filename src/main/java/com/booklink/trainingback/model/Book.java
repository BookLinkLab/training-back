package com.booklink.trainingback.model;

import com.booklink.trainingback.dto.CreateBookDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private long isbn;

    private String publishDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public static Book from(CreateBookDto dto) {
        return Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .publishDate(dto.getPublishDate())
                .build();
    }





}
