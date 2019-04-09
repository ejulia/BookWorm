package com.ejulia.bookworm.dao;

import com.ejulia.bookworm.entity.Loan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoanRepository extends CrudRepository<Loan, Integer> {
    List<Loan> findAll();
    List<Loan> findByBook_BookId(Integer bookId);
    Loan findFirstByBook_BookIdOrderByRentTimeDesc(Integer bookId);
    List<Loan> findByUser_UserId(Integer userId);

    List<Loan> findByUser_UserIdAndReturnTimeIsNull(Integer userId);

    List<Loan> findByReturnTimeIsNull();
}
