package com.booklink.trainingback.dto;

import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private Long isbn;
    private String title;
    private String publishDate;
    private List<Author> authors;

    public static BookDto from(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .publishDate(book.getPublishDate())
                .authors(book.getAuthors())
                .build();
    }
}
