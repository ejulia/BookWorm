package com.ejulia.bookworm.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Loan {

    @Id
    @GeneratedValue(generator = "increment")
    @Column(updatable = false, nullable = false)    // There is probably an overlap between those parameters and @Id
    private Integer loanId;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;

    @Column
    private LocalDateTime rentTime;

    @Column
    private LocalDateTime returnTime;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

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
