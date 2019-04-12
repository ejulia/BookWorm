package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.entity.Loan;
import com.ejulia.bookworm.service.LoanService;
import com.ejulia.bookworm.service.OverdueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/overdue")
public class OverdueController {

    @Autowired
    private OverdueService overdueService;

    @Autowired
    private LoanService loanService;

    @GetMapping(path = "/current")
    public List<Loan> getCurrentOverdue() {
        return overdueService.getCurrentOverdue();
    }

    @GetMapping(path = "/current", params = "userId")
    public List<Loan> getCurrentUserOverdue(@RequestParam Integer userId) {
        return overdueService.getCurrentUserOverdue(userId);
    }

    @GetMapping(path = "/all")
    public List<Loan> getAllOverdue() {
        return overdueService.getAllOverdue();
    }

    @GetMapping(path = "/all", params = "userId")
    public List<Loan> getAllUserOverdue(@RequestParam Integer userId) {
        return overdueService.getAllUserOverdue(userId);
    }

    ///// ENDPOINT FOR DEBUG PURPOSE ONLY /////
    @GetMapping(path = "/debug/add")
    public String addOverdue(@RequestParam Integer userId, @RequestParam Integer bookId, @RequestParam Integer backwardDays, @RequestParam Boolean isClosed) throws Exception {
        return overdueService.addOverdue(userId, bookId, backwardDays, isClosed);
    }
}
