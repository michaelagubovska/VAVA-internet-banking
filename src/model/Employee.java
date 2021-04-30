/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 * Class representing an employee
 * 
 */
public class Employee extends User {
    
    private String position;
    
    /**
     * empty constructor to enable XMLEncoder serialization
     */
    public Employee() {
        super();
    }
    
    /**
     * Creates a new employee
     * @param username login name
     * @param password login password
     * @param name first name
     * @param surname last name
     * @param address home address
     * @param email email address
     * @param birthdate date of birth
     * @param position work position
     */
    public Employee(String username, String password, String name, String surname, String address, String email, Date birthdate, String position) {
        super(username, password, name, surname, address, email, birthdate);
        this.position = position;
    }

    @Override
    public String toString() {
        return this.getSurname() + " " + this.getName() + " (" + position + ")";
    }
    
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
}
