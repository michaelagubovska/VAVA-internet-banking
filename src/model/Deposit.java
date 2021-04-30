/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.util.Date;
import view.MainFrame;

/**
 * Class representing the deposit transaction type
 * 
 */
public class Deposit extends Transaction {
    
    private boolean confirmed;
    
    /**
     * empty constructor to enable XMLEncoder serialization
     */
    public Deposit() {
        super();
    }
    
    /**
     * Creates a new deposit transaction
     * @param initiatorAccount account of the customer that deposited money
     * @param amount amount of money deposited
     * @param timestamp date and time of transaction
     */
    public Deposit(Account initiatorAccount, BigDecimal amount, Date timestamp) {
        super(initiatorAccount, amount, timestamp);
        this.confirmed = false;
    }

    /**
     *
     * @return true if an employee confirmed the deposit / false if the deposit awaits confirmation by an employee
     */
    public boolean isConfirmed() {
        return confirmed;
    }

    /**
     *
     * @param confirmed whether an employee confirmed the deposit or not
     */
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        java.util.ResourceBundle bundle = MainFrame.getCurrentBundle();
        if (this.confirmed) {
            return "[" + this.getStringTimestamp() + "] +" + this.getAmount().toString() + " (" + this.getInitiatorAccount().getIban() + ")";
        } else {
            return "(" + bundle.getString("NEPOTVRDENÉ") + ") [" + this.getStringTimestamp() + "] +" + this.getAmount().toString() + " (" + this.getInitiatorAccount().getIban() + ")";
        }
    }
    
}
