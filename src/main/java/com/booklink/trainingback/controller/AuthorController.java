package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private AuthorService authorService;

    public AuthorController(AuthorService authorService1){
        this.authorService = authorService1;
    }


    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody CreateAuthorDto dto) {
        AuthorDto createdAuthor = authorService.createAuthor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }
    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthor(@PathVariable Long id) {
        return authorService.getAuthor(id);
    }

    @GetMapping("/name/{name}")
    public AuthorDto getAuthorByName(@PathVariable String name) {
        return authorService.getAuthorByName(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody CreateAuthorDto author) {
        AuthorDto updatedAuthor = authorService.updateAuthor(id, author);
        return ResponseEntity.ok(updatedAuthor);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

}
