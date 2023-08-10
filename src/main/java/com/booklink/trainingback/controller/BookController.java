package com.booklink.trainingback.controller;

import com.booklink.trainingback.dto.book.CreateBookDto;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody CreateBookDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(dto));
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks(@RequestParam(name = "template", defaultValue = "full") String template){
        if (!template.equals("full") && !template.equals("basic")){
            return ResponseEntity.badRequest().build();
        }
        if(template.equals("basic")){
            return ResponseEntity.ok(bookService.getBasicBooks());
        }
        else{
            return ResponseEntity.ok(bookService.getFullBooks());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> modifyBook(@PathVariable Long id, @RequestBody CreateBookDto dto){
        return ResponseEntity.ok(bookService.modifyBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable Long isbn){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBookByIsbn(isbn));
    }


}
