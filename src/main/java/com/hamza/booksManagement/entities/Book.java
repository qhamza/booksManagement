package com.hamza.booksManagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    @Column
    private int id;

    @Basic
    @Column
    private String title;

    @Basic
    @Column
    private String releaseDate;

    @Basic
    @Column
    private String genre;

    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "books"
    )

    private Set<Author> authors = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "books"
    )

    private Set<User> users = new HashSet<>();

}
