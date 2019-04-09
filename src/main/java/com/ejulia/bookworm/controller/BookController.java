package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.entity.Book;
import com.ejulia.bookworm.entity.Loan;
import com.ejulia.bookworm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path= "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping(path= "/find", params="title")
    public List<Book> getByTitle(@RequestParam String title) {
        return bookService.getByTitle(title);
    }

    @GetMapping(path= "/find", params="author")
    public List<Book> getByAuthor(@RequestParam String author) {
        return bookService.getByAuthor(author);
    }

    @GetMapping(path= "/add")
    public String addBook(@RequestParam String title, @RequestParam String author, @RequestParam(defaultValue="") String isbn) {
        return bookService.addBook(title, author, isbn);
    }

    @GetMapping(path= "/edit")
    public String editBook(@RequestParam Integer bookId, @RequestParam(required=false) String title, @RequestParam(required=false) String author, @RequestParam(required=false) String isbn)
            throws Exception {
        if(!title.isEmpty()) { bookService.editBookTitle(bookId, title); }
        if(author!="") { bookService.editBookAuthor(bookId, author); }
        if(isbn!="") { bookService.editBookIsbn(bookId, isbn); }
        return "Book edited";
    }

    //Proposition meilleure méthode

    @GetMapping(path= "/delete")
    public String deleteBook(@RequestParam Integer bookId) {
        return bookService.deleteBook(bookId);
    }

     @GetMapping(path = "/all")
    public List<Book> getAllBooks() {
       return bookService.getAllBooks();
     }

     @GetMapping(path = "/available")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
     }
}
