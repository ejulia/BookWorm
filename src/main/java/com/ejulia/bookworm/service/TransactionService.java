package com.ejulia.bookworm.service;

import com.ejulia.bookworm.dao.TransactionRepository;
import com.ejulia.bookworm.model.Book;
import com.ejulia.bookworm.model.Transaction;
import com.ejulia.bookworm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    private int maxRentedBooks = 3;

    public String rentBook(Integer bookId, Integer userId) throws Exception {

        String returnStatement = "Rental not allowed";

        if (isAllowedRent(userId)) {

            Transaction transaction = new Transaction();

            Optional<User> optionalUser = userService.getUser(userId);
            // Define user as userOpt if it exists, else throw indicated exception
            User user = optionalUser.orElseThrow(() -> new Exception("User not found in transaction creation"));
            transaction.setUser(user);

            Optional<Book> optionalBook = bookService.getBook(bookId);
            Book book = optionalBook.orElseThrow(() -> new Exception("Book not found in transaction creation"));
            transaction.setBook(book);

            transaction.setRentTimeNow();

            transactionRepository.save(transaction);

            returnStatement = "Book rented";
        }
        return returnStatement;
    }

    private boolean isAllowedRent(Integer userId) {
        boolean rentalAllowed = true;
        List<Transaction> transactionList = getUserCurrentTransactions(userId);
        if (transactionList.size() >= maxRentedBooks) {
            rentalAllowed = false;
        }
        return rentalAllowed;
    }

    public String returnBook(Integer bookId) {
//        List<Transaction> bookTransactions = transactionRepository.findByBook_BookId(bookId);
        Transaction transaction = new Transaction();
        transaction = transactionRepository.findFirstByBook_BookIdOrderByRentTimeDesc(bookId);
        transaction.setReturnTimeNow();
        transactionRepository.save(transaction);
        return "Book returned";
    }

    public Transaction getTransaction(Integer transactionId) throws Exception {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        Transaction transaction = optionalTransaction.orElseThrow(() -> new Exception("Transaction not found"));
        return transaction;
    }

    public List<Transaction> getBookTransactions(Integer bookId) {
        return transactionRepository.findByBook_BookId(bookId);
    }

    public List<Transaction> getUserTransactions(Integer userId) {
        return transactionRepository.findByUser_UserId(userId);
    }

    public List<Transaction> getUserCurrentTransactions(Integer userId) {
        return transactionRepository.findByUser_UserIdAndReturnTimeIsNull(userId);
    }

    public List<Transaction> getCurrentTransactions() {
        return transactionRepository.findByReturnTimeIsNull();
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
