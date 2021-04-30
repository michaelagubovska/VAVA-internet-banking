/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 * Class representing an admin
 * 
 */
public class Admin extends User {
    
    /**
     * empty constructor to enable XMLEncoder serialization
     */
    public Admin() {
        super();
    }
    
    /**
     * Creates a new app admin
     * @param username login name
     * @param password login password
     * @param name first name
     * @param surname last name
     * @param address home address
     * @param email email address
     * @param birthdate date of birth
     */
    public Admin(String username, String password, String name, String surname, String address, String email, Date birthdate) {
        super(username, password, name, surname, address, email, birthdate);
    }
    
}
