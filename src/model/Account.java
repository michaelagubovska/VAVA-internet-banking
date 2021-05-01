/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;

/**
 * Class representing bank accounts of customers
 *
 */
public class Account {
    
    private String type;
    private String iban;
    private BigDecimal balance;
    private Customer owner;

    /**
     * empty constructor to enable XMLEncoder serialization
     */
    public Account() {
    }
    
    /**
     * Creates a new bank account
     * @param type type of bank account
     * @param owner customer that owns this account
     * @param existing existing accounts for comparison - to generate a unique IBAN
     * @param bundle language I18N ResourceBuncle currently in use
     */
    public Account(String type, Customer owner, DefaultListModel existing, java.util.ResourceBundle bundle) {
        this.type = type;
        this.iban = createIban(existing);
        this.balance = BigDecimal.valueOf(0.0).setScale(2, RoundingMode.HALF_UP);
        this.owner = owner;
        
        if (type.equals(bundle.getString("ŠTUDENTSKÝ"))) this.balance = this.balance.add(BigDecimal.valueOf(20.00));
    }

    @Override
    public String toString() {
        return owner.getSurname() + " " + owner.getName() + " (" + iban + ")";
    }
    
    private String createIban(DefaultListModel input) {
        boolean unique = false;
        String newIban = "";
        
	while (unique == false) {
            unique = true;
            newIban = "SK052349000000";
			
            for (int i = 0; i < 10; i++) {
		newIban = newIban + (int)(Math.random()*10);
            }
            
            for (int index = 0; index < input.size(); index++) {
                if (((Account) input.get(index)).getIban().equals(newIban)) {
                    unique = false;
                    break;
                }
            }
          
	}
        
        return newIban;
    }

    public String getType() {
        return type;
    }
    
    /**
     * Overloaded account type getter
     * @param bundle language I18N ResourceBuncle currently in use
     * @return type of account in correct language
     */
    public String getType(java.util.ResourceBundle bundle) {
        return bundle.getString(this.type.toUpperCase());
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }
    
    private String getAccountNumber(String iban) {
        return iban.substring(14, iban.length());
    }

    private String odsadText(String text, int spaces) {
        return String.format("%1$" + String.valueOf(spaces) + "s", text);
    }
    
    private int odsadenieSuma(String initial, DefaultListModel accountTransactions) {
        int max = initial.length();
        for (int index = 0; index < accountTransactions.getSize(); index++) {
            Transaction t = (Transaction) accountTransactions.getElementAt(index);
            if (String.valueOf(t.getAmount()).length() > max) max = String.valueOf(t.getAmount()).length();
        }
        
        return max + 1;
    }
    
    private String date(Date date, java.util.ResourceBundle bundle) {
        SimpleDateFormat customFormat = new SimpleDateFormat(bundle.getString("DD.MM.YYYY HH:MM:SS"));
        return customFormat.format(date); 
    } 
    
    private String shortenPopis(String popis, int odsadenie) {
        if (popis.length() >= odsadenie) return popis.substring(0, odsadenie - 4) + "...";
        return popis;
    }
    
