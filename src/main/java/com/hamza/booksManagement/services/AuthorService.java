package com.hamza.booksManagement.services;

import com.hamza.booksManagement.entities.Author;
import com.hamza.booksManagement.exceptions.AuthorServiceException;
import com.hamza.booksManagement.mapstruct.dtos.AuthorAllDto;
import com.hamza.booksManagement.mapstruct.dtos.AuthorDto;
import com.hamza.booksManagement.repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Author addAuthor(AuthorDto author){
        return this.repository.save(this.modelMapper.map(author, Author.class));
    }

    public List<AuthorAllDto> findAllAuthors(){
        List<Author> authors = (List<Author>) this.repository.findAll();
        List<AuthorAllDto> gotAuthors = new ArrayList<>();
        authors.forEach(author -> { gotAuthors.add(this.modelMapper.map(author, AuthorAllDto.class)); });
        return gotAuthors;
    }

    public AuthorAllDto findAuthorById(int id){
        Author author = this.repository.findById(id)
                .orElseThrow(()-> new AuthorServiceException("author not found by given id"));
        return this.modelMapper.map(author, AuthorAllDto.class);
    }

    public Author updateAuthorById(int id, AuthorDto authorData){
        this.repository.findById(id).orElseThrow(()-> new AuthorServiceException("author not found by given id"));
        Author author = this.modelMapper.map(authorData, Author.class);
        author.setId(id);
        return this.repository.save(author);
    }

    public void deleteAuthor(int id){ this.repository.deleteById(id); }
}
