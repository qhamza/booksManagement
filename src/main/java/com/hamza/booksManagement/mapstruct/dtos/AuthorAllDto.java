package com.hamza.booksManagement.mapstruct.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AuthorAllDto {
    private int id;
    private String firstname;
    private String lastname;
    private String birthDate;
    private Set<BookSlimDto> books;
}
