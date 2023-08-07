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
        assertTrue(this.authorService.getAllAuthors().isEmpty());

        //create
        CreateAuthorDto createAuthorDto = CreateAuthorDto.builder()
                .name("George R. R. Martin")
                .nationality("United States")
                .dateOfBirth("1948/10/20")
                .build();

        AuthorDto savedAuthor = this.authorService.createAuthor(createAuthorDto);

        List<AuthorDto> allAuthors = this.authorService.getAllAuthors();
        assertFalse(allAuthors.isEmpty());
        assertEquals(1, allAuthors.size());

        AuthorDto author = allAuthors.get(0);
        assertEquals(author, savedAuthor);

        //update
        CreateAuthorDto updatedAuthorData = CreateAuthorDto.builder()
                .name("George Martin")
                .nationality("United States")
                .dateOfBirth("1948/10/20")
                .build();

        this.authorService.updateAuthor(author.getId(), updatedAuthorData);
        assertEquals(this.authorService.getAllAuthors().get(0).getName(), "George Martin");

        //delete
        this.authorService.deleteAuthor(1L);
        assertTrue(this.authorService.getAllAuthors().isEmpty());
    }

    @Test
    void exceptionTest() {
        assertThrows(NotFoundException.class, () -> this.authorService.getAuthor(1L));
    }
}
