package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;
import com.booklink.trainingback.exception.NotFoundException;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDto createAuthor(CreateAuthorDto authorDto) {
        Author authorToSave = Author.from(authorDto);
        Author savedAuthor = this.authorRepository.save(authorToSave);
        return AuthorDto.from(savedAuthor);
    }

    public void deleteAuthor(Long id) {
        Author authorToDelete = this.authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author %d not found".formatted(id)));
        this.authorRepository.delete(authorToDelete);
    }

    public AuthorDto updateAuthor(Long id, CreateAuthorDto createAuthorDto) {
        Author authorToUpdate = this.authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author %d not found".formatted(id)));
        authorToUpdate.setName(createAuthorDto.getName());
        authorToUpdate.setNationality(createAuthorDto.getNationality());
        authorToUpdate.setDateOfBirth(createAuthorDto.getDateOfBirth());
        Author updatedAuthor = this.authorRepository.save(authorToUpdate);
        return AuthorDto.from(updatedAuthor);
    }

    public AuthorDto getAuthor(Long id) {
        Author author = this.authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author %d not found".formatted(id)));
        return AuthorDto.from(author);
    }

    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = this.authorRepository.findAll();
        return authors.stream().map(AuthorDto::from).toList();
    }
}
