package com.ejulia.bookworm.service;

import com.ejulia.bookworm.dao.LoanRepository;
import com.ejulia.bookworm.entity.Book;
import com.ejulia.bookworm.entity.Loan;
import com.ejulia.bookworm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    //No need to specify an isolation level on transactional tags since we want the default one: repeatable_read
    @Transactional
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

    @Transactional(readOnly = true)
    private boolean isAllowedLoan(Integer userId) {
        boolean loanAllowed = true;
        List<Loan> loanList = getUserCurrentLoans(userId);
        if (loanList.size() >= maxRentedBooks) {
            loanAllowed = false;
        }
        return loanAllowed;
    }

    @Transactional
    public String returnBook(Integer bookId) {
        Loan loan = loanRepository.findFirstByBook_BookIdOrderByRentTimeDesc(bookId);
        loan.setReturnTimeNow();
        loanRepository.save(loan);
        return "Book returned";
    }

    @Transactional(readOnly = true)
    public Loan getLoan(Integer loanId) throws Exception {
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        Loan loan = optionalLoan.orElseThrow(() -> new Exception("Loan not found"));
        return loan;
    }

    @Transactional(readOnly = true)
    public List<Loan> getBookLoans(Integer bookId) {
        return loanRepository.findByBook_BookId(bookId);
    }

    @Transactional(readOnly = true)
    public List<Loan> getUserLoans(Integer userId) {
        return loanRepository.findByUser_UserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Loan> getUserCurrentLoans(Integer userId) {
        return loanRepository.findByUser_UserIdAndReturnTimeIsNull(userId);
    }

    @Transactional(readOnly = true)
    public List<Loan> getCurrentLoans() {
        return loanRepository.findByReturnTimeIsNull();
    }

    @Transactional(readOnly = true)
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    ///// METHOD FOR DEBUG PURPOSE ONLY /////
    @Transactional
    public String setLoanOverdue(Integer loanId) throws Exception {
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);
        Loan overdueLoan = optionalLoan.orElseThrow(() -> new Exception("Loan not found"));
        overdueLoan.setRentTime(LocalDateTime.now().minusDays(20));
        return "Loan edited as overdue";
    }
}
