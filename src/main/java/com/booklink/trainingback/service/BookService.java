package com.booklink.trainingback.service;


import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.dto.CreateBookDto;
import com.booklink.trainingback.model.Book;
import org.springframework.stereotype.Service;


import java.util.List;


public interface BookService {
    BookDto createBook(CreateBookDto book);
    BookDto getBook(Long id);
    List<BookDto> getAllBooks();
    void deleteBook(Long id);
    BookDto getBookByTitle(String title);
    BookDto getBookByIsbn(Long isbn);
    List<BookDto> getAllBooksWithFullAuthorInfo();
    List<BookDto> getAllBooksWithBasicAuthorInfo();
    BookDto updateBook(Long id, CreateBookDto book);
}


