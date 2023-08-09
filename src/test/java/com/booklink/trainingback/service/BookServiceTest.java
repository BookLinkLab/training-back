package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.dto.CreateBookDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.AuthorRepository;
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
    private AuthorRepository authorRepository;

    @Test
    void happyPathTest() {
        assertTrue(bookService.getAllBooks().isEmpty());

        Author createAuthor = Author.from(CreateAuthorDto.builder()
                .name("JK Rowling")
                .nationality("British")
                .dateOfBirth("31/07/1965")
                .build());

        createAuthor = authorRepository.save(createAuthor);

        Author createAuthor1 = Author.from(CreateAuthorDto.builder()
                .name("JK Bowling")
                .nationality("American")
                .dateOfBirth("31/07/1995")
                .build());


        createAuthor1 = authorRepository.save(createAuthor1);


        CreateBookDto createBookDto = CreateBookDto.builder()
                .title("Harry potter")
                .isbn(12345678910L)
                .publishDate("12/12/1997")
                .author_id(createAuthor.getId())
                .build();


        BookDto savedBook = bookService.createBook(createBookDto);

        List<BookDto> allBooks = bookService.getAllBooks();
        assertFalse(allBooks.isEmpty());
        assertEquals(1, allBooks.size());

        BookDto myBook = allBooks.get(0);
        assertEquals(myBook, savedBook);

        CreateBookDto createBookDto1 = CreateBookDto.builder()
                .title("Harry potter 2")
                .isbn(1234567891L)
                .publishDate("12/12/2012")
                .author_id(createAuthor1.getId())
                .build();

        BookDto savedUpdatedBook = bookService.updateBook(myBook.getId(), createBookDto1);

        List<BookDto> updatedBooks = bookService.getAllBooks();
        assertFalse(updatedBooks.isEmpty());
        assertEquals(1, updatedBooks.size());

        BookDto myUpdatedBook = updatedBooks.get(0);
        assertEquals(myUpdatedBook, savedUpdatedBook);

        bookService.deleteBook(myUpdatedBook.getId());
        assertTrue(bookService.getAllBooks().isEmpty());
    }

    @Test
    void exceptionTest() {
        assertThrows(NotFoundException.class, () -> bookService.getBook(1L));
    }
}

