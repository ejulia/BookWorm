package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.model.Transaction;
import com.ejulia.bookworm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping(path= "/rent")
    public String rentBook(@RequestParam Integer bookId, @RequestParam Integer userId) throws Exception {
        return transactionService.rentBook(bookId, userId);
    }

    @GetMapping(path = "/return")
    public String returnBook (@RequestParam Integer bookId) throws Exception {
        return transactionService.returnBook(bookId);
    }

    @GetMapping(path = "/book")
    public List<Transaction> getBookTransactions(@RequestParam Integer bookId) {
        return transactionService.getBookTransactions(bookId);
    }

    @GetMapping(path = "/user")
    public List<Transaction> getUserTransactions(@RequestParam Integer userId) {
        return transactionService.getUserTransactions(userId);
    }

    //AUTRE APPROCHE POSSIBLE : METTRE LE PARAMETRE DANS L'URI
    @GetMapping(path = "/rented")
    public List<Transaction> getUserCurrentTransactions(@RequestParam Integer userId) {
        return transactionService.getUserCurrentTransactions(userId);
    }

    @GetMapping(path = "/all")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
