package com.booklink.trainingback.dto.book;

import com.booklink.trainingback.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String title;
    private Long isbn;
    private LocalDate publishDate;
    private Long authorId;

    public static BookDto from(Book book){
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate())
                .authorId(book.getAuthor().getId())
                .build();
    }
}
