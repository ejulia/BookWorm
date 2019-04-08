package com.ejulia.bookworm.model;

import javax.persistence.*;
import java.util.Set;

@Entity //Tells Hibernate to make a table out of this class
public class User {

    @Id
    @GeneratedValue(generator = "increment")
    private Integer userId;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    // Defines a set which will contains all the transactions related to the instantiated user
    // Tells JPA where to find the @ManyToOne configuration defined in Transaction
    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactionSet;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
