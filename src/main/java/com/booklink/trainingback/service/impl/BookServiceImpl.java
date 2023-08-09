package com.booklink.trainingback.service.impl;

import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.dto.CreateBookDto;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.AuthorRepository;
import com.booklink.trainingback.repository.BookRepository;
import com.booklink.trainingback.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository1, AuthorRepository authorRepository1) {
        this.bookRepository = bookRepository1;
        this.authorRepository = authorRepository1;
    }


    @Override
    public BookDto createBook(CreateBookDto book) {
        Author author = authorRepository.findById(book.getAuthor_id())
                .orElseThrow(() -> new NotFoundException("Author not found with ID: " + book.getAuthor_id()));

        Book newBook = Book.from(book);
        newBook.setAuthor(author);

        Book savedBook = bookRepository.save(newBook);
        return BookDto.from(savedBook);

    }





    @Override
    public BookDto getBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book book = optionalBook.orElseThrow(() -> new NotFoundException("Bomb %d not found".formatted(id)));
        return BookDto.from(book);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookDto::from).toList();
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto getBookByTitle(String title) {
        Optional<Book> optionalBook = Optional.ofNullable(bookRepository.findByTitle(title));
        Book book = optionalBook.orElseThrow(() -> new NotFoundException("Bomb %s not found".formatted(title)));
        return BookDto.from(book);
    }

    @Override
    public BookDto getBookByIsbn(Long isbn) {
        Optional<Book> optionalBook = Optional.ofNullable(bookRepository.findByIsbn(isbn));
        Book book = optionalBook.orElseThrow(() -> new NotFoundException("Bomb %d not found".formatted(isbn)));
        return BookDto.from(book);
    }

    @Override
    public List<BookDto> getAllBooksWithFullAuthorInfo() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = BookDto.from(book);
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }


    @Override
    public List<BookDto> getAllBooksWithBasicAuthorInfo() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = BookDto.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .isbn(book.getIsbn())
                    .publishDate(book.getPublishDate())
                    .authorId(book.getAuthor().getId())
                    .author(null)
                    .build();
            bookDtos.add(bookDto);
        }
        return bookDtos;
    }


    @Override
    public BookDto updateBook(Long id, CreateBookDto book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        Book existingBook = optionalBook.orElseThrow(() -> new NotFoundException("Book with ID " + id + " not found"));
        Long Id = book.getAuthor_id();
        Optional<Author> optionalAuthor = authorRepository.findById(Id);
        Author author = optionalAuthor.orElseThrow(() -> new NotFoundException("Author with ID " + id + " not found"));
        existingBook.setTitle(book.getTitle());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublishDate(book.getPublishDate());
        existingBook.setAuthor(author);

        Book updatedBook = bookRepository.save(existingBook);
        return BookDto.from(updatedBook);
    }



}
