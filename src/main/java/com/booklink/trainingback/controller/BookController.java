package com.booklink.trainingback.controller;

import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.service.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //listar
    @PostMapping
    public List<Book> getAllBooks() {
        return this.bookService.getAllBooks();
    }
}
