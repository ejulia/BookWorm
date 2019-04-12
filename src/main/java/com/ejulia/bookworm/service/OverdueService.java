package com.ejulia.bookworm.service;

import com.ejulia.bookworm.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OverdueService {

    @Autowired
    private LoanService loanService;

    private Integer authorizedLoanPeriod = 20;

    private List<Loan> filterOverdueFromLoans(List<Loan> loanList) {
        return loanList.stream().filter( loan ->
                ( loan.getReturnTime() == null && loan.getRentTime().isBefore(LocalDateTime.now().minusDays(authorizedLoanPeriod)) ) ||
                ( loan.getReturnTime() != null && loan.getReturnTime().isAfter(loan.getRentTime().plusDays(authorizedLoanPeriod)) )
                ).collect(Collectors.toList());
    }

    public List<Loan> getCurrentOverdue() {
        List<Loan> currentLoans = loanService.getCurrentLoans();
        return filterOverdueFromLoans(currentLoans);
    }

    public List<Loan> getCurrentUserOverdue(Integer userId) {
        List<Loan> currentUserLoans = loanService.getUserCurrentLoans(userId);
        return filterOverdueFromLoans(currentUserLoans);
    }

    public List<Loan> getAllOverdue() {
        List<Loan> allLoans = loanService.getAllLoans();
        return filterOverdueFromLoans(allLoans);
    }

    public List<Loan> getAllUserOverdue(Integer userId) {
        List<Loan> allUserLoans = loanService.getUserLoans(userId);
        return filterOverdueFromLoans(allUserLoans);
    }

    /// METHOD FOR DEBUG PURPOSE ONLY ///
    public String addOverdue(Integer userId, Integer bookId, Integer backwardDays, Boolean isClosed) throws Exception {
        return loanService.addOverdueLoan(userId, bookId, backwardDays, isClosed);
    }
}
