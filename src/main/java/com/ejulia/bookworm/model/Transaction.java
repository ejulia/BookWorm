package com.ejulia.bookworm.model;

import com.ejulia.bookworm.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(generator = "increment")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;

    private LocalDateTime rentTime;
    private LocalDateTime returnTime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getRentTime() {
        return rentTime;
    }

    public void setRentTime(LocalDateTime rentTime) {
        this.rentTime = rentTime;
    }

    public void setRentTimeNow() {
        this.rentTime = LocalDateTime.now();
    }

    public LocalDateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(LocalDateTime returnTime) {
        this.returnTime = returnTime;
    }

    public void setReturnTimeNow() {
        this.returnTime = LocalDateTime.now();
    }
}
