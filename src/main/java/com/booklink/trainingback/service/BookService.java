package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto getBook(Long id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        Book book = bookOptional.orElseThrow(() -> new NotFoundException("Book %d not found".formatted(id)));
        return BookDto.from(book);
    }

    public BookDto getBook(Integer ISBN) {
        Book book = this.bookRepository.findByISBN(ISBN);
        return BookDto.from(book);
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = this.bookRepository.findAll();
        return books.stream().map(BookDto::from).toList();
    }
}
