package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.model.Author;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AuthorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String authorBaseUrl = "/author";

    @BeforeEach
    void setUp() {
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("JK Rowling")
                .nationality("British")
                .dateOfBirth("31/07/1965")
                .build();
        restTemplate.postForEntity(authorBaseUrl, createAuthorDto, AuthorDto.class);
    }


    @Test
    void createAuthor() {
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("JK Rowling")
                .nationality("British")
                .dateOfBirth("31/07/1965")
                .build();

        ResponseEntity<AuthorDto> response = restTemplate.postForEntity(
                authorBaseUrl, createAuthorDto, AuthorDto.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        Assertions.assertEquals("JK Rowling", response.getBody().getName());
        Assertions.assertEquals("British", response.getBody().getNationality());
        Assertions.assertEquals("31/07/1965", response.getBody().getDateOfBirth());
    }



    @Test
    void getAllAuthors() {
        ResponseEntity<List<AuthorDto>> response = restTemplate.exchange(
                authorBaseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AuthorDto>>() {}
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        List<AuthorDto> authors = response.getBody();
        assertNotNull(authors);
        Assertions.assertEquals(1, authors.size());
    }

    @Test
    void getAuthorById() {
        Long authorId = 1L;
        ResponseEntity<AuthorDto> response = restTemplate.getForEntity(
                authorBaseUrl + "/" + authorId, AuthorDto.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        AuthorDto author = response.getBody();
        Assertions.assertEquals(authorId, author.getId());
    }

    @Test
    void deleteBook() {
        Long authorIdToDelete = 1L;

        restTemplate.delete(authorBaseUrl + "/" + authorIdToDelete);

        ResponseEntity<AuthorDto> response = restTemplate.getForEntity(
                authorBaseUrl + "/" + authorIdToDelete, AuthorDto.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateAuthor(){
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("JK Bowling")
                .nationality("American")
                .dateOfBirth("31/07/1985")
                .build();
        ResponseEntity<Author> response = restTemplate.exchange(
                authorBaseUrl + "/1", HttpMethod.PUT, new HttpEntity<>(createAuthorDto), Author.class
        );
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}


