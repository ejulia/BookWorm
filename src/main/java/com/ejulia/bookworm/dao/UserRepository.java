package com.ejulia.bookworm.dao;

import com.ejulia.bookworm.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers to Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
    // Integer above means ID type

    // On redéfinie dans l'interface findAll() pour dire à JPA qu'on veut qu'elle retourne une liste et pas un iterable
    List<User> findAll();
}
