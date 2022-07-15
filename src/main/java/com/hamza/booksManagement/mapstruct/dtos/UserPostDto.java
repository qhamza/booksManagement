package com.hamza.booksManagement.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserPostDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Set<BookSlimDto> books;

    public UserPostDto(){}

    public UserPostDto(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
