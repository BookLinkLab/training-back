package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.book.BookDTO;
import com.booklink.trainingback.dto.book.BookType;
import com.booklink.trainingback.dto.book.CreateBookDTO;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {this.bookService = bookService;}

    @GetMapping
    ResponseEntity<?> getAllBook(@RequestParam BookType template) {
        if (template == BookType.FULL) {
            return ResponseEntity.ok(bookService.getAllBooks());
        }
        else if(template == BookType.BASIC) {
            return ResponseEntity.ok(bookService.getAllBasicBooks());
        }
        else {
            throw new NotFoundException("Book type not found");
        }
    }

    @PostMapping
    BookDTO createBook(@RequestBody CreateBookDTO bookDTO) {
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

    @PutMapping
    BookDTO updateBook(@RequestBody BookDTO bookDTO) {return bookService.updateBook(bookDTO);}

    @GetMapping("/search/{isbn}")
    BookDTO searchBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }

}
