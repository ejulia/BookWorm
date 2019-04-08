package com.ejulia.bookworm.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Book {
/*
    Using below @GeneratedValue(strategy= GenerationType.AUTO) leads to having same incrementation in several tabs.
    To fix it, use @GeneratedValue(generator = "increment") or @GenericGenerator(name="increment", strategy = "increment")
 */
    @Id
    @GeneratedValue (generator = "increment")
    private Integer bookId;

    private String title;
    private String author;
    private String isbn;

    // Tells JPA where to find the @ManyToOne configuration defined in Transaction
    @OneToMany(mappedBy = "book")
    Set<Transaction> transactionSet;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
