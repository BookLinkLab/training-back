package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
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

    @PostMapping
    public AuthorDto createAuthor(@RequestBody CreateAuthorDto authorDto) {
        return this.authorService.createAuthor(authorDto);
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable Long id, @RequestBody CreateAuthorDto createAuthorDto) {
        return this.authorService.updateAuthor(id, createAuthorDto);
    }

    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable Long id) {
        this.authorService.deleteAuthor(id);
    }

    @GetMapping("{id}")
    public AuthorDto getAuthor(@PathVariable Long id) {
        return this.authorService.getAuthor(id);
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return this.authorService.getAllAuthors();
    }
}
