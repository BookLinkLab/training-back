package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.author.AuthorDTO;
import com.booklink.trainingback.dto.author.CreateAuthorDTO;
import com.booklink.trainingback.dto.book.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AuthorControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "/author";

    @Test
    void getAllAuthors() {
        ResponseEntity<List<AuthorDTO>> response = restTemplate.exchange(
                baseUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AuthorDTO>>() {}
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCreateAuthor() {
        CreateAuthorDTO newAuthor = new CreateAuthorDTO(
                "J.R.R. Tolkien",
                "British",
                new Date(1892,1,3)
        );
        ResponseEntity<AuthorDTO> response = restTemplate.postForEntity(
                "/author", newAuthor, AuthorDTO.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("British", Objects.requireNonNull(response.getBody()).getNationality());

    }

    @Test
    void testUpdateAuthor() {
        CreateAuthorDTO newAuthor = new CreateAuthorDTO(
                "J.R.R. Tolkien",
                "British",
                new Date(1892,1,3)
        );
        ResponseEntity<AuthorDTO> response = restTemplate.postForEntity(
                "/author", newAuthor, AuthorDTO.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("British", Objects.requireNonNull(response.getBody()).getNationality());
        AuthorDTO authorDTO = response.getBody();
        authorDTO.setNationality("English");
        restTemplate.put("/author", authorDTO);
        ResponseEntity<AuthorDTO> response2 = restTemplate.getForEntity(
                "/author/1", AuthorDTO.class
        );
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("English", response2.getBody().getNationality());
    }

}
