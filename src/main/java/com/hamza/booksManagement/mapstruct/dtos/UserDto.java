package com.hamza.booksManagement.mapstruct.dtos;

import com.hamza.booksManagement.entities.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    public int id;
    public String firstname;
    public String lastname;
    public String email;
    public String password;
    public Set<Book> books;

    public UserDto(){};
    public UserDto(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
