package com.hamza.booksManagement.controllers;

import com.hamza.booksManagement.entities.Book;
import com.hamza.booksManagement.mapstruct.dtos.BookDto;
import com.hamza.booksManagement.mapstruct.dtos.BookSlimDto;
import com.hamza.booksManagement.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookSlimDto>> getAllBooks(){
        List<BookSlimDto> books = this.bookService.findAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<BookSlimDto> getBookById(@PathVariable int id){
        BookSlimDto book = this.bookService.findBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody BookDto book){
        Book newBook = this.bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody BookDto book){
        Book updatedBook = this.bookService.updateBookById(id, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody HttpStatus deleteBook(@PathVariable int id){
        this.bookService.deleteBook(id);
        return HttpStatus.OK;
    }
}
