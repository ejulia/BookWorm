package com.ejulia.bookworm.dao;

import com.ejulia.bookworm.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    //Redefine findAll() output from Iterable to List :
    List<Book> findAll();
}
