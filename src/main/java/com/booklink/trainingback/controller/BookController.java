package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.dto.CreateBookDto;
import com.booklink.trainingback.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService1) {
        this.bookService = bookService1;
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody CreateBookDto dto) {
        BookDto createdBook = bookService.createBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam(required = false) String template) {
        if (template != null && !template.isEmpty()) {
            if (template.equalsIgnoreCase("full")) {
                List<BookDto> booksWithFullAuthorInfo = bookService.getAllBooksWithFullAuthorInfo();
                return ResponseEntity.ok(booksWithFullAuthorInfo);
            } else if (template.equalsIgnoreCase("basic")) {
                List<BookDto> booksWithBasicAuthorInfo = bookService.getAllBooksWithBasicAuthorInfo();
                return ResponseEntity.ok(booksWithBasicAuthorInfo);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            List<BookDto> allBooks = bookService.getAllBooks();
            return ResponseEntity.ok(allBooks);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody CreateBookDto book) {
        BookDto updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return bookService.getBook(id);
    }

    @GetMapping("/title/{title}")
    public BookDto getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @GetMapping("/isbn/{isbn}")
    public BookDto getBookByIsbn(@PathVariable Long isbn) {
        return bookService.getBookByIsbn(isbn);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }


}