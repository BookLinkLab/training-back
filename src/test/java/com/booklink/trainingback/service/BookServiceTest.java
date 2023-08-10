package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.author.CreateAuthorDto;
import com.booklink.trainingback.dto.book.BookDto;
import com.booklink.trainingback.dto.book.CreateBookDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
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
    void happyPathTest(){
        assertTrue(bookService.getFullBooks().isEmpty());

        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("Ray Bradbury")
                .nationality("American")
                .dateOfBirth(LocalDate.of(1902, 8, 19))
                .build();
        Author savedAuthor = authorService.createAuthor(createAuthorDto);


            CreateBookDto createBookDto = CreateBookDto.builder()
                    .title("Fahrenheit 451")
                    .isbn(9783060311354L)
                    .publishDate(LocalDate.of(1953, 10, 19))
                    .authorId(savedAuthor.getId())
                    .build();
        Book savedBook = bookService.createBook(createBookDto);

        List<Book> allBooksFull = bookService.getFullBooks();
        List<BookDto> allBooksBasic = bookService.getBasicBooks();
        assertFalse(allBooksFull.isEmpty());
        assertFalse(allBooksBasic.isEmpty());
        assertEquals(1, allBooksFull.size());
        assertEquals(1, allBooksBasic.size());

        Book myBookFull = allBooksFull.get(0);
        BookDto myBookBasic = allBooksBasic.get(0);

        assertEquals(myBookBasic.getId(), myBookFull.getId());

        assertEquals(savedBook, myBookFull);

        CreateBookDto updatedBookDto = CreateBookDto.builder()
                .title("Fahrenheit")
                .isbn(978306031135L)
                .publishDate(LocalDate.of(1953, 10, 1))
                .authorId(savedAuthor.getId())
                .build();
        Book savedUpdatedBook = bookService.modifyBook(myBookFull.getId(), updatedBookDto);

        List<Book> updatedBooks = bookService.getFullBooks();
        assertFalse(updatedBooks.isEmpty());
        assertEquals(1, updatedBooks.size());

        Book myUpdatedBook = updatedBooks.get(0);
        assertEquals(savedUpdatedBook, myUpdatedBook);
        assertNotEquals(myUpdatedBook, myBookFull);

        Book myBookIsbn = bookService.getBookByIsbn(978306031135L);
        assertEquals(savedUpdatedBook, myBookIsbn);

        bookService.deleteBook(myUpdatedBook.getId());
        assertTrue(bookService.getFullBooks().isEmpty());
        assertTrue(bookService.getBasicBooks().isEmpty());
    }

    @Test
    void exceptionTest(){
        assertThrows(NotFoundException.class, () -> bookService.getBook(1L));
    }

}
