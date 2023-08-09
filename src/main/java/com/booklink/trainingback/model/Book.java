package com.booklink.trainingback.model;

import com.booklink.trainingback.dto.book.CreateBookDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @Column(unique = true)
    private String ISBN;

    private Date publicationDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public static Book from(CreateBookDTO bookDTO) {
        return Book.builder()
                .title(bookDTO.getTitle())
                .ISBN(bookDTO.getISBN())
                .publicationDate(bookDTO.getPublicationDate())
                .build();
    }

}
