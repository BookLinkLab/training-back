package com.booklink.trainingback.dto;

import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookWithAuthorIdDTO {
    private Long id;
    private Long isbn;
    private String title;
    private String publishDate;
    private List<Long> authorIds;

    public static BookWithAuthorIdDTO from(Book book) {
        return BookWithAuthorIdDTO.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .publishDate(book.getPublishDate())
                .authorIds(book.getAuthors().stream()
                        .map(Author::getId)
                        .toList())
                .build();
    }
}
