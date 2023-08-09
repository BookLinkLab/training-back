package com.booklink.trainingback.dto;


import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long authorId;
    private Long id;
    private String title;
    private long isbn;
    private String publishDate;
    private Author author;

    public static BookDto from(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate())
                .authorId(book.getAuthor().getId())
                .author(Author.builder().id(book.getAuthor().getId()).build())
                .build();
    }




}
