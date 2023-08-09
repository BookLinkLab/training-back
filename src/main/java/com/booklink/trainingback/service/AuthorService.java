package com.booklink.trainingback.service;



import com.booklink.trainingback.dto.AuthorDto;
import com.booklink.trainingback.dto.CreateAuthorDto;


import java.util.List;


public interface AuthorService {
    AuthorDto createAuthor(CreateAuthorDto author);
    AuthorDto getAuthor(Long id);
    List<AuthorDto> getAllAuthors();
    void deleteAuthor(Long id);
    AuthorDto getAuthorByName(String name);
    AuthorDto updateAuthor(Long id, CreateAuthorDto author);
}
