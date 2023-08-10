package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.author.CreateAuthorDto;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors(){
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody CreateAuthorDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.createAuthor(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor (@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> modifyAuthor(@PathVariable Long id, @RequestBody CreateAuthorDto dto){
        return ResponseEntity.ok(authorService.modifyAuthor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.accepted().build();
    }

}
