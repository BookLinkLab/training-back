package com.booklink.trainingback.repository;

import com.booklink.trainingback.model.Author;
import com.booklink.trainingback.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(Long isbn);
    List<Book> findAllByAuthor(Author author);
}
