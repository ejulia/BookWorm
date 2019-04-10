package com.ejulia.bookworm.controller;

import com.ejulia.bookworm.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/overdue")
public class OverdueController {

    @Autowired
    private LoanService loanService;

    ///// ENDPOINT FOR DEBUG PURPOSE ONLY /////
    @GetMapping(path = "/generate")
    public String addOverdue(@RequestParam Integer loanId) throws Exception {
        return loanService.setLoanOverdue(loanId);
    }
}
