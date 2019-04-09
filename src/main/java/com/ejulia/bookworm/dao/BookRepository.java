package com.ejulia.bookworm.dao;

import com.ejulia.bookworm.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Integer> {
    //Redefine findAll() output from Iterable to List :
    List<Book> findAll();
    List<Book> findByTitleContaining(String title);
    List<Book> findByAuthorContaining(String author);
}
