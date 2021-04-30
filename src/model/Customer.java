/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 * Class representing a customer
 * 
 */
public class Customer extends User {
    
    private Account account;
    
    /**
     * empty constructor to enable XMLEncoder serialization
     */
    public Customer() {
        super();
    }
    
    /**
     * Creates a new customer
     * @param username login name
     * @param password login password
     * @param name first name
     * @param surname last name
     * @param address home address
     * @param email email address
     * @param birthdate date of birth
     * @param account instance of Account owned by the customer
     */
    public Customer(String username, String password, String name, String surname, String address, String email, Date birthdate, Account account) {
        super(username, password, name, surname, address, email, birthdate);
        this.account = account;
    }

    @Override
    public String toString() {
        return this.getSurname() + " " + this.getName() + " (" + account.getIban() + ")";
    }
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
}
