/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * class representing money withdrawal transactions
 * 
 */
public class Withdrawal extends Transaction {
    
    /**
     * empty constructor to enable XMLEncoder serialization
     */
    public Withdrawal() {
        super();
    }
    
    /**
     * Creates a new withdrawal transaction
     * @param initiatorAccount account of the customer that withdrew money
     * @param amount amount of money withdrawn
     * @param timestamp date and time of transaction
     */
    public Withdrawal(Account initiatorAccount, BigDecimal amount, Date timestamp) {
        super(initiatorAccount, amount, timestamp);
    }

    @Override
    public String toString() {
        return "[" + this.getStringTimestamp() + "] -" + this.getAmount().toString() + " (" + this.getInitiatorAccount().getIban() + ")";
    }
    
}
