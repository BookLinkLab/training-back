package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.author.CreateAuthorDto;
import com.booklink.trainingback.dto.book.BookDto;
import com.booklink.trainingback.dto.book.CreateBookDto;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/book";

    @BeforeEach
    void setup(){
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("Ray Bradbury")
                .nationality("American")
                .dateOfBirth(LocalDate.of(1902, 8, 19))
                .build();
        String authorUrl = "/author";
        restTemplate.postForEntity(authorUrl, createAuthorDto, Author.class);

        CreateBookDto createBookDto = CreateBookDto.builder()
                .title("Fahrenheit 451")
                .isbn(9783060311354L)
                .publishDate(LocalDate.of(1953, 10, 19))
                .authorId(1L)
                .build();
        restTemplate.postForEntity(baseUrl, createBookDto, Book.class);
    }

    @Test
    void createBook(){
        CreateBookDto createBookDto = CreateBookDto.builder()
                .title("Fahrenheit 451")
                .isbn(9783060311354L)
                .publishDate(LocalDate.of(1953, 10, 19))
                .authorId(1L)
                .build();
        ResponseEntity<Book> response = restTemplate.exchange(
                baseUrl, HttpMethod.POST, new HttpEntity<>(createBookDto), Book.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    void getBook(){
        ResponseEntity<Book> response = restTemplate.exchange(
                baseUrl + "/1", HttpMethod.GET, null, Book.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void getAllBooks(){
        ResponseEntity<List<Book>> response = restTemplate.exchange(
                baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseEntity<List<Book>> responseFull = restTemplate.exchange(
                baseUrl + "?template=full", HttpMethod.GET, null, new ParameterizedTypeReference<List<Book>>() {}
        );
        assertEquals(HttpStatus.OK, responseFull.getStatusCode());

        ResponseEntity<List<BookDto>> responseBasic = restTemplate.exchange(
                baseUrl + "?template=basic", HttpMethod.GET, null, new ParameterizedTypeReference<List<BookDto>>() {}
        );
        assertEquals(HttpStatus.OK, responseBasic.getStatusCode());

        ResponseEntity<?> responseBadRequest = restTemplate.exchange(
                baseUrl + "?template=none", HttpMethod.GET, null, new ParameterizedTypeReference<>() {}
        );
        assertEquals(HttpStatus.BAD_REQUEST, responseBadRequest.getStatusCode());
    }

    @Test
    void deleteBook(){
        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/1", HttpMethod.DELETE, null, Void.class
        );
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void modifyBook(){
        CreateBookDto modifyBookDto = CreateBookDto.builder()
                .title("Fahrenheit")
                .isbn(9783060311354L)
                .publishDate(LocalDate.of(1960, 10, 19))
                .authorId(1L)
                .build();
        ResponseEntity<Book> response = restTemplate.exchange(
                baseUrl + "/1", HttpMethod.PUT, new HttpEntity<>(modifyBookDto), Book.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBookByIsbn(){
        ResponseEntity<Book> response = restTemplate.exchange(
                baseUrl + "/isbn/9783060311354", HttpMethod.GET, null, Book.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
