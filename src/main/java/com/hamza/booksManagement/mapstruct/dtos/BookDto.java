package com.hamza.booksManagement.mapstruct.dtos;

import com.hamza.booksManagement.entities.Author;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookDto {
    private String title;
    private String releaseDate;
    private String genre;
}
