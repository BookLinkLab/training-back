package com.booklink.trainingback.service.impl;

import com.booklink.trainingback.dto.author.AuthorDTO;
import com.booklink.trainingback.dto.author.CreateAuthorDTO;
import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.repository.AuthorRepository;
import com.booklink.trainingback.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorDTO create(CreateAuthorDTO authorDTO) {
        Author author = Author.from(authorDTO);
        Author newAuthor = authorRepository.save(author);
        return AuthorDTO.from(newAuthor);
    }

    @Override
    public AuthorDTO getAuthor(Long authorId) {
        Optional<Author> optionalAuthor = authorRepository.findById(authorId);
        Author author = optionalAuthor.orElseThrow(() -> new RuntimeException("Author not found"));
        return AuthorDTO.from(author);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(AuthorDTO::from).toList();
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        Author updatedAuthor = authorRepository.findById(authorDTO.getId())
                .map(author -> {
                    author.setName(authorDTO.getName());
                    author.setNationality(authorDTO.getNationality());
                    author.setDateOfBirth(authorDTO.getDateOfBirth());
                    return authorRepository.save(author);
                })
                .orElseThrow(() -> new RuntimeException("Author not found"));
        return AuthorDTO.from(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
    }
}
