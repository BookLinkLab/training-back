package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
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
public class AuthorControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/author";

    @BeforeEach
    void setup() {
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("George R. R. Martin")
                .nationality("United States")
                .dateOfBirth("1948/10/20")
                .build();

        this.restTemplate.postForEntity(this.baseUrl, createAuthorDto, AuthorDto.class);
    }

    @Test
    void createAuthor() {
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("George R. R. Martin")
                .nationality("United States")
                .dateOfBirth("1948/10/20")
                .build();

        ResponseEntity<AuthorDto> response = this.restTemplate.exchange(this.baseUrl, HttpMethod.POST, new HttpEntity<>(createAuthorDto), AuthorDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteAuthor() {
        ResponseEntity<?> response = this.restTemplate.exchange(
                this.baseUrl + "/1", HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateAuthor() {
        CreateAuthorDto updatedAuthorDto = CreateAuthorDto.builder()
                .name("George Martin")
                .nationality("United States")
                .dateOfBirth("1948/10/20")
                .build();

        ResponseEntity<AuthorDto> response = this.restTemplate.exchange(this.baseUrl + "/1", HttpMethod.PUT, new HttpEntity<>(updatedAuthorDto), AuthorDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAuthor() {
        ResponseEntity<AuthorDto> response = this.restTemplate.exchange(
                this.baseUrl + "/1", HttpMethod.GET, null, new ParameterizedTypeReference<AuthorDto>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAllAuthors() {
        ResponseEntity<List<AuthorDto>> response = this.restTemplate.exchange(
                this.baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AuthorDto>>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
