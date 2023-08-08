package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.author.CreateAuthorDto;
import com.booklink.trainingback.model.Author;
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
public class AuthorControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/author";

    @BeforeEach
    void setup(){
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("Ray Bradbury")
                .nationality("American")
                .dateOfBirth(LocalDate.of(1902, 8, 19))
                .build();
        restTemplate.postForEntity(baseUrl, createAuthorDto, Author.class);
    }

    @Test
    void createAuthor(){
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("Ray Bradbury")
                .nationality("American")
                .dateOfBirth(LocalDate.of(1902, 8, 19))
                .build();
        ResponseEntity<Author> response = restTemplate.exchange(
                baseUrl, HttpMethod.POST, new HttpEntity<>(createAuthorDto), Author.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getAllAuthors(){
        ResponseEntity<List<Author>> response = restTemplate.exchange(
                baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Author>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAuthor(){
        ResponseEntity<Author> response = restTemplate.exchange(
                baseUrl + "/1", HttpMethod.GET, null, Author.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteAuthor(){
        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/1", HttpMethod.DELETE, null, Void.class
        );
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void modifyAuthor(){
        CreateAuthorDto modifyAuthorDto = CreateAuthorDto.builder()
                .name("Ray Bradbury")
                .nationality("British")
                .dateOfBirth(LocalDate.of(1910, 8, 19))
                .build();
        ResponseEntity<Author> response = restTemplate.exchange(
                baseUrl + "/1",HttpMethod.PUT, new HttpEntity<>(modifyAuthorDto), Author.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
