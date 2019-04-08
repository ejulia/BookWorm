package com.ejulia.bookworm.dao;

import com.ejulia.bookworm.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findAll();
    List<Transaction> findByBook_BookId(Integer bookId);
    Transaction findFirstByBook_BookIdOrderByRentTimeDesc(Integer bookId);

}
