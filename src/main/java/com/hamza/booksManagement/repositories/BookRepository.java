package com.hamza.booksManagement.repositories;

import com.hamza.booksManagement.entities.Book;
import com.hamza.booksManagement.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Integer> {
    Optional<Book> findById(int id);
    void deleteById(int id);
}
