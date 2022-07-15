package com.hamza.booksManagement.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {
    private int id;
    private String firstname;
    private String lastname;
    private String birthDate;
}
