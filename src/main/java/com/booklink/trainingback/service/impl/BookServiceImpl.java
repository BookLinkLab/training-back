package com.booklink.trainingback.service.impl;

import com.booklink.trainingback.dto.book.BookDTO;
import com.booklink.trainingback.dto.book.CreateBookDTO;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.BookRepository;
import com.booklink.trainingback.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookDTO create(CreateBookDTO bookDTO) {
        Book book = Book.from(bookDTO);
        Book newBook = repository.save(book);
        return BookDTO.from(newBook);
    }

    @Override
    public BookDTO getBook(Long bookId) {
        Optional<Book> optionalBook = repository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new RuntimeException("Book not found"));
        return BookDTO.from(book);
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = repository.findAll();
        return books.stream().map(BookDTO::from).toList();
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        Book updatedBook = repository.findById(bookDTO.getId())
                .map(book -> {
                    book.setTitle(bookDTO.getTitle());
                    book.setISBN(bookDTO.getISBN());
                    book.setPublicationDate(bookDTO.getPublicationDate());
                    return repository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return BookDTO.from(updatedBook);
    }

    @Override
    public void deleteBook(Long bookId) {
        repository.deleteById(bookId);
    }
}
