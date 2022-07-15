package com.hamza.booksManagement.services;

import com.hamza.booksManagement.entities.User;
import com.hamza.booksManagement.exceptions.UserServiceException;
import com.hamza.booksManagement.mapstruct.dtos.UserDto;
import com.hamza.booksManagement.mapstruct.dtos.UserGetDto;
import com.hamza.booksManagement.mapstruct.dtos.UserPostDto;
import com.hamza.booksManagement.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public User addUser(UserPostDto user){
        return this.repository.save(this.modelMapper.map(user, User.class));
    }

    public List<UserGetDto> findAllUsers() throws Exception {
        List<User> users = this.repository.findAll();
        if (users.isEmpty()) throw new Exception("users is empty");
        List<UserGetDto> gotUsers = new ArrayList<>();
        users.forEach(user -> { gotUsers.add(this.modelMapper.map(user, UserGetDto.class)); });
        return gotUsers;
//        return users.stream().map(this::UserToGetDto).toList();
    }

    public UserGetDto findUserById(int id) throws Exception {
        if (id == 0) throw new Exception("id not valid");
        User user = this.repository.findById(id)
                .orElseThrow(()-> new UserServiceException("user not found by given id"));
        return this.modelMapper.map(user, UserGetDto.class);
    }

    public User updateUserById(int id, UserPostDto userData) throws Exception {
        if (id == 0) throw new Exception("id not valid");
        this.repository.findById(id).orElseThrow(()-> new UserServiceException("user not found by given id"));
        User user = this.modelMapper.map(userData, User.class);
        user.setId(id);
        return this.repository.save(user);
    }

    public User deleteUser(int id) throws Exception {
        if (id == 0) throw new Exception("id not valid");
        this.repository.findById(id).orElseThrow(()-> new UserServiceException("user not found by given id"));
        return this.repository.deleteById(id);
    }
}
