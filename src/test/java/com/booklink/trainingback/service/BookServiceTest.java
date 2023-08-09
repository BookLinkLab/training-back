package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.author.AuthorDTO;
import com.booklink.trainingback.dto.author.CreateAuthorDTO;
import com.booklink.trainingback.dto.book.BookDTO;
import com.booklink.trainingback.dto.book.BookType;
import com.booklink.trainingback.dto.book.CreateBookDTO;
import com.booklink.trainingback.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

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
    @Transactional
    void BookTests() {
        assertTrue(bookService.getAllBooks().isEmpty());

        CreateBookDTO newBook = new CreateBookDTO(
                "The Lord of the Rings",
                "123456",
                new Date(1982,10,11),
                102L
        );

        assertThrows(NotFoundException.class, () -> bookService.create(newBook));

        CreateAuthorDTO newAuthor = new CreateAuthorDTO(
                "J.R.R. Tolkien",
                "British",
                new Date(1892, 1,3)
        );

        AuthorDTO authorDTO = authorService.create(newAuthor);

        newBook.setAuthorId(authorDTO.getId());
        BookDTO bookDTO = bookService.create(newBook);
        assertEquals(1, bookService.getAllBooks().size());
        BookDTO bookDTO1 = bookService.getBook(bookDTO.getId());
        assertEquals("123456", bookDTO1.getISBN());

        bookService.updateBook(new BookDTO(
                bookDTO1.getId(),
                "The Lord of the Rings",
                "99",
                new Date(1900,1,1),
                authorDTO
        ));

        BookDTO bookDTO2 = bookService.getBook(bookDTO1.getId());
        assertEquals("99", bookDTO2.getISBN());

        BookDTO bookDTO3 = bookService.getBookByIsbn("99");
        assertEquals("99", bookDTO3.getISBN());

        bookService.deleteBook(bookDTO2.getId());
        assertTrue(bookService.getAllBooks().isEmpty());

    }

}
