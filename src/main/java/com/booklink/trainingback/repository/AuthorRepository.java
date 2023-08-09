package com.booklink.trainingback.repository;

import com.booklink.trainingback.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
