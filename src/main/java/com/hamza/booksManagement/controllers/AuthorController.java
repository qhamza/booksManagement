package com.hamza.booksManagement.controllers;

import com.hamza.booksManagement.entities.Author;
import com.hamza.booksManagement.mapstruct.dtos.AuthorAllDto;
import com.hamza.booksManagement.mapstruct.dtos.AuthorDto;
import com.hamza.booksManagement.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/all")
    public ResponseEntity<List<AuthorAllDto>> getAllAuthors(){
        List<AuthorAllDto> authors = this.authorService.findAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<AuthorAllDto> getAuthorById(@PathVariable int id){
        AuthorAllDto author = this.authorService.findAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Author> addAuthor(@RequestBody AuthorDto author){
        Author newAuthor = this.authorService.addAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable int id, @RequestBody AuthorDto author){
        Author updatedAuthor = this.authorService.updateAuthorById(id, author);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody HttpStatus deleteAuthor(@PathVariable int id){
        this.authorService.deleteAuthor(id);
        return HttpStatus.OK;
    }
}
