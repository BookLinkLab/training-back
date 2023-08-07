package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.*;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/book";

    @BeforeEach
    void setup() {
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("George R. R. Martin")
                .nationality("United States")
                .dateOfBirth("1948/10/20")
                .build();
        String authorUrl = "/author";
        this.restTemplate.postForEntity(authorUrl, createAuthorDto, AuthorDto.class);

        CreateBookDto createBookDto = CreateBookDto.builder()
                .isbn(9781338878950L)
                .title("Harry Potter and The Goblet of Fire")
                .publishDate("08/07/2000")
                .authorsId(List.of(1L))
                .build();
        ResponseEntity<BookDto> response = this.restTemplate.exchange(this.baseUrl, HttpMethod.POST, new HttpEntity<>(createBookDto), BookDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createBook() {
        CreateBookDto createBookDto = CreateBookDto.builder()
                .isbn(9781338878951L)
                .title("Harry Potter and The Goblet of Fire")
                .publishDate("08/07/2000")
                .authorsId(List.of(1L))
                .build();
        ResponseEntity<BookDto> response = this.restTemplate.exchange(this.baseUrl, HttpMethod.POST, new HttpEntity<>(createBookDto), BookDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteBook() {
        ResponseEntity<?> response = this.restTemplate.exchange(
                this.baseUrl + "/1", HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateBook() {
        CreateBookDto updatedBookDto = CreateBookDto.builder()
                .isbn(110102121L)
                .title("Harry Potter and The Goblet of Fire 2")
                .publishDate("08/07/2000")
                .authorsId(List.of(1L))
                .build();

        ResponseEntity<BookDto> response = this.restTemplate.exchange(this.baseUrl + "/1", HttpMethod.PUT, new HttpEntity<>(updatedBookDto), BookDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBook() {
        ResponseEntity<BookDto> response = this.restTemplate.exchange(
                this.baseUrl + "/id/1", HttpMethod.GET, null, new ParameterizedTypeReference<BookDto>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getBookByIsbn() {
        ResponseEntity<BookDto> response = this.restTemplate.exchange(
                this.baseUrl + "/isbn/9781338878950", HttpMethod.GET, null, new ParameterizedTypeReference<BookDto>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllBooks() {
        ResponseEntity<List<BookWithAuthorIdDTO>> responseBasic = this.restTemplate.exchange(
                this.baseUrl + "/template/basic", HttpMethod.GET, null, new ParameterizedTypeReference<List<BookWithAuthorIdDTO>>() {
                }
        );
        assertEquals(HttpStatus.OK, responseBasic.getStatusCode());

        ResponseEntity<List<BookDto>> responseFull = this.restTemplate.exchange(
                this.baseUrl + "/template/full", HttpMethod.GET, null, new ParameterizedTypeReference<List<BookDto>>() {
                }
        );
        assertEquals(HttpStatus.OK, responseFull.getStatusCode());
    }
}
