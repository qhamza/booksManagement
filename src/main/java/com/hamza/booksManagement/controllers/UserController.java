package com.hamza.booksManagement.controllers;

import com.hamza.booksManagement.entities.User;
import com.hamza.booksManagement.exceptions.UserServiceException;
import com.hamza.booksManagement.mapstruct.dtos.UserDto;
import com.hamza.booksManagement.mapstruct.dtos.UserGetDto;
import com.hamza.booksManagement.mapstruct.dtos.UserPostDto;
import com.hamza.booksManagement.services.UserService;
import org.hibernate.boot.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserGetDto>> getAllUsers(){
        try {
            List<UserGetDto> users = this.userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable int id){
        try {
            UserGetDto user = this.userService.findUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (UserServiceException exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody UserPostDto user){
        try{
            User newUser = this.userService.addUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        }catch (NullPointerException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody UserPostDto user){
        try{
            User updatedUser = this.userService.updateUserById(id, user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        catch(UserServiceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (NullPointerException e){
            System.out.println("prova");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody ResponseEntity<User> deleteUser(@PathVariable int id){
        try{
            User deletedUser = this.userService.deleteUser(id);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        }catch (UserServiceException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
