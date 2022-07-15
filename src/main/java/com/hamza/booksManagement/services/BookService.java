package com.hamza.booksManagement.services;

import com.hamza.booksManagement.entities.Book;
import com.hamza.booksManagement.exceptions.BookServiceException;
import com.hamza.booksManagement.mapstruct.dtos.BookDto;
import com.hamza.booksManagement.mapstruct.dtos.BookSlimDto;
import com.hamza.booksManagement.repositories.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Book addBook(BookDto book){
        return this.repository.save(this.modelMapper.map(book, Book.class));
    }

    public List<BookSlimDto> findAllBooks(){
        List<Book> books = (List<Book>) this.repository.findAll();
        List<BookSlimDto> gotBooks = new ArrayList<>();
        books.forEach(book -> { gotBooks.add(this.modelMapper.map(book, BookSlimDto.class)); });
        return gotBooks;
    }

    public BookSlimDto findBookById(int id){
        Book book = this.repository.findById(id)
                .orElseThrow(()-> new BookServiceException("book not found by given id"));
        return this.modelMapper.map(book, BookSlimDto.class);
    }

    public Book updateBookById(int id, BookDto bookData){
        this.repository.findById(id).orElseThrow(()-> new BookServiceException("book not found by given id"));
        Book book = this.modelMapper.map(bookData, Book.class);
        book.setId(id);
        return this.repository.save(book);
    }

    public void deleteBook(int id){ this.repository.deleteById(id); }
}
