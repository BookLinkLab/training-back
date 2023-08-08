package com.booklink.trainingback.service;

import com.booklink.trainingback.dto.author.AuthorDTO;
import com.booklink.trainingback.dto.author.CreateAuthorDTO;

import java.util.List;

public interface AuthorService {
    AuthorDTO create(CreateAuthorDTO authorDTO);
    AuthorDTO getAuthor(Long authorId);
    List<AuthorDTO> getAllAuthors();
    AuthorDTO updateAuthor(AuthorDTO authorDTO);
    void deleteAuthor(Long authorId);
}
