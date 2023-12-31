package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.BookDto;
import com.booklink.trainingback.dto.BookWithAuthorIdDTO;
import com.booklink.trainingback.dto.CreateBookDto;
import com.booklink.trainingback.exception.AuthorDoesntExistExcpetion;
import com.booklink.trainingback.exception.BookAlreadyExistsException;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.AuthorRepository;
import com.booklink.trainingback.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public BookDto createBook(CreateBookDto bookDto) {
        List<Author> authors = this.authorRepository.findAllById(bookDto.getAuthorIds());
        if (authors.size() < bookDto.getAuthorIds().size()) {
            throw new AuthorDoesntExistExcpetion("One or more author IDs not found");
        }
        if (this.bookRepository.findByIsbn(bookDto.getIsbn()).isPresent()) {
            throw new BookAlreadyExistsException("Book with isbn %d already exists".formatted(bookDto.getIsbn()));
        }
        Book bookToSave = Book.from(bookDto, authors);
        Book savedBook = this.bookRepository.save(bookToSave);
        authors.forEach(a -> a.getBooks().add(savedBook));
        return BookDto.from(savedBook);
    }

    public void deleteBook(Long id) {
        Book bookToDelete = this.bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book %d not found".formatted(id)));
        this.bookRepository.delete(bookToDelete);
    }

    public BookDto updateBook(Long id, CreateBookDto bookDto) {
        Book bookToModify = this.bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book %d not found".formatted(id)));
        List<Author> updatedAuthors = this.authorRepository.findAllById(bookDto.getAuthorIds());
        bookToModify.setIsbn(bookDto.getIsbn());
        bookToModify.setTitle(bookDto.getTitle());
        bookToModify.setPublishDate(bookDto.getPublishDate());
        bookToModify.setAuthors(updatedAuthors);
        Book updatedBook = this.bookRepository.save(bookToModify);
        return BookDto.from(updatedBook);
    }

    public BookDto getBook(Long id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        Book book = bookOptional.orElseThrow(() -> new NotFoundException("Book %d not found".formatted(id)));
        return BookDto.from(book);
    }

    public BookDto getBookByIsbn(Long isbn) {
        Book book = this.bookRepository.findByIsbn(isbn).orElseThrow(() -> new NotFoundException("Book %d not found".formatted(isbn)));
        return BookDto.from(book);
    }

    public List<BookDto> getAllBooksFull() {
        List<Book> books = this.bookRepository.findAll();
        return books.stream()
                .map(BookDto::from)
                .toList();
    }

    public List<BookWithAuthorIdDTO> getAllBooksBasic() {
        List<Book> books = this.bookRepository.findAll();
        return books.stream()
                .map(BookWithAuthorIdDTO::from)
                .toList();
    }
}
