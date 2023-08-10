package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.author.CreateAuthorDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    @Test
    void happyPathTest(){
        assertTrue(authorService.getAllAuthors().isEmpty());


        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("Ray Bradbury")
                .nationality("American")
                .dateOfBirth(LocalDate.of(1902, 8, 19))
                .build();

        Author savedAuthor = authorService.createAuthor(createAuthorDto);

        List<Author> allAuthors = authorService.getAllAuthors();
        assertFalse(allAuthors.isEmpty());
        assertEquals(allAuthors.size(), 1);

        Author myAuthor = allAuthors.get(0);
        assertEquals(myAuthor, savedAuthor);


        CreateAuthorDto modifyAuthorDto = CreateAuthorDto.builder()
                .name("Ray Bradbury")
                .nationality("British")
                .dateOfBirth(LocalDate.of(1903, 3, 19))
                .build();
        Author savedUpdatedAuthor = authorService.modifyAuthor(myAuthor.getId(), modifyAuthorDto);

        List<Author> updatedAuthors = authorService.getAllAuthors();
        assertFalse(updatedAuthors.isEmpty());
        assertEquals(1, updatedAuthors.size());

        Author myUpdatedAuthor = updatedAuthors.get(0);
        assertEquals(myUpdatedAuthor, savedUpdatedAuthor);
        assertNotEquals(myUpdatedAuthor, myAuthor);

        authorService.deleteAuthor(myUpdatedAuthor.getId());
        assertTrue(authorService.getAllAuthors().isEmpty());

    }

    @Test
    void exceptionTest(){
        assertThrows(NotFoundException.class, () -> authorService.getAuthor(1L));
    }
}
