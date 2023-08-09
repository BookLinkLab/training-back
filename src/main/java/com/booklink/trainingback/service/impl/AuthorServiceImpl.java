package com.booklink.trainingback.service.impl;


import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.repository.AuthorRepository;
import com.booklink.trainingback.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository1) {
        this.authorRepository = authorRepository1;
    }


    @Override
    public AuthorDto createAuthor(CreateAuthorDto author) {
        Author authorToSave = Author.from(author);
        Author savedAuthor = authorRepository.save(authorToSave);
        return AuthorDto.from(savedAuthor);
    }

    @Override
    public AuthorDto getAuthor(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        Author author = optionalAuthor.orElseThrow(() -> new NotFoundException("Author %d not found".formatted(id)));
        return AuthorDto.from(author);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(AuthorDto::from).toList();
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorDto getAuthorByName(String name) {
        Optional<Author> optionalAuthor = Optional.ofNullable(authorRepository.findByName(name));
        Author author = optionalAuthor.orElseThrow(() -> new NotFoundException("author %s not found".formatted(name)));
        return AuthorDto.from(author);
    }

    @Override
    public AuthorDto updateAuthor(Long id, CreateAuthorDto author) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        Author existingAuthor = optionalAuthor.orElseThrow(() -> new NotFoundException("author %d not found".formatted(id)));

        existingAuthor.setName(author.getName());
        existingAuthor.setNationality(author.getNationality());
        existingAuthor.setDateOfBirth(author.getDateOfBirth());

        Author updatedAuthor = authorRepository.save(existingAuthor);
        return AuthorDto.from(updatedAuthor);
    }
}
