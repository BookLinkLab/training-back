package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.book.BookDto;
import com.booklink.trainingback.dto.book.CreateBookDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.AuthorRepository;
import com.booklink.trainingback.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    public BookService(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }


    public Book createBook(CreateBookDto dto){
        Author author = getAuthor(dto.getAuthorId());
        Book book = Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .publishDate(dto.getPublishDate())
                .author(author)
                .build();
        bookRepository.save(book);
        return book;
    }

    private Author getAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author %d not found".formatted(authorId)));
    }

    public Book modifyBook(Long id, CreateBookDto dto){
        Book book = getBook(id);
        Author author = getAuthor(dto.getAuthorId());

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublishDate(dto.getPublishDate());
        book.setAuthor(author);
        bookRepository.save(book);
        return book;
    }


    public Book getBook(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book %d not found".formatted(id)));
    }

    public Book getBookByIsbn(Long isbn){
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Book %d not found".formatted(isbn)));
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    public List<BookDto> getBasicBooks(){
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookDto::from).toList();
    }

    public List<Book> getFullBooks(){
        return bookRepository.findAll();
    }

}
