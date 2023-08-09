package com.booklink.trainingback.service.impl;

import com.booklink.trainingback.dto.book.BasicBookDTO;
import com.booklink.trainingback.dto.book.BookDTO;
import com.booklink.trainingback.dto.book.CreateBookDTO;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.AuthorRepository;
import com.booklink.trainingback.repository.BookRepository;
import com.booklink.trainingback.service.BookService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookDTO create(CreateBookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(() -> new NotFoundException("Author not found"));
        Book book = Book.from(bookDTO);
        book.setAuthor(author);
        Book newBook = bookRepository.save(book);

        return BookDTO.from(newBook);
    }

    @Override
    public BookDTO getBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new RuntimeException("Book not found"));
        return BookDTO.from(book);
    }

    @Override
    public List<BasicBookDTO> getAllBasicBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BasicBookDTO::from).toList();
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookDTO::from).toList();
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        Book updatedBook = bookRepository.findById(bookDTO.getId())
                .map(book -> {
                    book.setTitle(bookDTO.getTitle());
                    book.setISBN(bookDTO.getISBN());
                    book.setPublicationDate(bookDTO.getPublicationDate());
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new NotFoundException("Book not found"));
        return BookDTO.from(updatedBook);
    }


    @Override
    public BookDTO getBookByIsbn(String ISBN) {
        Book book = bookRepository.findByISBN(ISBN);
        return BookDTO.from(book);
    }


    @Override
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }
}
