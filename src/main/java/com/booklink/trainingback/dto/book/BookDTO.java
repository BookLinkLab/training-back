package com.booklink.trainingback.dto.book;

import com.booklink.trainingback.dto.author.AuthorDTO;
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
public class BookDTO {
    private Long id;
    private String title;
    private String ISBN;
    private Date publicationDate;
    private AuthorDTO author;

    public static BookDTO from(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .ISBN(book.getISBN())
                .publicationDate(book.getPublicationDate())
                .author(AuthorDTO.from(book.getAuthor()))
                .build();
    }

}
