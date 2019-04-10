package com.ejulia.bookworm.service;

import com.ejulia.bookworm.entity.Book;
import com.ejulia.bookworm.dao.BookRepository;
import com.ejulia.bookworm.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LoanService loanService;

    @Transactional
    public String addBook(String title, String author, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        bookRepository.save(book);
        return "Book registered";
    }

    @Transactional
    public String deleteBook(Integer bookId) {
        bookRepository.deleteById(bookId);
        return "Book deleted";
    }

    @Transactional(readOnly=true)
    public Optional<Book> getBook(Integer bookId) {
        return bookRepository.findById(bookId);
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> getByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    @Transactional(readOnly = true)
    public List<Book> getByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }

    @Transactional(readOnly = true)
    public List<Book> getRentedBooks() {
        List<Loan> loanList = loanService.getCurrentLoans();
        return loanList.stream().map(Loan::getBook).collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public List<Book> getAvailableBooks() {
        List<Book> allBooks = bookRepository.findAll();
        List<Loan> currentLoans = loanService.getCurrentLoans();
        List<Book> rentedBooks = currentLoans.stream().map(Loan::getBook).collect(Collectors.toList());
        return allBooks.stream().filter(book -> !rentedBooks.contains(book)).collect(Collectors.toList());
    }

    @Transactional
    public String editBook(Integer bookId, String title, String author, String isbn) throws Exception {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new Exception("Book not found"));
        if (!title.isEmpty()) {book.setTitle(title); }
        if (!author.isEmpty()) {book.setAuthor(author); }
        if (!isbn.isEmpty()) {book.setIsbn(isbn); }
        bookRepository.save(book);
        return "Book edited";
    }
}
