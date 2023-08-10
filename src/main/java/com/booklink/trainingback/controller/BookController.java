package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.dto.CreateBookDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookDto bookDto) {
        return this.bookService.createBook(bookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        this.bookService.deleteBook(id);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable Long id, @RequestBody CreateBookDto bookDto) {
        return this.bookService.updateBook(id, bookDto);
    }

    @GetMapping("/id/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return this.bookService.getBook(id);
    }

    @GetMapping("/isbn/{isbn}")
    public BookDto getBookByIsbn(@PathVariable Long isbn) {
        return this.bookService.getBookByIsbn(isbn);
    }

    @GetMapping("/template/{template}")
    public List<?> getAllBooks(@PathVariable String template) {
        if (template.equals("full")) {
            return this.bookService.getAllBooksFull();
        } else if (template.equals("basic")) {
            return this.bookService.getAllBooksBasic();
        }
        throw new NotFoundException("Template %s not found".formatted(template));
    }
}
