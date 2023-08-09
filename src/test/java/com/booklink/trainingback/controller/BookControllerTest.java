package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.author.AuthorDTO;
import com.booklink.trainingback.dto.author.CreateAuthorDTO;
import com.booklink.trainingback.dto.book.BookDTO;
import com.booklink.trainingback.dto.book.BookType;
import com.booklink.trainingback.dto.book.CreateBookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/book";
    private final String baseUrl2 = "/author";


    //Test for getAllBooks with a query parameter
    @Test
    void testGetAllBooks() {
        CreateAuthorDTO newAuthor = new CreateAuthorDTO(
                "J.R.R. Tolkien",
                "British",
                new Date(1892,1,3)
        );
        ResponseEntity<AuthorDTO> response = restTemplate.postForEntity(
                baseUrl2, newAuthor, AuthorDTO.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("British", Objects.requireNonNull(response.getBody()).getNationality());
        Long authorId = Objects.requireNonNull(response.getBody()).getId();
        CreateBookDTO newBook = new CreateBookDTO(
                "The Lord of the Rings",
                "12345",
                new Date(1954, 7, 29),
                authorId
        );

        ResponseEntity<BookDTO> response1 = restTemplate.postForEntity(
                baseUrl, newBook, BookDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("12345", Objects.requireNonNull(response1.getBody()).getISBN());

        ResponseEntity<List<BookDTO>> response2 = restTemplate.exchange(
                baseUrl + "?template=FULL",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BookDTO>>() {}
        );

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertEquals(1, Objects.requireNonNull(response2.getBody()).size());
        assertEquals("12345", response2.getBody().get(0).getISBN());
    }

    @Test
    void testCreateBook() {
        CreateAuthorDTO newAuthor = new CreateAuthorDTO(
                "J.R.R. Tolkien",
                "British",
                new Date(1892,1,3)
        );
        ResponseEntity<AuthorDTO> response = restTemplate.postForEntity(
                baseUrl2, newAuthor, AuthorDTO.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("British", Objects.requireNonNull(response.getBody()).getNationality());
        Long authorId = Objects.requireNonNull(response.getBody()).getId();
        System.out.println(authorId);
        CreateBookDTO newBook = new CreateBookDTO(
                "The Lord of the Rings",
                "12345",
                new Date(1954, 7, 29),
                authorId
        );

        ResponseEntity<BookDTO> response1 = restTemplate.postForEntity(
                baseUrl, newBook, BookDTO.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("12345", Objects.requireNonNull(response1.getBody()).getISBN());
    }


}
