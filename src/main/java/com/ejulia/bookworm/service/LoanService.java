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
    @Autowired
    private OverdueService overdueService;

    private int maxRentedBooks = 3;

    // Warning due to debug method below
    @SuppressWarnings("Duplicates")
    //No need to specify an isolation level on transactional tags since we want the default one: repeatable_read
    @Transactional
    public String rentBook(Integer bookId, Integer userId) throws Exception {

        String returnStatement = "Loan not allowed";

/*        if (isRented(bookId)) {
            returnStatement = "Book already borrowed";
        } else */if (isAllowedLoan(userId)) {
            Loan loan = new Loan();

            Optional<User> optionalUser = userService.getUser(userId);
            User user = optionalUser.orElseThrow(() -> new Exception("User not found in loan creation"));
            loan.setUser(user);

            loan.setBook(bookService.getBook(bookId));

            loan.setRentTimeNow();
            loanRepository.save(loan);
            returnStatement = "Book rented";
        }

        return returnStatement;
    }

    @Transactional(readOnly = true)
    protected boolean isAllowedLoan(Integer userId) {
        boolean loanAllowed = true;

        List<Loan> loanList = getUserCurrentLoans(userId);
        if (loanList.size() >= maxRentedBooks || overdueService.getCurrentUserOverdue(userId).size() > 0) {
            loanAllowed = false;
        }
        return loanAllowed;
    }

    @Transactional(readOnly = true)
    protected boolean isRented(Integer bookId) {
        Boolean isRented = false;
        List<Book> rentedBooks = bookService.getRentedBooks();
        if (rentedBooks.stream().filter(book -> book.getBookId().equals(bookId)).findAny().orElse(null).equals(null)) {
            isRented = true;
        }
        return isRented;
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
    @SuppressWarnings("Duplicates")
    @Transactional
    public String addOverdueLoan(Integer userId, Integer bookId, Integer backwardDays, Boolean isClosed) throws Exception {

        Loan loan = new Loan();

        Optional<User> optionalUser = userService.getUser(userId);
        // Define user as userOpt if it exists, else throw indicated exception
        User user = optionalUser.orElseThrow(() -> new Exception("User not found in loan creation"));
        loan.setUser(user);

        loan.setBook(bookService.getBook(bookId));

        loan.setRentTime(LocalDateTime.now().minusDays(50 + backwardDays));

        if (isClosed) {
            loan.setReturnTime(loan.getRentTime().plusDays(30));
        }

        loanRepository.save(loan);

        return "Overdue loan added";
    }
}
