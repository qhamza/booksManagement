package com.hamza.booksManagement.daos;

import com.hamza.booksManagement.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    public List<User> findAll();
    Optional<User> findById(int id);
    void deleteById(int id);
}
