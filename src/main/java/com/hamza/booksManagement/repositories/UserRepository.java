package com.hamza.booksManagement.repositories;

import com.hamza.booksManagement.entities.User;
import com.hamza.booksManagement.mapstruct.dtos.UserGetDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findById(int id);
    User deleteById(int id);

    List<User> findAll();
}
