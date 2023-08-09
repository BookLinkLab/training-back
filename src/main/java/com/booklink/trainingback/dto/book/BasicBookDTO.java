package com.booklink.trainingback.dto.book;

import com.booklink.trainingback.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicBookDTO {
    private Long id;
    private String title;
    private String ISBN;
    private Date publicationDate;
    private Long authorId;

    public static BasicBookDTO from(Book book) {
        return BasicBookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .ISBN(book.getISBN())
                .publicationDate(book.getPublicationDate())
                .authorId(book.getAuthor().getId())
                .build();
    }
}

