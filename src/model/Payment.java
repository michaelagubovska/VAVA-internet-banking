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
 * Class representing payment transaction type
 * 
 */
public class Payment extends Transaction {
    
    private Account receiverAccount;
    
    /**
     * empty constructor to enable XMLEncoder serialization
     */
    public Payment() {
        super();
    }
    
    /**
     * Creates a new payment
     * @param initiatorAccount account of the customer that initiated the payment
     * @param amount amount of money paid
     * @param timestamp date and time of transaction
     * @param receiverAccount account of the customer that received the payment
     */
    public Payment(Account initiatorAccount, BigDecimal amount, Date timestamp, Account receiverAccount) {
        super(initiatorAccount, amount, timestamp);
        this.receiverAccount = receiverAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    @Override
    public String toString() {
        java.util.ResourceBundle bundle = MainFrame.getCurrentBundle();
        return "[" + this.getStringTimestamp() + "] " + this.getAmount().toString() + " " + bundle.getString(" OD ") + this.getInitiatorAccount().getIban() + " " + bundle.getString(" PRE ") + this.getReceiverAccount().getIban();
    }
    
}
