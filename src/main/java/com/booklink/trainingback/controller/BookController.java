package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.book.BookDTO;
import com.booklink.trainingback.dto.book.CreateBookDTO;
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

    @GetMapping
    List<BookDTO> getAllBook() {
        return bookService.getAllBooks();
    }

    @PostMapping
    BookDTO createBook(CreateBookDTO bookDTO) {
        return bookService.create(bookDTO);
    }

    @GetMapping("/{id}")
    BookDTO getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @DeleteMapping
    void deleteBook(@RequestParam Long id) {
        bookService.deleteBook(id);
    }


}
