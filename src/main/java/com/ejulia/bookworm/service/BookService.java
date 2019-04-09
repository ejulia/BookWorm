package com.ejulia.bookworm.service;

import com.ejulia.bookworm.model.Book;
import com.ejulia.bookworm.dao.BookRepository;
import com.ejulia.bookworm.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TransactionService transactionService;

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

    public List<Book> getByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> getByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author);
    }

    public List<Book> getRentedBooks(Integer userId) {
        List<Transaction> transactionList = transactionService.getUserCurrentTransactions(userId);
        // map = ce que je vais faire : je mets get book dans le stream
        // Collect permet de récupérer le contenu du stream au fur et à mesure
        return transactionList.stream().map(Transaction::getBook).collect(Collectors.toList());
    }

    public List<Book> getAvailableBooks() {

        List<Book> allBooks = bookRepository.findAll();
        List<Transaction> currentTransactions = transactionService.getCurrentTransactions();
        List<Book> rentedBooks = currentTransactions.stream().map(Transaction::getBook).collect(Collectors.toList());
        return allBooks.stream().filter(book -> !rentedBooks.contains(book)).collect(Collectors.toList());
    }

    public void editBookTitle(Integer bookId, String title) throws Exception {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new Exception("Book not found"));
        book.setTitle(title);
        bookRepository.save(book);
    }

    public void editBookAuthor(Integer bookId, String author) throws Exception {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new Exception("Book not found"));
        book.setAuthor(author);
        bookRepository.save(book);
    }

    public void editBookIsbn(Integer bookId, String isbn) throws Exception {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new Exception("Book not found"));
        book.setIsbn(isbn);
        bookRepository.save(book);
    }
}
