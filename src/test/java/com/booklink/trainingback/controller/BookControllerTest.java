package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.dto.CreateBookDto;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.BookRepository;
import com.booklink.trainingback.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private BookRepository bookRepository;

    private final String baseUrl = "/book";
    private final String authorBaseUrl = "/author";




    @BeforeEach
    void setup() {
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("JK Rowling")
                .nationality("British")
                .dateOfBirth("31/07/1965")
                .build();
        restTemplate.postForEntity(authorBaseUrl, createAuthorDto, AuthorDto.class);

        CreateBookDto createBookDto = CreateBookDto.builder()
                .title("Harry Potter")
                .isbn(1234567890L)
                .publishDate("10/06/1997")
                .author_id(1L)
                .build();
        restTemplate.postForEntity(baseUrl, createBookDto, BookDto.class);
    }


    @Test
    void getAllBooks() {
        ResponseEntity<List<BookDto>> response = restTemplate.exchange(
                baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<BookDto>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<BookDto> books = response.getBody();
        assertNotNull(books);
        assertEquals(1, books.size());
    }

    @Test
    void getBookById() {
        Long bookId = 1L;
        ResponseEntity<BookDto> response = restTemplate.getForEntity(
                baseUrl + "/" + bookId, BookDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        BookDto book = response.getBody();
        assertEquals(bookId, book.getId());
        assertNotNull(book.getAuthor());
    }

    @Test
    void createBook() {
        CreateBookDto createBookDto = CreateBookDto.builder()
                .title("Harry Potter")
                .isbn(1234567890L)
                .publishDate("10/06/1997")
                .author_id(1L)
                .build();

        ResponseEntity<BookDto> response = restTemplate.postForEntity(
                baseUrl, createBookDto, BookDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Harry Potter", response.getBody().getTitle());
        assertEquals(1234567890L, response.getBody().getIsbn());
        assertEquals("10/06/1997", response.getBody().getPublishDate());
        assertEquals(1L, response.getBody().getAuthorId());
    }

    @Test
    void bookId(){
        Book book = Book.builder()
                .title("Harry potter 2")
                .isbn(1234567891L)
                .publishDate("12/12/2012")
                .build();

        bookRepository.save(book);

        assertNotNull(book.getId());
    }

    @Test
    void findBookByIsbn() {
        Long isbnToFind = 1234567890L;

        ResponseEntity<BookDto> response = restTemplate.getForEntity(
                baseUrl + "/isbn/" + isbnToFind, BookDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(isbnToFind, response.getBody().getIsbn());
    }


    @Test
    void deleteBook() {
        Long bookIdToDelete = 1L;

        restTemplate.delete(baseUrl + "/" + bookIdToDelete);

        ResponseEntity<BookDto> response = restTemplate.getForEntity(
                baseUrl + "/" + bookIdToDelete, BookDto.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void updateBook(){
        CreateBookDto modifyBookDto = CreateBookDto.builder()
                .title("Harry potter 2")
                .isbn(1234567891L)
                .publishDate("12/12/2012")
                .author_id(1L)
                .build();
        ResponseEntity<Book> response = restTemplate.exchange(
                baseUrl + "/1", HttpMethod.PUT, new HttpEntity<>(modifyBookDto), Book.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }





}