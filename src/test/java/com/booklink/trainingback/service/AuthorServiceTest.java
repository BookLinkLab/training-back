package com.booklink.trainingback.service;


import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.exception.NotFoundException;
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
public class AuthorServiceTest {


    @Autowired
    private AuthorService authorService;

    @Test
    void happyPathTest() {
        assertTrue(authorService.getAllAuthors().isEmpty());

        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("JK Rowling")
                .nationality("British")
                .dateOfBirth("31/07/1965")
                .build();
        AuthorDto savedAuthor = authorService.createAuthor(createAuthorDto);

        List<AuthorDto> allAuthors = authorService.getAllAuthors();
        assertFalse(allAuthors.isEmpty());
        assertEquals(1, allAuthors.size());

        AuthorDto myAuthor = allAuthors.get(0);
        assertEquals(myAuthor, savedAuthor);

        CreateAuthorDto createAuthorDto1 = CreateAuthorDto.builder()
                .name("JK Bowling")
                .nationality("American")
                .dateOfBirth("31/07/1985")
                .build();

        AuthorDto savedUpdatedAuthor = authorService.updateAuthor(myAuthor.getId(), createAuthorDto1);

        List<AuthorDto> updatedAuthors = authorService.getAllAuthors();
        assertFalse(updatedAuthors.isEmpty());
        assertEquals(1, updatedAuthors.size());

        AuthorDto myUpdatedAuthor = updatedAuthors.get(0);
        assertEquals(myUpdatedAuthor, savedUpdatedAuthor);

        authorService.deleteAuthor(myUpdatedAuthor.getId());
        assertTrue(authorService.getAllAuthors().isEmpty());
    }

    @Test
    void exceptionTest() {
        assertThrows(NotFoundException.class, () -> authorService.getAuthor(1L));
    }
}