    /**
     * Generates an account report and writes it into a text file
     * @param accountTransactions list of transactions of the account
     * @param from date which all written transactions have to exceed
     * @param to date which all written transactions cannot exceed
     * @param bundle language I18N ResourceBundle currently in use
     * @return true if file saved successfully, otherwise false
     */
    public boolean generateAccountReport(DefaultListModel accountTransactions, Date from, Date to, java.util.ResourceBundle bundle) {
        try {          
            SimpleDateFormat fileNameFormat = new SimpleDateFormat(bundle.getString("DD_MM_YYYY"));
            String name = "report_" + this.getOwner().getSurname() + "_" + fileNameFormat.format(from) + "-" + fileNameFormat.format(to) + ".txt";
            String accountNumber = getAccountNumber(this.getIban());
            String currentDate = date(new Date(System.currentTimeMillis()), bundle);
            
            int odsadenieLabel = 14;    // dlzka pre odsadenie hlavicky s udajmi
            
            // magicka konstanta pre vzdialenost udajov zakaznika a banky
            int odsadenieLudia = 70 - odsadenieLabel;
            
            int odsadenieDatum = currentDate.length() + 1;
            int odsadeniePopis = odsadenieLudia + odsadenieLabel - odsadenieDatum - ("| ").length();
            int odsadenieSuma = odsadenieSuma(bundle.getString("SUMA"), accountTransactions);
            
            String oddelovac = "|------------------------------------------------------------------------------------------------------------------\n";
            
            FileWriter fw = new FileWriter(name);
            
            fw.write(oddelovac);
            fw.write("| RayBank, " + currentDate + "\n|\n");
            fw.write(java.text.MessageFormat.format(bundle.getString("| ČÍSLO ÚČTU: {0}"), new Object[] {accountNumber}) + "\n");
            fw.write(java.text.MessageFormat.format(bundle.getString("| TYP ÚČTU: {0}"), new Object[] {this.getType(bundle)}) + "\n");
            fw.write(oddelovac);
            fw.write("| " + odsadText(bundle.getString("KLIENT"), -odsadenieLabel - odsadenieLudia) + "| RayBank, a.s\n");
            fw.write("| " + odsadText(bundle.getString("MENO:"), -odsadenieLabel) + odsadText(this.getOwner().getName(), -odsadenieLudia) + "| Rybničná 8, 811 06 Bratislava\n");
            fw.write("| " + odsadText(bundle.getString("PRIEZVISKO:"), -odsadenieLabel) + odsadText(this.getOwner().getSurname(), -odsadenieLudia) + "| IČO: 00797041\n");
            fw.write("| " + odsadText(bundle.getString("ADRESA:"), -odsadenieLabel) + odsadText(this.getOwner().getAddress(), -odsadenieLudia) + "| DIČ: 3131519633\n");
            fw.write(oddelovac);
            fw.write(java.text.MessageFormat.format(bundle.getString("INFOMESSAGE {0} {1}"), new Object[] {date(from, bundle), date(to, bundle)}));
            fw.write(oddelovac);
            fw.write("| " + odsadText(bundle.getString("ČÍSLO ÚČTU"), -odsadenieLabel - odsadenieLudia) + bundle.getString("ZOSTATOK") + "\n");
            fw.write("| " + odsadText(accountNumber, -odsadenieLabel - odsadenieLudia) + this.getBalance().toString() + " €\n");
            fw.write(oddelovac);
            fw.write("| " + odsadText(bundle.getString("DÁTUM"), -odsadenieDatum) + "| " + odsadText(bundle.getString("POPIS"), -odsadeniePopis) + "| " + odsadText(bundle.getString("SUMA"), -odsadenieSuma) + "\n");
            fw.write(oddelovac);
            for (int index = 0; index < accountTransactions.getSize(); index++) {
                Transaction t = (Transaction) accountTransactions.getElementAt(index);
                Date transactionTime = t.getTimestamp();
                if ((transactionTime.after(from) || transactionTime.equals(from)) && (transactionTime.before(to) || transactionTime.equals(to))) {
                    if (t instanceof Payment) {
                        if (((Payment) t).getReceiverAccount().getIban().equals(this.getIban())) {
                            fw.write("| " + odsadText(date(transactionTime, bundle), -odsadenieDatum) + "| " + odsadText(shortenPopis(bundle.getString("PRIJATÉ OD ") + ((Payment) t).getInitiatorAccount().getIban(), odsadeniePopis), -odsadeniePopis) + odsadText(t.getAmount().toString(), odsadenieSuma) + " €\n");
                        
                        } else {
                            fw.write("| " + odsadText(date(transactionTime, bundle), -odsadenieDatum) + "| " + odsadText(shortenPopis(bundle.getString("ODOSLANÉ PRE ") + ((Payment) t).getReceiverAccount().getIban(), odsadeniePopis), -odsadeniePopis) + odsadText(t.getAmount().toString(), odsadenieSuma) + " €\n");
                        }
                        
                    } else if ( t instanceof Deposit && ((Deposit)t).isConfirmed()) {
                        fw.write("| " + odsadText(date(transactionTime, bundle), -odsadenieDatum) + "| " + odsadText(bundle.getString("VKLAD NA ÚČET"), -odsadeniePopis) + odsadText(t.getAmount().toString(), odsadenieSuma) + " €\n");
                    
                    } else if ( t instanceof Withdrawal) {
                        fw.write("| " + odsadText(date(transactionTime, bundle), -odsadenieDatum) + "| " + odsadText(bundle.getString("VÝBER Z ÚČTU"), -odsadeniePopis) + odsadText(t.getAmount().toString(), odsadenieSuma) + " €\n");
                    }
                }
            }
            
            fw.write(oddelovac);
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
