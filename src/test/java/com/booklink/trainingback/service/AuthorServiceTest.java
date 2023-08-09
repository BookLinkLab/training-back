package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.author.AuthorDTO;
import com.booklink.trainingback.dto.author.CreateAuthorDTO;
import com.booklink.trainingback.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    @Test
    void AuthorTests() {
        assertTrue(authorService.getAllAuthors().isEmpty());

        CreateAuthorDTO newAuthor = new CreateAuthorDTO(
                "J.R.R. Tolkien",
                "British",
                new Date(1892,1,3)
        );
        authorService.create(newAuthor);
        List<AuthorDTO> authors = authorService.getAllAuthors();
        assertEquals(1, authors.size());
        assertEquals("British", authors.get(0).getNationality());

        AuthorDTO authorDTO = authorService.getAuthor(authors.get(0).getId());
        assertEquals("J.R.R. Tolkien", authorDTO.getName());

        authorService.updateAuthor(new AuthorDTO(
                authorDTO.getId(),
                "J.R.R. Tolkien",
                "South African",
                new Date(1900,1,1)
        ));

        authorDTO = authorService.getAuthor(authorDTO.getId());
        assertEquals("South African", authorDTO.getNationality());

        authorService.deleteAuthor(authorDTO.getId());
        assertTrue(authorService.getAllAuthors().isEmpty());
    }

    @Test
    void exceptionTest() {
        assertThrows(NotFoundException.class, () -> authorService.getAuthor(122L));
    }

}
