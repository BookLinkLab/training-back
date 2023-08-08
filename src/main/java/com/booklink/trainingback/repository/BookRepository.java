package com.booklink.trainingback.repository;

import com.booklink.trainingback.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
