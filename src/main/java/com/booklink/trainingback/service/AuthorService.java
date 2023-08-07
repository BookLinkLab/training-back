package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.author.CreateAuthorDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import com.booklink.trainingback.repository.AuthorRepository;
import com.booklink.trainingback.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService (AuthorRepository authorRepository, BookRepository bookRepository){
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author createAuthor(CreateAuthorDto dto){
        Author author = Author.builder()
                .name(dto.getName())
                .nationality(dto.getNationality())
                .dateOfBirth(dto.getDateOfBirth())
                .build();
        return authorRepository.save(author);
    }


    public Author getAuthor(Long id){
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author %d not found".formatted(id)));
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Author modifyAuthor(Long id, CreateAuthorDto dto){
        Author author = getAuthor(id);
        author.setName(dto.getName());
        author.setNationality(dto.getNationality());
        author.setDateOfBirth(dto.getDateOfBirth());
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id){
        Author author = getAuthor(id);
        List<Book> books = bookRepository.findAllByAuthor(author);
        bookRepository.deleteAll(books);
        authorRepository.delete(author);
    }


}
