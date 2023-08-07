package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.dto.CreateBookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;

    @Test
    void happyPathTest() {
        //create author first
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("George R. R. Martin")
                .nationality("United States")
                .dateOfBirth("1948/10/20")
                .build();
        AuthorDto savedAuthor = this.authorService.createAuthor(createAuthorDto);

        assertTrue(this.bookService.getAllBooksFull().isEmpty());
        CreateBookDto createBookDto = CreateBookDto.builder()
                .isbn(9781338878950L)
                .title("Harry Potter and The Goblet of Fire")
                .publishDate("08/07/2000")
                .authorsId(List.of(savedAuthor.getId()))
                .build();
        BookDto savedBook = this.bookService.createBook(createBookDto);

        List<BookDto> allBooks = this.bookService.getAllBooksFull();
        assertFalse(allBooks.isEmpty());
        assertEquals(1, allBooks.size());

        BookDto myBook = allBooks.get(0);

        assertEquals(myBook.getIsbn(), savedBook.getIsbn());
        assertEquals(myBook.getTitle(), savedBook.getTitle());
        assertEquals(myBook.getPublishDate(), savedBook.getPublishDate());
        assertEquals(myBook.getAuthors().get(0).getId(), savedBook.getAuthors().get(0).getId());

        CreateBookDto updatedBookData = CreateBookDto.builder()
                .isbn(999L)
                .title("Harry Potter and The Goblet of Fire")
                .publishDate("08/07/2000")
                .authorsId(List.of(savedAuthor.getId()))
                .build();
        this.bookService.updateBook(myBook.getId(), updatedBookData);
        assertEquals(this.bookService.getAllBooksFull().get(0).getIsbn(), 999L);

        this.bookService.deleteBook(1L);
        assertTrue(this.bookService.getAllBooksFull().isEmpty());
    }

}
