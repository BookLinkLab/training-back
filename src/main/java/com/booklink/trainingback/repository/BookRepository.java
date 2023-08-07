package com.booklink.trainingback.repository;

import com.booklink.trainingback.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(Long ISBN);
}
