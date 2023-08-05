package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.BookDto;
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
    public List<BookDto> getAllBooks() {
        return this.bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return this.bookService.getBook(id);
    }

    @GetMapping("/{ISBN}")
    public BookDto getBook(@PathVariable Integer ISBN){
        return this.bookService.getBook(ISBN);
    }
}
