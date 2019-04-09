package com.ejulia.bookworm.entity;

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
    /*
    Normally this set should be part of Book object (and so on for Loan). But then we need DTOs as an interface between BackEnd and
    FrontEnd to prevent Book and Loan to call each other infinitely (stackoverflow error).
     */
    // Tells JPA where to find the @ManyToOne configuration defined in Loan
    @OneToMany(mappedBy = "book")
    Set<Loan> loanSet;

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

/*  ATTENTION : CE BLOC AVAIT ETE CODE POUR LEVER UNE ERREUR (ABSENCE DE TRANSACTIONS AVEC LA DATABASE) MAIS S'IL EST DANS LE CODE IL PROVOQUE DES STACKOVERFLOW
    public Set<Loan> getLoanSet() {
        return loanSet;
    }

    public void setLoanSet(Set<Loan> loanSet) {
        this.loanSet = loanSet;
    }*/
}
