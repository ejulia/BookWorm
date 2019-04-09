package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.entity.Loan;
import com.ejulia.bookworm.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping(path= "/rent")
    public String rentBook(@RequestParam Integer bookId, @RequestParam Integer userId) throws Exception {
        return loanService.rentBook(bookId, userId);
    }

    @GetMapping(path = "/return")
    public String returnBook (@RequestParam Integer bookId) throws Exception {
        return loanService.returnBook(bookId);
    }

    @GetMapping(path = "/book")
    public List<Loan> getBookLoans(@RequestParam Integer bookId) {
        return loanService.getBookLoans(bookId);
    }

    @GetMapping(path = "/user")
    public List<Loan> getUserLoans(@RequestParam Integer userId) {
        return loanService.getUserLoans(userId);
    }

    //AUTRE APPROCHE POSSIBLE : METTRE LE PARAMETRE DANS L'URI
    @GetMapping(path = "/rented")
    public List<Loan> getUserCurrentLoans(@RequestParam Integer userId) {
        return loanService.getUserCurrentLoans(userId);
    }

    @GetMapping(path = "/all")
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }
}
