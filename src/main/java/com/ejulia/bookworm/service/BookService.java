package com.ejulia.bookworm.service;

import com.ejulia.bookworm.model.Book;
import com.ejulia.bookworm.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public String addBook(String title, String author, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        bookRepository.save(book);
        return "Book registered";
    }

    public String deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
        return "Book deleted";
    }

    public Optional<Book> getBook(Integer bookId) {
        return bookRepository.findById(bookId);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
