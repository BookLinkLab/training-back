package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.book.BasicBookDTO;
import com.booklink.trainingback.dto.book.BookDTO;
import com.booklink.trainingback.dto.book.CreateBookDTO;

import java.util.List;

public interface BookService {
    BookDTO create(CreateBookDTO bookDTO);
    BookDTO getBook(Long bookId);
    List<BookDTO> getAllBooks();
    List<BasicBookDTO> getAllBasicBooks();
    BookDTO updateBook(BookDTO bookDTO);
    BookDTO getBookByIsbn(String isbn);
    void deleteBook(Long bookId);
}
