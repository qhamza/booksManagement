package com.hamza.booksManagement.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserGetDto {
    private int id;
    private String firstname;
    private String lastname;
    private String email;

    public UserGetDto(){}

    public UserGetDto(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
