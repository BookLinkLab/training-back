package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.author.AuthorDTO;
import com.booklink.trainingback.dto.author.CreateAuthorDTO;
import com.booklink.trainingback.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping
    AuthorDTO createAuthor(@RequestBody CreateAuthorDTO authorDTO) {
        return authorService.create(authorDTO);
    }

    @GetMapping("/{id}")
    AuthorDTO getAuthor(@PathVariable Long id) {
        return authorService.getAuthor(id);
    }

    @DeleteMapping
    void deleteAuthor(@RequestParam Long id) {
        authorService.deleteAuthor(id);
    }

    @PutMapping
    AuthorDTO updateAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.updateAuthor(authorDTO);
    }


}
