package com.booklink.trainingback.dto;

import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private Integer ISBN;
    private String title;
    private Date publishDate;
    private List<Author> author;

    public static BookDto from(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .ISBN(book.getISBN())
                .title(book.getTitle())
                .publishDate(book.getPublishDate())
                .author(book.getAuthor())
                .build();
    }
}
