package com.booklink.trainingback.repository;

import com.booklink.trainingback.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByName(String name);

}
