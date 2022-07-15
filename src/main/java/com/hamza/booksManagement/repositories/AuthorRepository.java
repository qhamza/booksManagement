package com.hamza.booksManagement.repositories;

import com.hamza.booksManagement.entities.Author;
import com.hamza.booksManagement.entities.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
    Optional<Author> findById(int id);
    void deleteById(int id);
}
