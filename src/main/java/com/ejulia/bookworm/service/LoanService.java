package com.ejulia.bookworm.service;

import com.ejulia.bookworm.dao.LoanRepository;
import com.ejulia.bookworm.entity.Book;
import com.ejulia.bookworm.entity.Loan;
import com.ejulia.bookworm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    private int maxRentedBooks = 3;

    public String rentBook(Integer bookId, Integer userId) throws Exception {

        String returnStatement = "Loan not allowed";

        if (isAllowedLoan(userId)) {

            Loan loan = new Loan();

            Optional<User> optionalUser = userService.getUser(userId);
            // Define user as userOpt if it exists, else throw indicated exception
            User user = optionalUser.orElseThrow(() -> new Exception("User not found in loan creation"));
            loan.setUser(user);

            Optional<Book> optionalBook = bookService.getBook(bookId);
            Book book = optionalBook.orElseThrow(() -> new Exception("Book not found in loan creation"));
            loan.setBook(book);

            loan.setRentTimeNow();

            loanRepository.save(loan);

            returnStatement = "Book rented";
        }
        return returnStatement;
    }

    private boolean isAllowedLoan(Integer userId) {
        boolean loanAllowed = true;
        List<Loan> loanList = getUserCurrentLoans(userId);
        if (loanList.size() >= maxRentedBooks) {
            loanAllowed = false;
        }
        return loanAllowed;
    }

    public String returnBook(Integer bookId) {
        Loan loan = new Loan();
        loan = loanRepository.findFirstByBook_BookIdOrderByRentTimeDesc(bookId);
        loan.setReturnTimeNow();
        loanRepository.save(loan);
        return "Book returned";
    }

    public Loan getLoan(Integer loanId) throws Exception {
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        Loan loan = optionalLoan.orElseThrow(() -> new Exception("Loan not found"));
        return loan;
    }

    public List<Loan> getBookLoans(Integer bookId) {
        return loanRepository.findByBook_BookId(bookId);
    }

    public List<Loan> getUserLoans(Integer userId) {
        return loanRepository.findByUser_UserId(userId);
    }

    public List<Loan> getUserCurrentLoans(Integer userId) {
        return loanRepository.findByUser_UserIdAndReturnTimeIsNull(userId);
    }

    public List<Loan> getCurrentLoans() {
        return loanRepository.findByReturnTimeIsNull();
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

}
