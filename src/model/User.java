/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Parent class representing all users of the app
 * 
 */
public abstract class User {
    
    private String username;
    private String password;
    private String name;
    private String surname;
    private String address;
    private String email;
    private Date birthdate;
    
    /**
     * empty constructor to enable XMLEncoder serialization
     */
    public User() {
         
    }

    /**
     * Creates a new user
     * @param username login name
     * @param password login password
     * @param name first name
     * @param surname last name
     * @param address home address
     * @param email email address
     * @param birthdate date of birth
     */
    public User(String username, String password, String name, String surname, String address, String email, Date birthdate) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.email = email;
        this.birthdate = birthdate;
    }
    
    /**
     *
     * @return date of birth in custom string format
     */
    public String getStringBirthdate() {
        DateFormat custom = new SimpleDateFormat("dd.MM.yyyy");
        return custom.format(birthdate);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    
}
