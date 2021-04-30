/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.CardLayout;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import model.Account;
import model.Admin;
import model.Customer;
import model.Deposit;
import model.Employee;
import model.Payment;
import model.Transaction;
import model.User;
import model.Withdrawal;

/**
 *Main GUI form class
 * 
 */
public class MainFrame extends javax.swing.JFrame {
    
    private static java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("view/Bundle_SVK");;
    
    private final File serializeFile;
    
    private static final Logger logger = Logger.getLogger(MainFrame.class.getName());
    private FileHandler loggerFileHandler;
    
    private final CardLayout layout;
    private final CardLayout actionLayout;
    
    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Transaction> transactionList = new ArrayList<>();
    
    private final DefaultListModel accountList = new DefaultListModel();
    private final DefaultListModel employeeList;
    private final DefaultListModel transactionSubList = new DefaultListModel();
    private final DefaultListModel depositList = new DefaultListModel();
    
    private final DefaultComboBoxModel customerComboList = new DefaultComboBoxModel();
    private final DefaultComboBoxModel receiverComboList = new DefaultComboBoxModel();
    
    private final ArrayList<AccountDetail> accDetailList = new ArrayList<>();
    private final ArrayList<EmployeeDetail> empDetailList = new ArrayList<>();
    private final ArrayList<ReportPopup> reportPopupList = new ArrayList<>();
    
    private User currentUser = null;

    /**
     * Creates the main GUI form
     */
    public MainFrame() {
        this.employeeList = new DefaultListModel();
        initComponents();
        internationalize();
        
        layout = (CardLayout) containerPanel.getLayout();
        actionLayout = (CardLayout) actionContainerPanel.getLayout();
        layout.show(containerPanel, bundle.getString("CARD2"));
        
        lbLoginImage.setIcon(new ImageIcon(MainFrame.class.getResource(bundle.getString("/IMAGES/LOGO_IMAGE.PNG"))));
        lbCustomerActionImage.setIcon(new ImageIcon(MainFrame.class.getResource(bundle.getString("/IMAGES/LOGO_IMAGE.PNG"))));
        lbRegisterImage.setIcon(new ImageIcon(MainFrame.class.getResource(bundle.getString("/IMAGES/REGISTER_IMAGE.PNG")))); 
        
        serializeFile = new File(System.getProperty(bundle.getString("USER.DIR")) + "/serialization.xml");
        
        deserialize();
        createAdmin();
        
        logToFile(logger, bundle.getString("LOG.TXT"));
        logger.log(Level.INFO, bundle.getString("---- APP SESSION OPENED ----"));
    }
    
    private void internationalize() {
        setTitle(bundle.getString("RAYBANK - VITAJTE"));
        lbNewCustomer.setText(bundle.getString("NOVÝ ZÁKAZNÍK? VYTVORTE SI ÚČET RÝCHLO A JEDNODUCHO!"));
        btRegister.setText(bundle.getString("REGISTROVAŤ SA"));
        lbUsername.setText(bundle.getString("PRIHLASOVACIE MENO:"));
        lbUserPassword.setText(bundle.getString("VAŠE HESLO:"));
        btLogin.setText(bundle.getString("PRIHLÁSIŤ SA"));
        btSlovak.setText(bundle.getString("SLOVENČINA"));
        btEnglish.setText(bundle.getString("ENGLISH"));
        btRegisterBack.setText(bundle.getString("SPAŤ"));
        lbRegisterName.setText(bundle.getString("MENO:"));
        lbRegisterSurname.setText(bundle.getString("PRIEZVISKO:"));
        lbRegisterAddress.setText(bundle.getString("BYDLISKO:"));
        lbBirthDate.setText(bundle.getString("DÁTUM NARODENIA:"));
        lbRegisterEmail.setText(bundle.getString("EMAIL:"));
        lbRegisterUsername.setText(bundle.getString("POUŽÍVATEĽSKÉ MENO:"));
        lbRegisterPassword.setText(bundle.getString("HESLO:"));
        btRegisterConfirm.setText(bundle.getString("REGISTROVAŤ SA"));
        lbAccountType.setText(bundle.getString("TYP ÚČTU:"));
        cbAccountType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { bundle.getString("BEŽNÝ"), bundle.getString("ŠTUDENTSKÝ") }));
        btRemoveAccount.setText(bundle.getString("ZRUŠIŤ ÚČET"));
        btAdminAccountDetail.setText(bundle.getString("DETAIL"));
        btRemoveEmployee.setText(bundle.getString("ODSTRÁNIŤ"));
        lbAccountList.setText(bundle.getString("ZOZNAM ÚČTOV"));
        lbEmployeeList.setText(bundle.getString("ZOZNAM ZAMESTNANCOV"));
        btAdminEmployeeDetail.setText(bundle.getString("DETAIL"));
        lbRegisterEmployeeName.setText(bundle.getString("MENO:"));
        lbRegisterEmployeeSurname.setText(bundle.getString("PRIEZVISKO:"));
        lbRegisterEmployeeAddress.setText(bundle.getString("BYDLISKO:"));
        lbEmployeeBirthDate.setText(bundle.getString("DÁTUM NARODENIA:"));
        lbRegisterEmployeeEmail.setText(bundle.getString("EMAIL:"));
        lbRegisterEmployeeUsername.setText(bundle.getString("POUŽÍVATEĽSKÉ MENO:"));
        lbRegisteremployeePassword.setText(bundle.getString("HESLO:"));
        btRegisterEmployeeConfirm.setText(bundle.getString("VYTVORIŤ"));
        lbNewEmployeePosition.setText(bundle.getString("POZÍCIA:"));
        lbNewEmployee.setText(bundle.getString("NOVÝ ZAMESTNANEC"));
        btAdminLogout.setText(bundle.getString("ODHLÁSIŤ SA"));
        lbEmployeePayList.setText(bundle.getString("ČAKAJÚCE VKLADY NA ÚČTY"));
        btConfirmDeposit.setText(bundle.getString("POTVRDIŤ"));
        lbEmployeePayments.setText(bundle.getString("POHYBY NA ÚČTE"));
        lbEmployeeChooseCustomer.setText(bundle.getString("ZÁKAZNÍK:"));
        btEmployeeCustomerDetail.setText(bundle.getString("DETAIL"));
        btEmployeeLogout.setText(bundle.getString("ODHLÁSIŤ SA"));
        lbTranasctions.setText(bundle.getString("ZOZNAM TRANSAKCIÍ"));
        lbCustomerIban.setText(bundle.getString("IBAN:"));
        lbCustomerBalance.setText(bundle.getString("ZOSTATOK:"));
        btPayment.setText(bundle.getString("PLATBA"));
        btWithdrawal.setText(bundle.getString("VÝBER"));
        btDeposit.setText(bundle.getString("VKLAD"));
        btReport.setText(bundle.getString("VÝPIS"));
        btCustomerLogout.setText(bundle.getString("ODHLÁSIŤ SA"));
        lbSender.setText(bundle.getString("ODOSIELATEĽ"));
        lbName.setText(bundle.getString("MENO:"));
        lbSurname.setText(bundle.getString("PRIEZVISKO:"));
        lbIban.setText(bundle.getString("IBAN:"));
        lbBalance.setText(bundle.getString("ZOSTATOK:"));
        lbReceiver.setText(bundle.getString("PRÍJEMCA:"));
        lbReceiverIban.setText(bundle.getString("IBAN:"));
        lbPayment.setText(bundle.getString("SUMA:"));
        btCancelPayment.setText(bundle.getString("ZRUŠIŤ"));
        btPay.setText(bundle.getString("ZAPLATIŤ"));
        lbDeposit.setText(bundle.getString("SUMA:"));
        btCancelDeposit.setText(bundle.getString("ZRUŠIŤ"));
        btDeposit1.setText(bundle.getString("VLOŽIŤ"));
        lbWithdrawal.setText(bundle.getString("SUMA:"));
        btCancelWithdrawal.setText(bundle.getString("ZRUŠIŤ"));
        btWithdrawal1.setText(bundle.getString("VYBRAŤ"));
        lbRegisterImage.setIcon(new ImageIcon(MainFrame.class.getResource(bundle.getString("/IMAGES/REGISTER_IMAGE.PNG")))); 
    }
    
    /**
     * Getter enabling other classes to access this forms language bundle
     * @return language I18N ResourceBuncle currently in use
     */
    public static java.util.ResourceBundle getCurrentBundle() {
        return bundle;
    }
    
    private void logToFile(Logger logger, String name) {
        try {   
            loggerFileHandler = new FileHandler(System.getProperty(bundle.getString("USER.DIR")) + "/" + name, true);  
            logger.addHandler(loggerFileHandler);
            SimpleFormatter formatter = new SimpleFormatter();  
            loggerFileHandler.setFormatter(formatter);
            
        } catch (SecurityException | IOException e) {  
            
        }  
    }
    
    private void serialize() {
        XMLEncoder encoder = null;
        try {
            encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(serializeFile)));
            encoder.setPersistenceDelegate(java.math.BigDecimal.class, encoder.getPersistenceDelegate(Integer.class));
        } catch(FileNotFoundException e) {
            System.out.println(bundle.getString("NENAŠIEL SA SÚBOR PRE SERIALIZÁCIU."));
            logger.log(Level.SEVERE, bundle.getString("SERIALIZATION FAILED. FILE NOT FOUND OR NOT CREATED."));
        }
        
        if (encoder != null) {
            encoder.writeObject(this.userList);
            encoder.writeObject(this.transactionList);
            encoder.close();
        } 
    }
    
    private void deserialize() {
        XMLDecoder decoder = null;
        try {
            decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(serializeFile)));
        } catch(FileNotFoundException e) {
            System.out.println(bundle.getString("NENAŠIEL SA SÚBOR PRE SERIALIZÁCIU, VYTVÁRA SA NOVÝ."));
            logger.log(Level.INFO, bundle.getString("SERIALIZATION FILE WAS NOT FOUND, CREATING NEW FILE."));
        }
        
        if (decoder != null) {
            userList = (ArrayList<User>) decoder.readObject();
            transactionList = (ArrayList<Transaction>) decoder.readObject();
            decoder.close();
        } 
        
        for (User u : userList) {
            if (u instanceof Customer) {
                accountList.addElement(((Customer) u).getAccount());
                customerComboList.addElement((Customer) u);
            } else if (u instanceof Employee) {
                employeeList.addElement((Employee) u);
            }
        }
        
        for (Transaction t : transactionList) {
            if (t instanceof Deposit && !((Deposit) t).isConfirmed()) depositList.addElement((Deposit) t);
        }
    }
    
    private void createAdmin() {
        Admin toAdd = new Admin("admin", "nbusr123", "Adam", "Adminovič", "Staromestská 3, 12345 Šaštín Stráže", "adam.admin@raybank.sk", createDate("1989-07-18"));
        boolean exists = false;
        for (User u : userList) {
            if (toAdd.getUsername().equals(u.getUsername()) && toAdd.getPassword().equals(u.getPassword())) {
                exists = true;
                break;
            }
        }
        
        if (!exists) {
            userList.add(toAdd);
            logger.log(Level.INFO, bundle.getString("ADMIN ACCOUNT WAS MISSING, CREATED NEW ADMIN ACCOUNT FROM SCRATCH."));
        }
    }
    
    /**
     * Creates a date object from an input string
     * @param input string with a date in a custom format (yyyy-mm-dd)
     * @return date object based on the input string if the format is correct, otherwise null
     */
    public Date createDate(String input) {
        try {
            return new SimpleDateFormat(bundle.getString("YYYY-MM-DD")).parse(input);
        } catch (ParseException e) {
            return null;
        }
    }
    
    private void resetRegistration() {
        tfRegisterUsername.setText(bundle.getString(""));
        tfRegisterPassword.setText(bundle.getString(""));
        tfRegisterName.setText(bundle.getString(""));
        tfRegisterSurname.setText(bundle.getString(""));
        tfRegisterAddress.setText(bundle.getString(""));
        tfRegisterEmail.setText(bundle.getString(""));
        spinnerBirthDate.setValue(new Date(System.currentTimeMillis()));
    }
    
    private void resetLogin() {
        tfUsername.setText(bundle.getString(""));
        pfPassword.setText(bundle.getString(""));
    }
    
    private void resetAddEmployee() {
        tfRegisterEmployeeUsername.setText(bundle.getString(""));
        tfRegisterEmployeePassword.setText(bundle.getString(""));
        tfRegisterEmployeeName.setText(bundle.getString(""));
        tfRegisterEmployeeSurname.setText(bundle.getString(""));
        tfRegisterEmployeeAddress.setText(bundle.getString(""));
        tfRegisterEmployeeEmail.setText(bundle.getString(""));
        tfRegisterEmployeePosition.setText(bundle.getString(""));
        spinnerAdminEmployee.setValue(new Date(System.currentTimeMillis()));
    }
    
    private void resetCustomer() {
        lbGreeting.setText(bundle.getString(""));
        lbCustomerAccountType.setText(bundle.getString(""));
        tfCustomerIban.setText(bundle.getString(""));
        tfCustomerBalance.setText(bundle.getString(""));
        tfName.setText(bundle.getString(""));
        tfSurname.setText(bundle.getString(""));
        tfIban.setText(bundle.getString(""));
        tfBalance.setText(bundle.getString(""));
        
        receiverComboList.removeAllElements();
        transactionSubList.removeAllElements();
    }
    
    private void resetEmployeeCustomerList() {      
        int selected = cbEmployeeCustomer.getSelectedIndex();
        if (selected >= 0) {
            createTransactionSubList(transactionSubList, ((Customer) customerComboList.getElementAt(selected)).getAccount());
        }
    }
    
    private boolean checkUniqueUsername(String username) {
        boolean unique = true;
        for (User u : userList) {
            if (u.getUsername().equals(username)) {
                unique = false;
                break;
            }
        }
        
        return unique;
    }
    
    private boolean validateEmailAddress(String email) {
        String regex = "^[A-Za-z0-9'/\\{\\}|~ ?#:!$%_\\+-]+[A-Za-z0-9'/\\{\\}|~ ?#:!$%_\\+.-]*@[A-Za-z0-9'/\\{\\}|~ ?#:!$%_\\+-]+[.][a-z0-9-]+$";
        return Pattern.compile(regex).matcher(email).matches(); 
    }
    
    private void createTransactionSubList(DefaultListModel model, Account a) {
        transactionSubList.removeAllElements();
        for (Transaction t : transactionList) {
            if ( (t instanceof Payment && (a.equals(t.getInitiatorAccount()) || a.equals(((Payment) t).getReceiverAccount()))) || a.equals(t.getInitiatorAccount()) ) {
                model.addElement(t);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        containerPanel = new javax.swing.JPanel();
        loginPanel = new javax.swing.JPanel();
        loginImagePanel = new javax.swing.JPanel();
        lbLoginImage = new javax.swing.JLabel();
        formPanel = new javax.swing.JPanel();
        newUserPanel = new javax.swing.JPanel();
        lbNewCustomer = new javax.swing.JLabel();
        btRegister = new javax.swing.JButton();
        userLoginPanel = new javax.swing.JPanel();
        lbUsername = new javax.swing.JLabel();
        tfUsername = new javax.swing.JTextField();
        lbUserPassword = new javax.swing.JLabel();
        btLogin = new javax.swing.JButton();
        pfPassword = new javax.swing.JPasswordField();
        languagePanel = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(15, 0), new java.awt.Dimension(180, 35), new java.awt.Dimension(15, 32767));
        btSlovak = new javax.swing.JButton();
        btEnglish = new javax.swing.JButton();
        registerPanel = new javax.swing.JPanel();
        registerImagePanel = new javax.swing.JPanel();
        lbRegisterImage = new javax.swing.JLabel();
        registerFormHolderPanel = new javax.swing.JPanel();
        registerBackPanel = new javax.swing.JPanel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(250, 0), new java.awt.Dimension(330, 0), new java.awt.Dimension(250, 32767));
        btRegisterBack = new javax.swing.JButton();
        registerFormPanel = new javax.swing.JPanel();
        lbRegisterName = new javax.swing.JLabel();
        tfRegisterName = new javax.swing.JTextField();
        lbRegisterSurname = new javax.swing.JLabel();
        tfRegisterAddress = new javax.swing.JTextField();
        lbRegisterAddress = new javax.swing.JLabel();
        tfRegisterSurname = new javax.swing.JTextField();
        lbBirthDate = new javax.swing.JLabel();
        spinnerBirthDate = new javax.swing.JSpinner();
        lbRegisterEmail = new javax.swing.JLabel();
        tfRegisterEmail = new javax.swing.JTextField();
        lbRegisterUsername = new javax.swing.JLabel();
        tfRegisterUsername = new javax.swing.JTextField();
        lbRegisterPassword = new javax.swing.JLabel();
        tfRegisterPassword = new javax.swing.JTextField();
        btRegisterConfirm = new javax.swing.JButton();
        lbAccountType = new javax.swing.JLabel();
        cbAccountType = new javax.swing.JComboBox<>();
        adminPanel = new javax.swing.JPanel();
        adminListPanel = new javax.swing.JPanel();
        scrollAdminAccounts = new javax.swing.JScrollPane();
        listAdminAccounts = new javax.swing.JList<>();
        btRemoveAccount = new javax.swing.JButton();
        btAdminAccountDetail = new javax.swing.JButton();
        scrollAdminEmployees = new javax.swing.JScrollPane();
        listAdminEmployees = new javax.swing.JList<>();
        btRemoveEmployee = new javax.swing.JButton();
        lbAccountList = new javax.swing.JLabel();
        lbEmployeeList = new javax.swing.JLabel();
        fillerAdminLists = new javax.swing.Box.Filler(new java.awt.Dimension(50, 10), new java.awt.Dimension(50, 20), new java.awt.Dimension(50, 32767));
        btAdminEmployeeDetail = new javax.swing.JButton();
        adminAddEmployeeHolderPanel = new javax.swing.JPanel();
        adminAddEmployeePanel = new javax.swing.JPanel();
        lbRegisterEmployeeName = new javax.swing.JLabel();
        tfRegisterEmployeeName = new javax.swing.JTextField();
        lbRegisterEmployeeSurname = new javax.swing.JLabel();
        tfRegisterEmployeeAddress = new javax.swing.JTextField();
        lbRegisterEmployeeAddress = new javax.swing.JLabel();
        tfRegisterEmployeeSurname = new javax.swing.JTextField();
        lbEmployeeBirthDate = new javax.swing.JLabel();
        spinnerAdminEmployee = new javax.swing.JSpinner();
        lbRegisterEmployeeEmail = new javax.swing.JLabel();
        tfRegisterEmployeeEmail = new javax.swing.JTextField();
        lbRegisterEmployeeUsername = new javax.swing.JLabel();
        tfRegisterEmployeeUsername = new javax.swing.JTextField();
        lbRegisteremployeePassword = new javax.swing.JLabel();
        tfRegisterEmployeePassword = new javax.swing.JTextField();
        btRegisterEmployeeConfirm = new javax.swing.JButton();
        lbNewEmployeePosition = new javax.swing.JLabel();
        tfRegisterEmployeePosition = new javax.swing.JTextField();
        lbNewEmployee = new javax.swing.JLabel();
        adminLogoutPanel = new javax.swing.JPanel();
        fillerAdmin = new javax.swing.Box.Filler(new java.awt.Dimension(320, 0), new java.awt.Dimension(320, 0), new java.awt.Dimension(330, 32767));
        btAdminLogout = new javax.swing.JButton();
        employeePanel = new javax.swing.JPanel();
        employeeListPanel = new javax.swing.JPanel();
        lbEmployeePayList = new javax.swing.JLabel();
        scrollDeposit = new javax.swing.JScrollPane();
        listDeposit = new javax.swing.JList<>();
        btConfirmDeposit = new javax.swing.JButton();
        employeeWatchAccountHolderPanel = new javax.swing.JPanel();
        employeeWatchAccountPanel = new javax.swing.JPanel();
        lbEmployeePayments = new javax.swing.JLabel();
        lbEmployeeChooseCustomer = new javax.swing.JLabel();
        cbEmployeeCustomer = new javax.swing.JComboBox<>();
        scrollEmployeeCustomer = new javax.swing.JScrollPane();
        listEmployeeCustomer = new javax.swing.JList<>();
        btEmployeeCustomerDetail = new javax.swing.JButton();
        employeeLogoutPanel = new javax.swing.JPanel();
        fillerEmployee = new javax.swing.Box.Filler(new java.awt.Dimension(300, 0), new java.awt.Dimension(300, 0), new java.awt.Dimension(330, 32767));
        btEmployeeLogout = new javax.swing.JButton();
        customerPanel = new javax.swing.JPanel();
        customerListPanel = new javax.swing.JPanel();
        lbTranasctions = new javax.swing.JLabel();
        scrollTransactions = new javax.swing.JScrollPane();
        listTransactions = new javax.swing.JList<>();
        fillerTransactions = new javax.swing.Box.Filler(new java.awt.Dimension(0, 47), new java.awt.Dimension(0, 47), new java.awt.Dimension(32767, 50));
        customerAccountHolderPanel = new javax.swing.JPanel();
        customerAccountPanel = new javax.swing.JPanel();
        lbGreeting = new javax.swing.JLabel();
        lbCustomerAccountType = new javax.swing.JLabel();
        lbCustomerIban = new javax.swing.JLabel();
        tfCustomerIban = new javax.swing.JTextField();
        lbCustomerBalance = new javax.swing.JLabel();
        tfCustomerBalance = new javax.swing.JTextField();
        btPayment = new javax.swing.JButton();
        btWithdrawal = new javax.swing.JButton();
        btDeposit = new javax.swing.JButton();
        btReport = new javax.swing.JButton();
        fillerCustomerHeader = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 50), new java.awt.Dimension(32767, 50));
        fillerCustomerBody = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 30));
        customerLogoutPanel = new javax.swing.JPanel();
        fillerCustomer = new javax.swing.Box.Filler(new java.awt.Dimension(320, 0), new java.awt.Dimension(320, 0), new java.awt.Dimension(330, 32767));
        btCustomerLogout = new javax.swing.JButton();
        customerActionPanel = new javax.swing.JPanel();
        customerActionImagePanel = new javax.swing.JPanel();
        lbCustomerActionImage = new javax.swing.JLabel();
        actionHolderPanel = new javax.swing.JPanel();
        commonPanel = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        lbSender = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lbSurname = new javax.swing.JLabel();
        tfSurname = new javax.swing.JTextField();
        lbIban = new javax.swing.JLabel();
        tfIban = new javax.swing.JTextField();
        lbBalance = new javax.swing.JLabel();
        tfBalance = new javax.swing.JTextField();
        fillerPopupHeader = new javax.swing.Box.Filler(new java.awt.Dimension(0, 70), new java.awt.Dimension(0, 70), new java.awt.Dimension(32767, 50));
        fillerPopupBody = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 30));
        actionContainerPanel = new javax.swing.JPanel();
        paymentPanel = new javax.swing.JPanel();
        contentPaymentPanel = new javax.swing.JPanel();
        lbReceiver = new javax.swing.JLabel();
        cbReceiver = new javax.swing.JComboBox<>();
        lbReceiverIban = new javax.swing.JLabel();
        tfReceiverIban = new javax.swing.JTextField();
        fillerPayment = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 30));
        lbPayment = new javax.swing.JLabel();
        spinnerPayment = new javax.swing.JSpinner();
        toolbarPaymentPanel = new javax.swing.JPanel();
        btCancelPayment = new javax.swing.JButton();
        btPay = new javax.swing.JButton();
        depositPanel = new javax.swing.JPanel();
        contentDepositPanel = new javax.swing.JPanel();
        lbDeposit = new javax.swing.JLabel();
        spinnerDeposit = new javax.swing.JSpinner();
        fillerContentDeposit = new javax.swing.Box.Filler(new java.awt.Dimension(0, 100), new java.awt.Dimension(0, 100), new java.awt.Dimension(32767, 90));
        toolbarDepositPanel = new javax.swing.JPanel();
        btCancelDeposit = new javax.swing.JButton();
        btDeposit1 = new javax.swing.JButton();
        withdrawalPanel = new javax.swing.JPanel();
        contentWithdrawalPanel = new javax.swing.JPanel();
        lbWithdrawal = new javax.swing.JLabel();
        spinnerWithdrawal = new javax.swing.JSpinner();
        fillerContentWithdrawal = new javax.swing.Box.Filler(new java.awt.Dimension(0, 100), new java.awt.Dimension(0, 100), new java.awt.Dimension(32767, 90));
        toolbarWithdrawalPanel = new javax.swing.JPanel();
        btCancelWithdrawal = new javax.swing.JButton();
        btWithdrawal1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("view/Bundle_SVK"); // NOI18N
        setTitle(bundle.getString("RAYBANK - VITAJTE")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        containerPanel.setLayout(new java.awt.CardLayout());

        loginPanel.setPreferredSize(new java.awt.Dimension(900, 600));
        loginPanel.setLayout(new javax.swing.BoxLayout(loginPanel, javax.swing.BoxLayout.X_AXIS));

        loginImagePanel.setPreferredSize(new java.awt.Dimension(450, 600));
        loginImagePanel.setLayout(new java.awt.GridBagLayout());
        loginImagePanel.add(lbLoginImage, new java.awt.GridBagConstraints());

        loginPanel.add(loginImagePanel);

        formPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        formPanel.setLayout(new javax.swing.BoxLayout(formPanel, javax.swing.BoxLayout.Y_AXIS));

        newUserPanel.setBackground(new java.awt.Color(18, 18, 18));
        newUserPanel.setPreferredSize(new java.awt.Dimension(450, 180));
        newUserPanel.setLayout(new java.awt.GridBagLayout());

        lbNewCustomer.setBackground(new java.awt.Color(18, 18, 18));
        lbNewCustomer.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbNewCustomer.setForeground(new java.awt.Color(240, 240, 240));
        lbNewCustomer.setText(bundle.getString("NOVÝ ZÁKAZNÍK? VYTVORTE SI ÚČET RÝCHLO A JEDNODUCHO!")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        newUserPanel.add(lbNewCustomer, gridBagConstraints);

        btRegister.setBackground(new java.awt.Color(31, 31, 31));
        btRegister.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btRegister.setForeground(new java.awt.Color(240, 240, 240));
        btRegister.setText(bundle.getString("REGISTROVAŤ SA")); // NOI18N
        btRegister.setPreferredSize(new java.awt.Dimension(135, 35));
        btRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegisterActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        newUserPanel.add(btRegister, gridBagConstraints);

        formPanel.add(newUserPanel);

        userLoginPanel.setBackground(new java.awt.Color(18, 18, 18));
        userLoginPanel.setForeground(new java.awt.Color(240, 240, 240));
        userLoginPanel.setPreferredSize(new java.awt.Dimension(450, 370));
        userLoginPanel.setLayout(new java.awt.GridBagLayout());

        lbUsername.setBackground(new java.awt.Color(18, 18, 18));
        lbUsername.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbUsername.setForeground(new java.awt.Color(240, 240, 240));
        lbUsername.setText(bundle.getString("PRIHLASOVACIE MENO:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        userLoginPanel.add(lbUsername, gridBagConstraints);

        tfUsername.setBackground(new java.awt.Color(31, 31, 31));
        tfUsername.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfUsername.setForeground(new java.awt.Color(240, 240, 240));
        tfUsername.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        userLoginPanel.add(tfUsername, gridBagConstraints);

        lbUserPassword.setBackground(new java.awt.Color(18, 18, 18));
        lbUserPassword.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbUserPassword.setForeground(new java.awt.Color(240, 240, 240));
        lbUserPassword.setText(bundle.getString("VAŠE HESLO:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        userLoginPanel.add(lbUserPassword, gridBagConstraints);

        btLogin.setBackground(new java.awt.Color(31, 31, 31));
        btLogin.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btLogin.setForeground(new java.awt.Color(240, 240, 240));
        btLogin.setText(bundle.getString("PRIHLÁSIŤ SA")); // NOI18N
        btLogin.setPreferredSize(new java.awt.Dimension(135, 35));
        btLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoginActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        userLoginPanel.add(btLogin, gridBagConstraints);

        pfPassword.setBackground(new java.awt.Color(31, 31, 31));
        pfPassword.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        pfPassword.setForeground(new java.awt.Color(240, 240, 240));
        pfPassword.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        userLoginPanel.add(pfPassword, gridBagConstraints);

        formPanel.add(userLoginPanel);

        languagePanel.setBackground(new java.awt.Color(18, 18, 18));
        languagePanel.setPreferredSize(new java.awt.Dimension(450, 50));
        languagePanel.setLayout(new java.awt.GridBagLayout());
        languagePanel.add(filler1, new java.awt.GridBagConstraints());

        btSlovak.setBackground(new java.awt.Color(31, 31, 31));
        btSlovak.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btSlovak.setForeground(new java.awt.Color(240, 240, 240));
        btSlovak.setText(bundle.getString("SLOVENČINA")); // NOI18N
        btSlovak.setPreferredSize(new java.awt.Dimension(120, 35));
        btSlovak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSlovakActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        languagePanel.add(btSlovak, gridBagConstraints);

        btEnglish.setBackground(new java.awt.Color(31, 31, 31));
        btEnglish.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btEnglish.setForeground(new java.awt.Color(240, 240, 240));
        btEnglish.setText(bundle.getString("ENGLISH")); // NOI18N
        btEnglish.setPreferredSize(new java.awt.Dimension(120, 35));
        btEnglish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEnglishActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        languagePanel.add(btEnglish, gridBagConstraints);

        formPanel.add(languagePanel);

        loginPanel.add(formPanel);

        containerPanel.add(loginPanel, "card2");

        registerPanel.setPreferredSize(new java.awt.Dimension(900, 600));
        registerPanel.setLayout(new javax.swing.BoxLayout(registerPanel, javax.swing.BoxLayout.X_AXIS));

        registerImagePanel.setPreferredSize(new java.awt.Dimension(450, 600));
        registerImagePanel.setLayout(new java.awt.GridBagLayout());
        registerImagePanel.add(lbRegisterImage, new java.awt.GridBagConstraints());

        registerPanel.add(registerImagePanel);

        registerFormHolderPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        registerFormHolderPanel.setLayout(new javax.swing.BoxLayout(registerFormHolderPanel, javax.swing.BoxLayout.Y_AXIS));

        registerBackPanel.setBackground(new java.awt.Color(18, 18, 18));
        registerBackPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        registerBackPanel.setLayout(new java.awt.GridBagLayout());
        registerBackPanel.add(filler2, new java.awt.GridBagConstraints());

        btRegisterBack.setBackground(new java.awt.Color(31, 31, 31));
        btRegisterBack.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btRegisterBack.setForeground(new java.awt.Color(240, 240, 240));
        btRegisterBack.setText("Späť");
        btRegisterBack.setToolTipText("");
        btRegisterBack.setPreferredSize(new java.awt.Dimension(100, 35));
        btRegisterBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegisterBackActionPerformed(evt);
            }
        });
        registerBackPanel.add(btRegisterBack, new java.awt.GridBagConstraints());

        registerFormHolderPanel.add(registerBackPanel);

        registerFormPanel.setBackground(new java.awt.Color(18, 18, 18));
        registerFormPanel.setPreferredSize(new java.awt.Dimension(450, 550));
        registerFormPanel.setLayout(new java.awt.GridBagLayout());

        lbRegisterName.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterName.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterName.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterName.setText("Meno:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(lbRegisterName, gridBagConstraints);

        tfRegisterName.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterName.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterName.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterName.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(tfRegisterName, gridBagConstraints);

        lbRegisterSurname.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterSurname.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterSurname.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterSurname.setText("Priezvisko:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(lbRegisterSurname, gridBagConstraints);

        tfRegisterAddress.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterAddress.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterAddress.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterAddress.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(tfRegisterAddress, gridBagConstraints);

        lbRegisterAddress.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterAddress.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterAddress.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterAddress.setText("Bydlisko:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(lbRegisterAddress, gridBagConstraints);

        tfRegisterSurname.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterSurname.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterSurname.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterSurname.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(tfRegisterSurname, gridBagConstraints);

        lbBirthDate.setBackground(new java.awt.Color(18, 18, 18));
        lbBirthDate.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbBirthDate.setForeground(new java.awt.Color(240, 240, 240));
        lbBirthDate.setText("Dátum narodenia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(lbBirthDate, gridBagConstraints);

        spinnerBirthDate.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        spinnerBirthDate.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        spinnerBirthDate.setEditor(new javax.swing.JSpinner.DateEditor(spinnerBirthDate, "dd/MM/yyyy"));
        spinnerBirthDate.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(spinnerBirthDate, gridBagConstraints);

        lbRegisterEmail.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterEmail.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterEmail.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterEmail.setText("Email:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(lbRegisterEmail, gridBagConstraints);

        tfRegisterEmail.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterEmail.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterEmail.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterEmail.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(tfRegisterEmail, gridBagConstraints);

        lbRegisterUsername.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterUsername.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterUsername.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterUsername.setText("Používateľské meno:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(lbRegisterUsername, gridBagConstraints);

        tfRegisterUsername.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterUsername.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterUsername.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterUsername.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(tfRegisterUsername, gridBagConstraints);

        lbRegisterPassword.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterPassword.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterPassword.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterPassword.setText("Heslo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(lbRegisterPassword, gridBagConstraints);

        tfRegisterPassword.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterPassword.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterPassword.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterPassword.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(tfRegisterPassword, gridBagConstraints);

        btRegisterConfirm.setBackground(new java.awt.Color(31, 31, 31));
        btRegisterConfirm.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btRegisterConfirm.setForeground(new java.awt.Color(240, 240, 240));
        btRegisterConfirm.setText("Registrovať sa");
        btRegisterConfirm.setToolTipText("");
        btRegisterConfirm.setPreferredSize(new java.awt.Dimension(135, 35));
        btRegisterConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegisterConfirmActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(btRegisterConfirm, gridBagConstraints);

        lbAccountType.setBackground(new java.awt.Color(18, 18, 18));
        lbAccountType.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbAccountType.setForeground(new java.awt.Color(240, 240, 240));
        lbAccountType.setText("Typ účtu:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(lbAccountType, gridBagConstraints);

        cbAccountType.setBackground(new java.awt.Color(31, 31, 31));
        cbAccountType.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        cbAccountType.setForeground(new java.awt.Color(240, 240, 240));
        cbAccountType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bežný", "Študentský" }));
        cbAccountType.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        registerFormPanel.add(cbAccountType, gridBagConstraints);

        registerFormHolderPanel.add(registerFormPanel);

        registerPanel.add(registerFormHolderPanel);

        containerPanel.add(registerPanel, "card3");

        adminPanel.setLayout(new javax.swing.BoxLayout(adminPanel, javax.swing.BoxLayout.X_AXIS));

        adminListPanel.setBackground(new java.awt.Color(18, 18, 18));
        adminListPanel.setForeground(new java.awt.Color(240, 240, 240));
        adminListPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        adminListPanel.setLayout(new java.awt.GridBagLayout());

        scrollAdminAccounts.setPreferredSize(new java.awt.Dimension(420, 200));

        listAdminAccounts.setBackground(new java.awt.Color(31, 31, 31));
        listAdminAccounts.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        listAdminAccounts.setForeground(new java.awt.Color(240, 240, 240));
        listAdminAccounts.setModel(accountList);
        listAdminAccounts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listAdminAccounts.setPreferredSize(new java.awt.Dimension(397, 150));
        scrollAdminAccounts.setViewportView(listAdminAccounts);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminListPanel.add(scrollAdminAccounts, gridBagConstraints);

        btRemoveAccount.setBackground(new java.awt.Color(31, 31, 31));
        btRemoveAccount.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btRemoveAccount.setForeground(new java.awt.Color(240, 240, 240));
        btRemoveAccount.setText("Zrušiť účet");
        btRemoveAccount.setPreferredSize(new java.awt.Dimension(125, 35));
        btRemoveAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveAccountActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminListPanel.add(btRemoveAccount, gridBagConstraints);

        btAdminAccountDetail.setBackground(new java.awt.Color(31, 31, 31));
        btAdminAccountDetail.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btAdminAccountDetail.setForeground(new java.awt.Color(240, 240, 240));
        btAdminAccountDetail.setText("Detail");
        btAdminAccountDetail.setPreferredSize(new java.awt.Dimension(125, 35));
        btAdminAccountDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdminAccountDetailActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminListPanel.add(btAdminAccountDetail, gridBagConstraints);

        scrollAdminEmployees.setPreferredSize(new java.awt.Dimension(420, 200));

        listAdminEmployees.setBackground(new java.awt.Color(31, 31, 31));
        listAdminEmployees.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        listAdminEmployees.setForeground(new java.awt.Color(240, 240, 240));
        listAdminEmployees.setModel(employeeList);
        listAdminEmployees.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listAdminEmployees.setPreferredSize(new java.awt.Dimension(397, 150));
        scrollAdminEmployees.setViewportView(listAdminEmployees);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminListPanel.add(scrollAdminEmployees, gridBagConstraints);

        btRemoveEmployee.setBackground(new java.awt.Color(31, 31, 31));
        btRemoveEmployee.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btRemoveEmployee.setForeground(new java.awt.Color(240, 240, 240));
        btRemoveEmployee.setText("Odstrániť");
        btRemoveEmployee.setPreferredSize(new java.awt.Dimension(125, 35));
        btRemoveEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveEmployeeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminListPanel.add(btRemoveEmployee, gridBagConstraints);

        lbAccountList.setBackground(new java.awt.Color(18, 18, 18));
        lbAccountList.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbAccountList.setForeground(new java.awt.Color(240, 240, 240));
        lbAccountList.setText("Zoznam účtov");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminListPanel.add(lbAccountList, gridBagConstraints);

        lbEmployeeList.setBackground(new java.awt.Color(18, 18, 18));
        lbEmployeeList.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbEmployeeList.setForeground(new java.awt.Color(240, 240, 240));
        lbEmployeeList.setText("Zoznam zamestnancov");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminListPanel.add(lbEmployeeList, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        adminListPanel.add(fillerAdminLists, gridBagConstraints);

        btAdminEmployeeDetail.setBackground(new java.awt.Color(31, 31, 31));
        btAdminEmployeeDetail.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btAdminEmployeeDetail.setForeground(new java.awt.Color(240, 240, 240));
        btAdminEmployeeDetail.setText("Detail");
        btAdminEmployeeDetail.setPreferredSize(new java.awt.Dimension(125, 35));
        btAdminEmployeeDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdminEmployeeDetailActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminListPanel.add(btAdminEmployeeDetail, gridBagConstraints);

        adminPanel.add(adminListPanel);

        adminAddEmployeeHolderPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        adminAddEmployeeHolderPanel.setLayout(new javax.swing.BoxLayout(adminAddEmployeeHolderPanel, javax.swing.BoxLayout.Y_AXIS));

        adminAddEmployeePanel.setBackground(new java.awt.Color(18, 18, 18));
        adminAddEmployeePanel.setForeground(new java.awt.Color(240, 240, 240));
        adminAddEmployeePanel.setPreferredSize(new java.awt.Dimension(450, 550));
        adminAddEmployeePanel.setLayout(new java.awt.GridBagLayout());

        lbRegisterEmployeeName.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterEmployeeName.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterEmployeeName.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterEmployeeName.setText("Meno:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbRegisterEmployeeName, gridBagConstraints);

        tfRegisterEmployeeName.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterEmployeeName.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterEmployeeName.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterEmployeeName.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(tfRegisterEmployeeName, gridBagConstraints);

        lbRegisterEmployeeSurname.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterEmployeeSurname.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterEmployeeSurname.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterEmployeeSurname.setText("Priezvisko:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbRegisterEmployeeSurname, gridBagConstraints);

        tfRegisterEmployeeAddress.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterEmployeeAddress.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterEmployeeAddress.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterEmployeeAddress.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(tfRegisterEmployeeAddress, gridBagConstraints);

        lbRegisterEmployeeAddress.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterEmployeeAddress.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterEmployeeAddress.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterEmployeeAddress.setText("Bydlisko:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbRegisterEmployeeAddress, gridBagConstraints);

        tfRegisterEmployeeSurname.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterEmployeeSurname.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterEmployeeSurname.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterEmployeeSurname.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(tfRegisterEmployeeSurname, gridBagConstraints);

        lbEmployeeBirthDate.setBackground(new java.awt.Color(18, 18, 18));
        lbEmployeeBirthDate.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbEmployeeBirthDate.setForeground(new java.awt.Color(240, 240, 240));
        lbEmployeeBirthDate.setText("Dátum narodenia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbEmployeeBirthDate, gridBagConstraints);

        spinnerAdminEmployee.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        spinnerAdminEmployee.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        spinnerAdminEmployee.setEditor(new javax.swing.JSpinner.DateEditor(spinnerAdminEmployee, "dd/MM/yyyy"));
        spinnerAdminEmployee.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(spinnerAdminEmployee, gridBagConstraints);

        lbRegisterEmployeeEmail.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterEmployeeEmail.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterEmployeeEmail.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterEmployeeEmail.setText("Email:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbRegisterEmployeeEmail, gridBagConstraints);

        tfRegisterEmployeeEmail.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterEmployeeEmail.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterEmployeeEmail.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterEmployeeEmail.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(tfRegisterEmployeeEmail, gridBagConstraints);

        lbRegisterEmployeeUsername.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisterEmployeeUsername.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisterEmployeeUsername.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisterEmployeeUsername.setText("Používateľské meno:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbRegisterEmployeeUsername, gridBagConstraints);

        tfRegisterEmployeeUsername.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterEmployeeUsername.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterEmployeeUsername.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterEmployeeUsername.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(tfRegisterEmployeeUsername, gridBagConstraints);

        lbRegisteremployeePassword.setBackground(new java.awt.Color(18, 18, 18));
        lbRegisteremployeePassword.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbRegisteremployeePassword.setForeground(new java.awt.Color(240, 240, 240));
        lbRegisteremployeePassword.setText("Heslo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbRegisteremployeePassword, gridBagConstraints);

        tfRegisterEmployeePassword.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterEmployeePassword.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfRegisterEmployeePassword.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterEmployeePassword.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(tfRegisterEmployeePassword, gridBagConstraints);

        btRegisterEmployeeConfirm.setBackground(new java.awt.Color(31, 31, 31));
        btRegisterEmployeeConfirm.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btRegisterEmployeeConfirm.setForeground(new java.awt.Color(240, 240, 240));
        btRegisterEmployeeConfirm.setText("Vytvoriť");
        btRegisterEmployeeConfirm.setToolTipText("");
        btRegisterEmployeeConfirm.setPreferredSize(new java.awt.Dimension(135, 35));
        btRegisterEmployeeConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegisterEmployeeConfirmActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(btRegisterEmployeeConfirm, gridBagConstraints);

        lbNewEmployeePosition.setBackground(new java.awt.Color(18, 18, 18));
        lbNewEmployeePosition.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbNewEmployeePosition.setForeground(new java.awt.Color(240, 240, 240));
        lbNewEmployeePosition.setText("Pozícia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbNewEmployeePosition, gridBagConstraints);

        tfRegisterEmployeePosition.setBackground(new java.awt.Color(31, 31, 31));
        tfRegisterEmployeePosition.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        tfRegisterEmployeePosition.setForeground(new java.awt.Color(240, 240, 240));
        tfRegisterEmployeePosition.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(tfRegisterEmployeePosition, gridBagConstraints);

        lbNewEmployee.setBackground(new java.awt.Color(18, 18, 18));
        lbNewEmployee.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lbNewEmployee.setForeground(new java.awt.Color(240, 240, 240));
        lbNewEmployee.setText("Nový zamestnanec");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        adminAddEmployeePanel.add(lbNewEmployee, gridBagConstraints);

        adminAddEmployeeHolderPanel.add(adminAddEmployeePanel);

        adminLogoutPanel.setBackground(new java.awt.Color(18, 18, 18));
        adminLogoutPanel.setForeground(new java.awt.Color(240, 240, 240));
        adminLogoutPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        adminLogoutPanel.setLayout(new java.awt.GridBagLayout());
        adminLogoutPanel.add(fillerAdmin, new java.awt.GridBagConstraints());

        btAdminLogout.setBackground(new java.awt.Color(31, 31, 31));
        btAdminLogout.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btAdminLogout.setForeground(new java.awt.Color(240, 240, 240));
        btAdminLogout.setText("Odhlásiť sa");
        btAdminLogout.setPreferredSize(new java.awt.Dimension(120, 35));
        btAdminLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdminLogoutActionPerformed(evt);
            }
        });
        adminLogoutPanel.add(btAdminLogout, new java.awt.GridBagConstraints());

        adminAddEmployeeHolderPanel.add(adminLogoutPanel);

        adminPanel.add(adminAddEmployeeHolderPanel);

        containerPanel.add(adminPanel, "card4");

        employeePanel.setLayout(new javax.swing.BoxLayout(employeePanel, javax.swing.BoxLayout.X_AXIS));

        employeeListPanel.setBackground(new java.awt.Color(18, 18, 18));
        employeeListPanel.setForeground(new java.awt.Color(240, 240, 240));
        employeeListPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        employeeListPanel.setLayout(new java.awt.GridBagLayout());

        lbEmployeePayList.setBackground(new java.awt.Color(18, 18, 18));
        lbEmployeePayList.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbEmployeePayList.setForeground(new java.awt.Color(240, 240, 240));
        lbEmployeePayList.setText("Čakajúce vklady na účty");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        employeeListPanel.add(lbEmployeePayList, gridBagConstraints);

        scrollDeposit.setPreferredSize(new java.awt.Dimension(420, 510));

        listDeposit.setBackground(new java.awt.Color(31, 31, 31));
        listDeposit.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        listDeposit.setForeground(new java.awt.Color(240, 240, 240));
        listDeposit.setModel(depositList);
        listDeposit.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listDeposit.setPreferredSize(new java.awt.Dimension(397, 487));
        scrollDeposit.setViewportView(listDeposit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        employeeListPanel.add(scrollDeposit, gridBagConstraints);

        btConfirmDeposit.setBackground(new java.awt.Color(31, 31, 31));
        btConfirmDeposit.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btConfirmDeposit.setForeground(new java.awt.Color(240, 240, 240));
        btConfirmDeposit.setText("Potvrdiť");
        btConfirmDeposit.setPreferredSize(new java.awt.Dimension(100, 35));
        btConfirmDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConfirmDepositActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        employeeListPanel.add(btConfirmDeposit, gridBagConstraints);

        employeePanel.add(employeeListPanel);

        employeeWatchAccountHolderPanel.setLayout(new javax.swing.BoxLayout(employeeWatchAccountHolderPanel, javax.swing.BoxLayout.Y_AXIS));

        employeeWatchAccountPanel.setBackground(new java.awt.Color(18, 18, 18));
        employeeWatchAccountPanel.setForeground(new java.awt.Color(240, 240, 240));
        employeeWatchAccountPanel.setPreferredSize(new java.awt.Dimension(450, 550));
        employeeWatchAccountPanel.setLayout(new java.awt.GridBagLayout());

        lbEmployeePayments.setBackground(new java.awt.Color(18, 18, 18));
        lbEmployeePayments.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbEmployeePayments.setForeground(new java.awt.Color(240, 240, 240));
        lbEmployeePayments.setText("Pohyby na účte");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 10, 5);
        employeeWatchAccountPanel.add(lbEmployeePayments, gridBagConstraints);

        lbEmployeeChooseCustomer.setBackground(new java.awt.Color(18, 18, 18));
        lbEmployeeChooseCustomer.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbEmployeeChooseCustomer.setForeground(new java.awt.Color(240, 240, 240));
        lbEmployeeChooseCustomer.setText("Zákazník:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        employeeWatchAccountPanel.add(lbEmployeeChooseCustomer, gridBagConstraints);

        cbEmployeeCustomer.setBackground(new java.awt.Color(31, 31, 31));
        cbEmployeeCustomer.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        cbEmployeeCustomer.setForeground(new java.awt.Color(240, 240, 240));
        cbEmployeeCustomer.setModel(customerComboList);
        cbEmployeeCustomer.setPreferredSize(new java.awt.Dimension(420, 30));
        cbEmployeeCustomer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbEmployeeCustomerItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        employeeWatchAccountPanel.add(cbEmployeeCustomer, gridBagConstraints);

        scrollEmployeeCustomer.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        scrollEmployeeCustomer.setPreferredSize(new java.awt.Dimension(420, 420));

        listEmployeeCustomer.setBackground(new java.awt.Color(31, 31, 31));
        listEmployeeCustomer.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        listEmployeeCustomer.setForeground(new java.awt.Color(240, 240, 240));
        listEmployeeCustomer.setModel(transactionSubList);
        listEmployeeCustomer.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listEmployeeCustomer.setPreferredSize(new java.awt.Dimension(600, 397));
        listEmployeeCustomer.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listEmployeeCustomerValueChanged(evt);
            }
        });
        scrollEmployeeCustomer.setViewportView(listEmployeeCustomer);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        employeeWatchAccountPanel.add(scrollEmployeeCustomer, gridBagConstraints);

        btEmployeeCustomerDetail.setBackground(new java.awt.Color(31, 31, 31));
        btEmployeeCustomerDetail.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btEmployeeCustomerDetail.setForeground(new java.awt.Color(240, 240, 240));
        btEmployeeCustomerDetail.setText("Detail");
        btEmployeeCustomerDetail.setPreferredSize(new java.awt.Dimension(100, 35));
        btEmployeeCustomerDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEmployeeCustomerDetailActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        employeeWatchAccountPanel.add(btEmployeeCustomerDetail, gridBagConstraints);

        employeeWatchAccountHolderPanel.add(employeeWatchAccountPanel);

        employeeLogoutPanel.setBackground(new java.awt.Color(18, 18, 18));
        employeeLogoutPanel.setForeground(new java.awt.Color(240, 240, 240));
        employeeLogoutPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        employeeLogoutPanel.setLayout(new java.awt.GridBagLayout());
        employeeLogoutPanel.add(fillerEmployee, new java.awt.GridBagConstraints());

        btEmployeeLogout.setBackground(new java.awt.Color(31, 31, 31));
        btEmployeeLogout.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btEmployeeLogout.setForeground(new java.awt.Color(240, 240, 240));
        btEmployeeLogout.setText("Odhlásiť sa");
        btEmployeeLogout.setPreferredSize(new java.awt.Dimension(120, 35));
        btEmployeeLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEmployeeLogoutActionPerformed(evt);
            }
        });
        employeeLogoutPanel.add(btEmployeeLogout, new java.awt.GridBagConstraints());

        employeeWatchAccountHolderPanel.add(employeeLogoutPanel);

        employeePanel.add(employeeWatchAccountHolderPanel);

        containerPanel.add(employeePanel, "card5");

        customerPanel.setLayout(new javax.swing.BoxLayout(customerPanel, javax.swing.BoxLayout.X_AXIS));

        customerListPanel.setBackground(new java.awt.Color(18, 18, 18));
        customerListPanel.setForeground(new java.awt.Color(240, 240, 240));
        customerListPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        customerListPanel.setLayout(new java.awt.GridBagLayout());

        lbTranasctions.setBackground(new java.awt.Color(18, 18, 18));
        lbTranasctions.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbTranasctions.setForeground(new java.awt.Color(240, 240, 240));
        lbTranasctions.setText("Zoznam transakcií");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerListPanel.add(lbTranasctions, gridBagConstraints);

        scrollTransactions.setPreferredSize(new java.awt.Dimension(420, 510));

        listTransactions.setBackground(new java.awt.Color(31, 31, 31));
        listTransactions.setFont(new java.awt.Font("Dialog", 0, 13)); // NOI18N
        listTransactions.setForeground(new java.awt.Color(240, 240, 240));
        listTransactions.setModel(transactionSubList);
        listTransactions.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listTransactions.setPreferredSize(new java.awt.Dimension(600, 487));
        listTransactions.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listTransactionsValueChanged(evt);
            }
        });
        scrollTransactions.setViewportView(listTransactions);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerListPanel.add(scrollTransactions, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        customerListPanel.add(fillerTransactions, gridBagConstraints);

        customerPanel.add(customerListPanel);

        customerAccountHolderPanel.setLayout(new javax.swing.BoxLayout(customerAccountHolderPanel, javax.swing.BoxLayout.Y_AXIS));

        customerAccountPanel.setBackground(new java.awt.Color(18, 18, 18));
        customerAccountPanel.setForeground(new java.awt.Color(240, 240, 240));
        customerAccountPanel.setPreferredSize(new java.awt.Dimension(450, 550));
        customerAccountPanel.setLayout(new java.awt.GridBagLayout());

        lbGreeting.setBackground(new java.awt.Color(18, 18, 18));
        lbGreeting.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbGreeting.setForeground(new java.awt.Color(240, 240, 240));
        lbGreeting.setText("Dobrý deň, MENO PRIEZVISKO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(lbGreeting, gridBagConstraints);

        lbCustomerAccountType.setBackground(new java.awt.Color(18, 18, 18));
        lbCustomerAccountType.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbCustomerAccountType.setForeground(new java.awt.Color(240, 240, 240));
        lbCustomerAccountType.setText("Typ účtu fess");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(lbCustomerAccountType, gridBagConstraints);

        lbCustomerIban.setBackground(new java.awt.Color(18, 18, 18));
        lbCustomerIban.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbCustomerIban.setForeground(new java.awt.Color(240, 240, 240));
        lbCustomerIban.setText("IBAN:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(lbCustomerIban, gridBagConstraints);

        tfCustomerIban.setEditable(false);
        tfCustomerIban.setBackground(new java.awt.Color(31, 31, 31));
        tfCustomerIban.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfCustomerIban.setForeground(new java.awt.Color(240, 240, 240));
        tfCustomerIban.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(tfCustomerIban, gridBagConstraints);

        lbCustomerBalance.setBackground(new java.awt.Color(18, 18, 18));
        lbCustomerBalance.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbCustomerBalance.setForeground(new java.awt.Color(240, 240, 240));
        lbCustomerBalance.setText("Zostatok:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(lbCustomerBalance, gridBagConstraints);

        tfCustomerBalance.setEditable(false);
        tfCustomerBalance.setBackground(new java.awt.Color(31, 31, 31));
        tfCustomerBalance.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfCustomerBalance.setForeground(new java.awt.Color(240, 240, 240));
        tfCustomerBalance.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(tfCustomerBalance, gridBagConstraints);

        btPayment.setBackground(new java.awt.Color(31, 31, 31));
        btPayment.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btPayment.setForeground(new java.awt.Color(240, 240, 240));
        btPayment.setText("Platba");
        btPayment.setPreferredSize(new java.awt.Dimension(150, 35));
        btPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPaymentActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(btPayment, gridBagConstraints);

        btWithdrawal.setBackground(new java.awt.Color(31, 31, 31));
        btWithdrawal.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btWithdrawal.setForeground(new java.awt.Color(240, 240, 240));
        btWithdrawal.setText("Výber");
        btWithdrawal.setPreferredSize(new java.awt.Dimension(150, 35));
        btWithdrawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWithdrawalActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(btWithdrawal, gridBagConstraints);

        btDeposit.setBackground(new java.awt.Color(31, 31, 31));
        btDeposit.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btDeposit.setForeground(new java.awt.Color(240, 240, 240));
        btDeposit.setText("Vklad");
        btDeposit.setPreferredSize(new java.awt.Dimension(150, 35));
        btDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDepositActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(btDeposit, gridBagConstraints);

        btReport.setBackground(new java.awt.Color(31, 31, 31));
        btReport.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btReport.setForeground(new java.awt.Color(240, 240, 240));
        btReport.setText("Výpis");
        btReport.setPreferredSize(new java.awt.Dimension(150, 35));
        btReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReportActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        customerAccountPanel.add(btReport, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        customerAccountPanel.add(fillerCustomerHeader, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        customerAccountPanel.add(fillerCustomerBody, gridBagConstraints);

        customerAccountHolderPanel.add(customerAccountPanel);

        customerLogoutPanel.setBackground(new java.awt.Color(18, 18, 18));
        customerLogoutPanel.setForeground(new java.awt.Color(240, 240, 240));
        customerLogoutPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        customerLogoutPanel.setLayout(new java.awt.GridBagLayout());
        customerLogoutPanel.add(fillerCustomer, new java.awt.GridBagConstraints());

        btCustomerLogout.setBackground(new java.awt.Color(31, 31, 31));
        btCustomerLogout.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btCustomerLogout.setForeground(new java.awt.Color(240, 240, 240));
        btCustomerLogout.setText("Odhlásiť sa");
        btCustomerLogout.setPreferredSize(new java.awt.Dimension(120, 35));
        btCustomerLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCustomerLogoutActionPerformed(evt);
            }
        });
        customerLogoutPanel.add(btCustomerLogout, new java.awt.GridBagConstraints());

        customerAccountHolderPanel.add(customerLogoutPanel);

        customerPanel.add(customerAccountHolderPanel);

        containerPanel.add(customerPanel, "card6");

        customerActionPanel.setLayout(new javax.swing.BoxLayout(customerActionPanel, javax.swing.BoxLayout.X_AXIS));

        customerActionImagePanel.setPreferredSize(new java.awt.Dimension(450, 600));
        customerActionImagePanel.setLayout(new java.awt.GridBagLayout());
        customerActionImagePanel.add(lbCustomerActionImage, new java.awt.GridBagConstraints());

        customerActionPanel.add(customerActionImagePanel);

        actionHolderPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        actionHolderPanel.setLayout(new javax.swing.BoxLayout(actionHolderPanel, javax.swing.BoxLayout.Y_AXIS));

        commonPanel.setBackground(new java.awt.Color(18, 18, 18));
        commonPanel.setForeground(new java.awt.Color(240, 240, 240));
        commonPanel.setPreferredSize(new java.awt.Dimension(450, 350));
        commonPanel.setLayout(new java.awt.GridBagLayout());

        lbTitle.setBackground(new java.awt.Color(18, 18, 18));
        lbTitle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(240, 240, 240));
        lbTitle.setText("Title");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(lbTitle, gridBagConstraints);

        lbSender.setBackground(new java.awt.Color(18, 18, 18));
        lbSender.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbSender.setForeground(new java.awt.Color(240, 240, 240));
        lbSender.setText("Odosielateľ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(lbSender, gridBagConstraints);

        lbName.setBackground(new java.awt.Color(18, 18, 18));
        lbName.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbName.setForeground(new java.awt.Color(240, 240, 240));
        lbName.setText("Meno:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(lbName, gridBagConstraints);

        tfName.setEditable(false);
        tfName.setBackground(new java.awt.Color(31, 31, 31));
        tfName.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfName.setForeground(new java.awt.Color(240, 240, 240));
        tfName.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(tfName, gridBagConstraints);

        lbSurname.setBackground(new java.awt.Color(18, 18, 18));
        lbSurname.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbSurname.setForeground(new java.awt.Color(240, 240, 240));
        lbSurname.setText("Priezvisko:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(lbSurname, gridBagConstraints);

        tfSurname.setEditable(false);
        tfSurname.setBackground(new java.awt.Color(31, 31, 31));
        tfSurname.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfSurname.setForeground(new java.awt.Color(240, 240, 240));
        tfSurname.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(tfSurname, gridBagConstraints);

        lbIban.setBackground(new java.awt.Color(18, 18, 18));
        lbIban.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbIban.setForeground(new java.awt.Color(240, 240, 240));
        lbIban.setText("IBAN:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(lbIban, gridBagConstraints);

        tfIban.setEditable(false);
        tfIban.setBackground(new java.awt.Color(31, 31, 31));
        tfIban.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfIban.setForeground(new java.awt.Color(240, 240, 240));
        tfIban.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(tfIban, gridBagConstraints);

        lbBalance.setBackground(new java.awt.Color(18, 18, 18));
        lbBalance.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbBalance.setForeground(new java.awt.Color(240, 240, 240));
        lbBalance.setText("Zostatok:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(lbBalance, gridBagConstraints);

        tfBalance.setEditable(false);
        tfBalance.setBackground(new java.awt.Color(31, 31, 31));
        tfBalance.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfBalance.setForeground(new java.awt.Color(240, 240, 240));
        tfBalance.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        commonPanel.add(tfBalance, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        commonPanel.add(fillerPopupHeader, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        commonPanel.add(fillerPopupBody, gridBagConstraints);

        actionHolderPanel.add(commonPanel);

        actionContainerPanel.setLayout(new java.awt.CardLayout());

        paymentPanel.setLayout(new javax.swing.BoxLayout(paymentPanel, javax.swing.BoxLayout.Y_AXIS));

        contentPaymentPanel.setBackground(new java.awt.Color(18, 18, 18));
        contentPaymentPanel.setForeground(new java.awt.Color(240, 240, 240));
        contentPaymentPanel.setPreferredSize(new java.awt.Dimension(450, 200));
        contentPaymentPanel.setLayout(new java.awt.GridBagLayout());

        lbReceiver.setBackground(new java.awt.Color(18, 18, 18));
        lbReceiver.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbReceiver.setForeground(new java.awt.Color(240, 240, 240));
        lbReceiver.setText("Príjemca:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentPaymentPanel.add(lbReceiver, gridBagConstraints);

        cbReceiver.setBackground(new java.awt.Color(31, 31, 31));
        cbReceiver.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        cbReceiver.setForeground(new java.awt.Color(240, 240, 240));
        cbReceiver.setModel(receiverComboList);
        cbReceiver.setPreferredSize(new java.awt.Dimension(250, 30));
        cbReceiver.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbReceiverItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentPaymentPanel.add(cbReceiver, gridBagConstraints);

        lbReceiverIban.setBackground(new java.awt.Color(18, 18, 18));
        lbReceiverIban.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbReceiverIban.setForeground(new java.awt.Color(240, 240, 240));
        lbReceiverIban.setText("IBAN:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentPaymentPanel.add(lbReceiverIban, gridBagConstraints);

        tfReceiverIban.setEditable(false);
        tfReceiverIban.setBackground(new java.awt.Color(31, 31, 31));
        tfReceiverIban.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        tfReceiverIban.setForeground(new java.awt.Color(240, 240, 240));
        tfReceiverIban.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentPaymentPanel.add(tfReceiverIban, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        contentPaymentPanel.add(fillerPayment, gridBagConstraints);

        lbPayment.setBackground(new java.awt.Color(18, 18, 18));
        lbPayment.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbPayment.setForeground(new java.awt.Color(240, 240, 240));
        lbPayment.setText("Suma:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentPaymentPanel.add(lbPayment, gridBagConstraints);

        spinnerPayment.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        spinnerPayment.setModel(new javax.swing.SpinnerNumberModel(0.01d, 0.01d, null, 0.5d));
        spinnerPayment.setEditor(new javax.swing.JSpinner.NumberEditor(spinnerPayment, "0.00"));
        spinnerPayment.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentPaymentPanel.add(spinnerPayment, gridBagConstraints);

        paymentPanel.add(contentPaymentPanel);

        toolbarPaymentPanel.setBackground(new java.awt.Color(18, 18, 18));
        toolbarPaymentPanel.setForeground(new java.awt.Color(240, 240, 240));
        toolbarPaymentPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        toolbarPaymentPanel.setLayout(new java.awt.GridLayout(1, 0));

        btCancelPayment.setBackground(new java.awt.Color(31, 31, 31));
        btCancelPayment.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btCancelPayment.setForeground(new java.awt.Color(240, 240, 240));
        btCancelPayment.setText("Zrušiť");
        btCancelPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelPaymentActionPerformed(evt);
            }
        });
        toolbarPaymentPanel.add(btCancelPayment);

        btPay.setBackground(new java.awt.Color(31, 31, 31));
        btPay.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btPay.setForeground(new java.awt.Color(240, 240, 240));
        btPay.setText("Zaplatiť");
        btPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPayActionPerformed(evt);
            }
        });
        toolbarPaymentPanel.add(btPay);

        paymentPanel.add(toolbarPaymentPanel);

        actionContainerPanel.add(paymentPanel, bundle.getString("CARD2")); // NOI18N

        depositPanel.setBackground(new java.awt.Color(18, 18, 18));
        depositPanel.setForeground(new java.awt.Color(240, 240, 240));
        depositPanel.setLayout(new javax.swing.BoxLayout(depositPanel, javax.swing.BoxLayout.Y_AXIS));

        contentDepositPanel.setBackground(new java.awt.Color(18, 18, 18));
        contentDepositPanel.setForeground(new java.awt.Color(240, 240, 240));
        contentDepositPanel.setPreferredSize(new java.awt.Dimension(450, 200));
        contentDepositPanel.setLayout(new java.awt.GridBagLayout());

        lbDeposit.setBackground(new java.awt.Color(18, 18, 18));
        lbDeposit.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbDeposit.setForeground(new java.awt.Color(240, 240, 240));
        lbDeposit.setText("Suma:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentDepositPanel.add(lbDeposit, gridBagConstraints);

        spinnerDeposit.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        spinnerDeposit.setModel(new javax.swing.SpinnerNumberModel(0.01d, 0.01d, null, 0.5d));
        spinnerDeposit.setEditor(new javax.swing.JSpinner.NumberEditor(spinnerDeposit, "0.00"));
        spinnerDeposit.setMinimumSize(new java.awt.Dimension(250, 30));
        spinnerDeposit.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentDepositPanel.add(spinnerDeposit, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        contentDepositPanel.add(fillerContentDeposit, gridBagConstraints);

        depositPanel.add(contentDepositPanel);

        toolbarDepositPanel.setBackground(new java.awt.Color(18, 18, 18));
        toolbarDepositPanel.setForeground(new java.awt.Color(240, 240, 240));
        toolbarDepositPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        toolbarDepositPanel.setLayout(new java.awt.GridLayout(1, 0));

        btCancelDeposit.setBackground(new java.awt.Color(31, 31, 31));
        btCancelDeposit.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btCancelDeposit.setForeground(new java.awt.Color(240, 240, 240));
        btCancelDeposit.setText("Zrušiť");
        btCancelDeposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelDepositActionPerformed(evt);
            }
        });
        toolbarDepositPanel.add(btCancelDeposit);

        btDeposit1.setBackground(new java.awt.Color(31, 31, 31));
        btDeposit1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btDeposit1.setForeground(new java.awt.Color(240, 240, 240));
        btDeposit1.setText("Vložiť");
        btDeposit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeposit1ActionPerformed(evt);
            }
        });
        toolbarDepositPanel.add(btDeposit1);

        depositPanel.add(toolbarDepositPanel);

        actionContainerPanel.add(depositPanel, bundle.getString("CARD3")); // NOI18N

        withdrawalPanel.setLayout(new javax.swing.BoxLayout(withdrawalPanel, javax.swing.BoxLayout.Y_AXIS));

        contentWithdrawalPanel.setBackground(new java.awt.Color(18, 18, 18));
        contentWithdrawalPanel.setForeground(new java.awt.Color(240, 240, 240));
        contentWithdrawalPanel.setPreferredSize(new java.awt.Dimension(450, 200));
        contentWithdrawalPanel.setLayout(new java.awt.GridBagLayout());

        lbWithdrawal.setBackground(new java.awt.Color(18, 18, 18));
        lbWithdrawal.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbWithdrawal.setForeground(new java.awt.Color(240, 240, 240));
        lbWithdrawal.setText(bundle.getString("SUMA:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentWithdrawalPanel.add(lbWithdrawal, gridBagConstraints);

        spinnerWithdrawal.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        spinnerWithdrawal.setModel(new javax.swing.SpinnerNumberModel(0.01d, 0.01d, null, 0.5d));
        spinnerWithdrawal.setEditor(new javax.swing.JSpinner.NumberEditor(spinnerWithdrawal, "0.00"));
        spinnerWithdrawal.setMinimumSize(new java.awt.Dimension(250, 30));
        spinnerWithdrawal.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        contentWithdrawalPanel.add(spinnerWithdrawal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        contentWithdrawalPanel.add(fillerContentWithdrawal, gridBagConstraints);

        withdrawalPanel.add(contentWithdrawalPanel);

        toolbarWithdrawalPanel.setBackground(new java.awt.Color(18, 18, 18));
        toolbarWithdrawalPanel.setForeground(new java.awt.Color(240, 240, 240));
        toolbarWithdrawalPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        toolbarWithdrawalPanel.setLayout(new java.awt.GridLayout(1, 0));

        btCancelWithdrawal.setBackground(new java.awt.Color(31, 31, 31));
        btCancelWithdrawal.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btCancelWithdrawal.setForeground(new java.awt.Color(240, 240, 240));
        btCancelWithdrawal.setText(bundle.getString("ZRUŠIŤ")); // NOI18N
        btCancelWithdrawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelWithdrawalActionPerformed(evt);
            }
        });
        toolbarWithdrawalPanel.add(btCancelWithdrawal);

        btWithdrawal1.setBackground(new java.awt.Color(31, 31, 31));
        btWithdrawal1.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btWithdrawal1.setForeground(new java.awt.Color(240, 240, 240));
        btWithdrawal1.setText(bundle.getString("VYBRAŤ")); // NOI18N
        btWithdrawal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btWithdrawal1ActionPerformed(evt);
            }
        });
        toolbarWithdrawalPanel.add(btWithdrawal1);

        withdrawalPanel.add(toolbarWithdrawalPanel);

        actionContainerPanel.add(withdrawalPanel, bundle.getString("CARD4")); // NOI18N

        actionHolderPanel.add(actionContainerPanel);

        customerActionPanel.add(actionHolderPanel);

        containerPanel.add(customerActionPanel, "card7");

        getContentPane().add(containerPanel, new java.awt.GridBagConstraints());

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegisterActionPerformed
        layout.show(containerPanel, bundle.getString("CARD3"));
        setTitle(bundle.getString("RAYBANK - REGISTRÁCIA"));
        resetLogin();
    }//GEN-LAST:event_btRegisterActionPerformed

    private void btRegisterBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegisterBackActionPerformed
        layout.show(containerPanel, bundle.getString("CARD2"));
        setTitle(bundle.getString("RAYBANK - VITAJTE"));
        resetRegistration();
    }//GEN-LAST:event_btRegisterBackActionPerformed

    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoginActionPerformed
        User loggedin = null;
        for (User u : userList) {
            if (tfUsername.getText().equals(u.getUsername()) && String.valueOf(pfPassword.getPassword()).equals(u.getPassword())) {
                loggedin = u;
                break;
            }
        }
        
        if (loggedin != null) {
            currentUser = loggedin;
            
            if (loggedin instanceof Admin) {
            setTitle(java.text.MessageFormat.format(bundle.getString("RAYBANK - ADMIN ({0} {1})"), new Object[] {loggedin.getName(), loggedin.getSurname()}));
                layout.show(containerPanel, bundle.getString("CARD4"));
                
            } else if (loggedin instanceof Employee) {
                setTitle(java.text.MessageFormat.format(bundle.getString("RAYBANK - ZAMESTNANEC ({0} {1})"), new Object[] {loggedin.getName(), loggedin.getSurname()}));
                layout.show(containerPanel, bundle.getString("CARD5"));
                resetEmployeeCustomerList();
                
            }else if (loggedin instanceof Customer) {
                setTitle(bundle.getString("RAYBANK - PREHĽAD"));
                layout.show(containerPanel, bundle.getString("CARD6"));
                
                createTransactionSubList(transactionSubList, ((Customer) loggedin).getAccount());
                
                //lbGreeting.setText("Dobrý deň, " + loggedin.getName());
                lbGreeting.setText(java.text.MessageFormat.format(bundle.getString("DOBRÝ DEŇ, {0}"), new Object[] {loggedin.getName()}));
                lbCustomerAccountType.setText(((Customer) loggedin).getAccount().getType(bundle) + bundle.getString(" ÚČET"));
                tfCustomerIban.setText(((Customer) loggedin).getAccount().getIban());
                tfCustomerBalance.setText(((Customer) loggedin).getAccount().getBalance().toString());
                tfName.setText(loggedin.getName());
                tfSurname.setText(loggedin.getSurname());
                tfIban.setText(((Customer) loggedin).getAccount().getIban());
                
                for (User u : userList) {
                    if (u instanceof Customer && !u.equals(loggedin)) receiverComboList.addElement(u);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, bundle.getString("NESPRÁVNE PRIHLASOVACIE ÚDAJE"), bundle.getString("CHYBA PRIHLÁSENIA"), JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, bundle.getString("INCORRECT LOGIN CREDENTIALS WERE USED."));
        }
        
        resetLogin();
    }//GEN-LAST:event_btLoginActionPerformed

    private void btAdminLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdminLogoutActionPerformed
        layout.show(containerPanel, bundle.getString("CARD2"));
        setTitle(bundle.getString("RAYBANK - VITAJTE"));
        
        for (AccountDetail a : accDetailList) a.dispose();
        accDetailList.clear();
        for (EmployeeDetail e : empDetailList) e.dispose();
        empDetailList.clear();
        
        listAdminAccounts.clearSelection();
        listAdminEmployees.clearSelection();
        resetAddEmployee();
        currentUser = null;
    }//GEN-LAST:event_btAdminLogoutActionPerformed

    private void btEmployeeLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEmployeeLogoutActionPerformed
        layout.show(containerPanel, bundle.getString("CARD2"));
        setTitle(bundle.getString("RAYBANK - VITAJTE"));
        
        for (AccountDetail a : accDetailList) a.dispose();
        accDetailList.clear();
        
        currentUser = null;
        transactionSubList.removeAllElements();
    }//GEN-LAST:event_btEmployeeLogoutActionPerformed

    private void btCustomerLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCustomerLogoutActionPerformed
        layout.show(containerPanel, bundle.getString("CARD2"));
        setTitle(bundle.getString("RAYBANK - VITAJTE"));
        
        for (ReportPopup r : reportPopupList) r.dispose();
        reportPopupList.clear();
        
        currentUser = null;
        resetCustomer();
    }//GEN-LAST:event_btCustomerLogoutActionPerformed

    private void btCancelPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelPaymentActionPerformed
        setTitle(bundle.getString("RAYBANK - PREHĽAD"));
        layout.show(containerPanel, bundle.getString("CARD6"));
        spinnerPayment.setValue(BigDecimal.valueOf(0.01));
    }//GEN-LAST:event_btCancelPaymentActionPerformed

    private void btCancelDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelDepositActionPerformed
        setTitle(bundle.getString("RAYBANK - PREHĽAD"));
        layout.show(containerPanel, bundle.getString("CARD6"));
        spinnerDeposit.setValue(BigDecimal.valueOf(0.01));
    }//GEN-LAST:event_btCancelDepositActionPerformed

    private void btCancelWithdrawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelWithdrawalActionPerformed
        setTitle(bundle.getString("RAYBANK - PREHĽAD"));
        layout.show(containerPanel, bundle.getString("CARD6"));
        spinnerWithdrawal.setValue(BigDecimal.valueOf(0.01));
    }//GEN-LAST:event_btCancelWithdrawalActionPerformed

    private void btPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPaymentActionPerformed
        layout.show(containerPanel, bundle.getString("CARD7"));
        actionLayout.show(actionContainerPanel, bundle.getString("CARD2"));
        setTitle(bundle.getString("RAYBANK - PLATBA"));
        lbTitle.setText(bundle.getString("VYKONAŤ PLATBU"));
        lbSender.setText(bundle.getString("ODOSIELATEĽ"));
        tfBalance.setText(((Customer) currentUser).getAccount().getBalance().toString());
    }//GEN-LAST:event_btPaymentActionPerformed

    private void btWithdrawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWithdrawalActionPerformed
        layout.show(containerPanel, bundle.getString("CARD7"));
        actionLayout.show(actionContainerPanel, bundle.getString("CARD4"));
        setTitle(bundle.getString("RAYBANK - VÝBER"));
        lbTitle.setText(bundle.getString("VYBRAŤ Z ÚČTU"));
        lbSender.setText(bundle.getString(""));
        tfBalance.setText(((Customer) currentUser).getAccount().getBalance().toString());
    }//GEN-LAST:event_btWithdrawalActionPerformed

    private void btDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDepositActionPerformed
        layout.show(containerPanel, bundle.getString("CARD7"));
        actionLayout.show(actionContainerPanel, bundle.getString("CARD3"));
        setTitle(bundle.getString("RAYBANK - VKLAD"));
        lbTitle.setText(bundle.getString("VLOŽIŤ NA ÚČET"));
        lbSender.setText(bundle.getString(""));
        tfBalance.setText(((Customer) currentUser).getAccount().getBalance().toString());
    }//GEN-LAST:event_btDepositActionPerformed

    private void btRegisterEmployeeConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegisterEmployeeConfirmActionPerformed
        if (tfRegisterEmployeeName.getText().length() > 0 && tfRegisterEmployeeSurname.getText().length() > 0 && tfRegisterEmployeeAddress.getText().length() > 0 && tfRegisterEmployeeEmail.getText().length() > 0 && tfRegisterEmployeeUsername.getText().length() > 0 && tfRegisterEmployeePassword.getText().length() > 0 && tfRegisterEmployeePosition.getText().length() > 0) {
            if (validateEmailAddress(tfRegisterEmployeeEmail.getText())) {
                if (checkUniqueUsername(tfRegisterEmployeeUsername.getText())) {
                    Employee newEmployee = new Employee(tfRegisterEmployeeUsername.getText(), tfRegisterEmployeePassword.getText(), tfRegisterEmployeeName.getText(), tfRegisterEmployeeSurname.getText(), tfRegisterEmployeeAddress.getText(), tfRegisterEmployeeEmail.getText(), (Date) spinnerAdminEmployee.getValue(), tfRegisterEmployeePosition.getText());
                    userList.add(newEmployee);
                    employeeList.addElement(newEmployee);
                    
                    resetAddEmployee();

                    JOptionPane.showMessageDialog(adminAddEmployeePanel, bundle.getString("NOVÝ ZAMESTNANEC BOL ÚSPEŠNE PRIDANÝ."), bundle.getString("PRIDANIE ZAMESTNANCA"), JOptionPane.INFORMATION_MESSAGE);
                    
                } else {
                    JOptionPane.showMessageDialog(adminAddEmployeePanel, bundle.getString("TOTO PRIHLASOVACIE MENO UŽ SA POUŽÍVA. PROSÍM, ZVOĽTE INÉ."), bundle.getString("CHYBA PRI PRIDANÍ ZAMESTNANCA"), JOptionPane.ERROR_MESSAGE);
                    logger.log(Level.WARNING, String.format(bundle.getString("USERNAME SPECIFIED IN EMPLOYEE REGISTRATION IS ALREADY IN USE. (USER: %S)"), currentUser.getUsername()));
                }
            } else {
                JOptionPane.showMessageDialog(adminAddEmployeePanel, bundle.getString("EMAILOVÁ ADRESA NEMÁ SPRÁVNY FORMÁT."), bundle.getString("CHYBA PRI PRIDANÍ ZAMESTNANCA"), JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, String.format(bundle.getString("INCORRECT EMAIL ADDRESS FORMAT SPECIFIED IN EMPLOYEE REGISTRATION. (USER: %S)"), currentUser.getUsername()));
            }
        } else {
            JOptionPane.showMessageDialog(adminAddEmployeePanel, bundle.getString("PROSÍM, VYPLŇTE VŠETKY POTREBNÉ POLIA."), bundle.getString("CHYBA PRI PRIDANÍ ZAMESTNANCA"), JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, String.format(bundle.getString("SOME OF THE REQUIRED VALUES WERE NOT SPECIFIED IN EMPLOYEE REGISTRATION. (USER: %S)"), currentUser.getUsername()));
        }    
    }//GEN-LAST:event_btRegisterEmployeeConfirmActionPerformed

    private void btRegisterConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegisterConfirmActionPerformed
        if (tfRegisterName.getText().length() > 0 && tfRegisterSurname.getText().length() > 0 && tfRegisterAddress.getText().length() > 0 && tfRegisterEmail.getText().length() > 0 && tfRegisterUsername.getText().length() > 0 && tfRegisterPassword.getText().length() > 0) {
            if (validateEmailAddress(tfRegisterEmail.getText())) {
                if (checkUniqueUsername(tfRegisterUsername.getText())) {
                    Account newAccount = new Account(String.valueOf(cbAccountType.getSelectedItem()), null, accountList, bundle);
                    Customer newCustomer = new Customer(tfRegisterUsername.getText(), tfRegisterPassword.getText(), tfRegisterName.getText(), tfRegisterSurname.getText(), tfRegisterAddress.getText(), tfRegisterEmail.getText(), (Date) spinnerBirthDate.getValue(), newAccount);
                    newAccount.setOwner(newCustomer);
                    accountList.addElement(newAccount);
                    userList.add(newCustomer);
                    customerComboList.addElement(newCustomer);
                    
                    resetRegistration();

                    JOptionPane.showMessageDialog(registerFormPanel, bundle.getString("REGISTRÁCIA PREBEHLA ÚSPEŠNE."), bundle.getString("REGISTRÁCIA"), JOptionPane.INFORMATION_MESSAGE);

                    layout.show(containerPanel, bundle.getString("CARD2"));
                    setTitle(bundle.getString("RAYBANK - VITAJTE"));
                    
                } else {
                    JOptionPane.showMessageDialog(registerFormPanel, bundle.getString("TOTO PRIHLASOVACIE MENO UŽ SA POUŽÍVA. PROSÍM, ZVOĽTE INÉ."), bundle.getString("CHYBA REGISTRÁCIE"), JOptionPane.ERROR_MESSAGE);
                    logger.log(Level.WARNING, bundle.getString("USERNAME SPECIFIED IN CUSTOMER REGISTRATION IS ALREADY IN USE."));
                }
            } else {
                JOptionPane.showMessageDialog(registerFormPanel, bundle.getString("EMAILOVÁ ADRESA NEMÁ SPRÁVNY FORMÁT."), bundle.getString("CHYBA REGISTRÁCIE"), JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, bundle.getString("INCORRECT EMAIL ADDRESS FORMAT SPECIFIED IN CUSTOMER REGISTRATION."));
            }
        } else {
            JOptionPane.showMessageDialog(registerFormPanel, bundle.getString("PROSÍM, VYPLŇTE VŠETKY POTREBNÉ POLIA."), bundle.getString("CHYBA REGISTRÁCIE"), JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, bundle.getString("SOME OF THE REQUIRED VALUES WERE NOT SPECIFIED IN CUSTOMER REGISTRATION."));
        }
    }//GEN-LAST:event_btRegisterConfirmActionPerformed

    private void btAdminAccountDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdminAccountDetailActionPerformed
        int selected = listAdminAccounts.getSelectedIndex();
        if (selected >= 0) {
            AccountDetail popup = new AccountDetail(currentUser, (Account) accountList.getElementAt(selected), bundle);
            accDetailList.add(popup);
            popup.setVisible(true);
            listAdminAccounts.clearSelection();
        } else {
            logger.log(Level.WARNING, String.format(bundle.getString("NO ACCOUNT SELECTED TO VIEW. (USER: %S)"), currentUser.getUsername()));
        }
    }//GEN-LAST:event_btAdminAccountDetailActionPerformed

    private void btRemoveEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveEmployeeActionPerformed
        int selected = listAdminEmployees.getSelectedIndex();
        if (selected >= 0) {
            Employee toRemove = (Employee) employeeList.getElementAt(selected);
            userList.remove(toRemove);
            employeeList.removeElement(toRemove);
        } else {
            logger.log(Level.WARNING, String.format(bundle.getString("NO EMPLOYEE SELECTED TO DELETE. (USER: %S)"), currentUser.getUsername()));
        }
    }//GEN-LAST:event_btRemoveEmployeeActionPerformed

    private void cbReceiverItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbReceiverItemStateChanged
        if (cbReceiver.getItemCount() > 0) {
            tfReceiverIban.setText(((Customer) cbReceiver.getSelectedItem()).getAccount().getIban());
        } else {
            tfReceiverIban.setText(bundle.getString(""));
        }
    }//GEN-LAST:event_cbReceiverItemStateChanged

    private void btRemoveAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveAccountActionPerformed
        int selected = listAdminAccounts.getSelectedIndex();
        if (selected >= 0) {
            Account toRemove = (Account) accountList.getElementAt(selected);
            userList.remove(toRemove.getOwner());
            customerComboList.removeElement(toRemove.getOwner());
            accountList.removeElement(toRemove);
        } else {
            logger.log(Level.WARNING, String.format(bundle.getString("NO ACCOUNT SELECTED TO DELETE. (USER: %S)"), currentUser.getUsername()));
        }
    }//GEN-LAST:event_btRemoveAccountActionPerformed

    private void btEmployeeCustomerDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEmployeeCustomerDetailActionPerformed
        int selected = cbEmployeeCustomer.getSelectedIndex();
        if (selected >= 0) {
            AccountDetail popup = new AccountDetail(currentUser, (Account) ((Customer) customerComboList.getElementAt(selected)).getAccount(), bundle);
            accDetailList.add(popup);
            popup.setVisible(true);
        } else {
            logger.log(Level.WARNING, String.format(bundle.getString("NO ACCOUNT SELECTED TO VIEW. (USER: %S)"), currentUser.getUsername()));
        }
    }//GEN-LAST:event_btEmployeeCustomerDetailActionPerformed

    private void btPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPayActionPerformed
        int selected = cbReceiver.getSelectedIndex();
        if (selected >= 0) {
            Customer payer = (Customer) currentUser;
            Customer receiver = (Customer) receiverComboList.getElementAt(selected);
            BigDecimal amount = BigDecimal.valueOf((double) spinnerPayment.getValue());
            
            if (amount.compareTo(payer.getAccount().getBalance()) < 1) {
                Payment newPayment = new Payment(payer.getAccount(), amount, new Date(System.currentTimeMillis()), receiver.getAccount());
                payer.getAccount().setBalance(payer.getAccount().getBalance().subtract(amount));
                receiver.getAccount().setBalance(receiver.getAccount().getBalance().add(amount));
                transactionList.add(newPayment);
                transactionSubList.addElement(newPayment);
                
                spinnerPayment.setValue(BigDecimal.valueOf(0.01));
                JOptionPane.showMessageDialog(paymentPanel, bundle.getString("PLATBA PREBEHLA ÚSPEŠNE."), bundle.getString("PLATBA"), JOptionPane.INFORMATION_MESSAGE);
                tfCustomerBalance.setText(payer.getAccount().getBalance().toString());
                setTitle(bundle.getString("RAYBANK - PREHĽAD"));
                layout.show(containerPanel, bundle.getString("CARD6"));
            } else {
                JOptionPane.showMessageDialog(paymentPanel, bundle.getString("NEDOSTATOK FINANCIÍ NA REALIZOVANIE PLATBY"), bundle.getString("CHYBA PRI PLATBE"), JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, String.format(bundle.getString("INSUFFICIENT FUNDS FOR PAYMENT. (USER: %S)"), currentUser.getUsername()));
            }
        } else {
            logger.log(Level.WARNING, String.format(bundle.getString("NO RECEIVER ACCOUNT SELECTED. (USER: %S)"), currentUser.getUsername()));
        }
    }//GEN-LAST:event_btPayActionPerformed

    private void btDeposit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeposit1ActionPerformed
        BigDecimal amount = BigDecimal.valueOf((double) spinnerDeposit.getValue());
        Deposit newDeposit = new Deposit(((Customer) currentUser).getAccount(), amount, new Date(System.currentTimeMillis()));
        transactionList.add(newDeposit);
        depositList.addElement(newDeposit);
        transactionSubList.addElement(newDeposit);
        
        spinnerDeposit.setValue(BigDecimal.valueOf(0.01));
        JOptionPane.showMessageDialog(depositPanel, bundle.getString("VKLAD BOL ZAZNAMENANÝ A ČAKÁ NA SPRACOVANIE."), bundle.getString("VKLAD"), JOptionPane.INFORMATION_MESSAGE);
        setTitle(bundle.getString("RAYBANK - PREHĽAD"));
        layout.show(containerPanel, bundle.getString("CARD6"));
    }//GEN-LAST:event_btDeposit1ActionPerformed

    private void btWithdrawal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btWithdrawal1ActionPerformed
        Customer withdrawer = (Customer) currentUser;
        
        BigDecimal amount = BigDecimal.valueOf((double) spinnerWithdrawal.getValue());
        if (amount.compareTo(withdrawer.getAccount().getBalance()) < 1) {
            Withdrawal newWithdrawal = new Withdrawal(withdrawer.getAccount(), amount, new Date(System.currentTimeMillis()));
            withdrawer.getAccount().setBalance(withdrawer.getAccount().getBalance().subtract(amount));
            transactionList.add(newWithdrawal);
            transactionSubList.addElement(newWithdrawal);
            
            spinnerWithdrawal.setValue(BigDecimal.valueOf(0.01));
            JOptionPane.showMessageDialog(withdrawalPanel, bundle.getString("VÝBER PREBEHOL ÚSPEŠNE."), bundle.getString("VÝBER"), JOptionPane.INFORMATION_MESSAGE);
            tfCustomerBalance.setText(withdrawer.getAccount().getBalance().toString());
            setTitle(bundle.getString("RAYBANK - PREHĽAD"));
            layout.show(containerPanel, bundle.getString("CARD6"));
        } else {
            JOptionPane.showMessageDialog(withdrawalPanel, bundle.getString("NEDOSTATOK FINANCIÍ NA VÝBER"), bundle.getString("CHYBA PRI VÝBERE"), JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, String.format(bundle.getString("INSUFFICIENT FUNDS FOR WITHDRAWAL. (USER: %S)"), currentUser.getUsername()));
        }
    }//GEN-LAST:event_btWithdrawal1ActionPerformed

    private void cbEmployeeCustomerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbEmployeeCustomerItemStateChanged
        resetEmployeeCustomerList();
    }//GEN-LAST:event_cbEmployeeCustomerItemStateChanged

    private void btConfirmDepositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConfirmDepositActionPerformed
        int selected = listDeposit.getSelectedIndex();
        if (selected >= 0) {
            Deposit toConfirm = (Deposit) depositList.getElementAt(selected);
            toConfirm.setConfirmed(true);
            toConfirm.getInitiatorAccount().setBalance(toConfirm.getInitiatorAccount().getBalance().add(toConfirm.getAmount()));
            toConfirm.setTimestamp(new Date(System.currentTimeMillis()));
            depositList.removeElement(toConfirm);
            resetEmployeeCustomerList();
        } else {
            logger.log(Level.WARNING, String.format(bundle.getString("NO UNCONFIRMED DEPOSIT SELECTED TO CONFIRM. (USER: %S)"), currentUser.getUsername()));
        }
    }//GEN-LAST:event_btConfirmDepositActionPerformed

    private void btAdminEmployeeDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdminEmployeeDetailActionPerformed
        int selected = listAdminEmployees.getSelectedIndex();
        if (selected >= 0) {
            EmployeeDetail popup = new EmployeeDetail((Employee) employeeList.getElementAt(selected), bundle);
            empDetailList.add(popup);
            popup.setVisible(true);
            listAdminEmployees.clearSelection();
        } else {
            logger.log(Level.WARNING, String.format(bundle.getString("NO EMPLOYEE SELECTED TO VIEW. (USER: %S)"), currentUser.getUsername()));
        }
    }//GEN-LAST:event_btAdminEmployeeDetailActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        serialize();
        logger.log(Level.INFO, bundle.getString("---- APP SESSION CLOSED ----"));
    }//GEN-LAST:event_formWindowClosing

    private void listTransactionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listTransactionsValueChanged
        int selected = listTransactions.getSelectedIndex();
        if (selected >= 0) {
            Transaction viewed = (Transaction) transactionSubList.getElementAt(selected);
            listTransactions.setToolTipText(viewed.toString());
        } else {
            listTransactions.setToolTipText(null);
        }
    }//GEN-LAST:event_listTransactionsValueChanged

    private void listEmployeeCustomerValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listEmployeeCustomerValueChanged
        int selected = listEmployeeCustomer.getSelectedIndex();
        if (selected >= 0) {
            Transaction viewed = (Transaction) transactionSubList.getElementAt(selected);
            listEmployeeCustomer.setToolTipText(viewed.toString());
        } else {
            listEmployeeCustomer.setToolTipText(null);
        }
    }//GEN-LAST:event_listEmployeeCustomerValueChanged

    private void btReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReportActionPerformed
        ReportPopup popup = new ReportPopup((Customer) currentUser, transactionSubList, logger, bundle);
        reportPopupList.add(popup);
        popup.setVisible(true);
    }//GEN-LAST:event_btReportActionPerformed

    private void btSlovakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSlovakActionPerformed
        bundle = java.util.ResourceBundle.getBundle("view/Bundle_SVK");;
        internationalize();
    }//GEN-LAST:event_btSlovakActionPerformed

    private void btEnglishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEnglishActionPerformed
        bundle = java.util.ResourceBundle.getBundle("view/Bundle_ENG");
        internationalize();
    }//GEN-LAST:event_btEnglishActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actionContainerPanel;
    private javax.swing.JPanel actionHolderPanel;
    private javax.swing.JPanel adminAddEmployeeHolderPanel;
    private javax.swing.JPanel adminAddEmployeePanel;
    private javax.swing.JPanel adminListPanel;
    private javax.swing.JPanel adminLogoutPanel;
    private javax.swing.JPanel adminPanel;
    private javax.swing.JButton btAdminAccountDetail;
    private javax.swing.JButton btAdminEmployeeDetail;
    private javax.swing.JButton btAdminLogout;
    private javax.swing.JButton btCancelDeposit;
    private javax.swing.JButton btCancelPayment;
    private javax.swing.JButton btCancelWithdrawal;
    private javax.swing.JButton btConfirmDeposit;
    private javax.swing.JButton btCustomerLogout;
    private javax.swing.JButton btDeposit;
    private javax.swing.JButton btDeposit1;
    private javax.swing.JButton btEmployeeCustomerDetail;
    private javax.swing.JButton btEmployeeLogout;
    private javax.swing.JButton btEnglish;
    private javax.swing.JButton btLogin;
    private javax.swing.JButton btPay;
    private javax.swing.JButton btPayment;
    private javax.swing.JButton btRegister;
    private javax.swing.JButton btRegisterBack;
    private javax.swing.JButton btRegisterConfirm;
    private javax.swing.JButton btRegisterEmployeeConfirm;
    private javax.swing.JButton btRemoveAccount;
    private javax.swing.JButton btRemoveEmployee;
    private javax.swing.JButton btReport;
    private javax.swing.JButton btSlovak;
    private javax.swing.JButton btWithdrawal;
    private javax.swing.JButton btWithdrawal1;
    private javax.swing.JComboBox<String> cbAccountType;
    private javax.swing.JComboBox<String> cbEmployeeCustomer;
    private javax.swing.JComboBox<String> cbReceiver;
    private javax.swing.JPanel commonPanel;
    private javax.swing.JPanel containerPanel;
    private javax.swing.JPanel contentDepositPanel;
    private javax.swing.JPanel contentPaymentPanel;
    private javax.swing.JPanel contentWithdrawalPanel;
    private javax.swing.JPanel customerAccountHolderPanel;
    private javax.swing.JPanel customerAccountPanel;
    private javax.swing.JPanel customerActionImagePanel;
    private javax.swing.JPanel customerActionPanel;
    private javax.swing.JPanel customerListPanel;
    private javax.swing.JPanel customerLogoutPanel;
    private javax.swing.JPanel customerPanel;
    private javax.swing.JPanel depositPanel;
    private javax.swing.JPanel employeeListPanel;
    private javax.swing.JPanel employeeLogoutPanel;
    private javax.swing.JPanel employeePanel;
    private javax.swing.JPanel employeeWatchAccountHolderPanel;
    private javax.swing.JPanel employeeWatchAccountPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler fillerAdmin;
    private javax.swing.Box.Filler fillerAdminLists;
    private javax.swing.Box.Filler fillerContentDeposit;
    private javax.swing.Box.Filler fillerContentWithdrawal;
    private javax.swing.Box.Filler fillerCustomer;
    private javax.swing.Box.Filler fillerCustomerBody;
    private javax.swing.Box.Filler fillerCustomerHeader;
    private javax.swing.Box.Filler fillerEmployee;
    private javax.swing.Box.Filler fillerPayment;
    private javax.swing.Box.Filler fillerPopupBody;
    private javax.swing.Box.Filler fillerPopupHeader;
    private javax.swing.Box.Filler fillerTransactions;
    private javax.swing.JPanel formPanel;
    private javax.swing.JPanel languagePanel;
    private javax.swing.JLabel lbAccountList;
    private javax.swing.JLabel lbAccountType;
    private javax.swing.JLabel lbBalance;
    private javax.swing.JLabel lbBirthDate;
    private javax.swing.JLabel lbCustomerAccountType;
    private javax.swing.JLabel lbCustomerActionImage;
    private javax.swing.JLabel lbCustomerBalance;
    private javax.swing.JLabel lbCustomerIban;
    private javax.swing.JLabel lbDeposit;
    private javax.swing.JLabel lbEmployeeBirthDate;
    private javax.swing.JLabel lbEmployeeChooseCustomer;
    private javax.swing.JLabel lbEmployeeList;
    private javax.swing.JLabel lbEmployeePayList;
    private javax.swing.JLabel lbEmployeePayments;
    private javax.swing.JLabel lbGreeting;
    private javax.swing.JLabel lbIban;
    private javax.swing.JLabel lbLoginImage;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbNewCustomer;
    private javax.swing.JLabel lbNewEmployee;
    private javax.swing.JLabel lbNewEmployeePosition;
    private javax.swing.JLabel lbPayment;
    private javax.swing.JLabel lbReceiver;
    private javax.swing.JLabel lbReceiverIban;
    private javax.swing.JLabel lbRegisterAddress;
    private javax.swing.JLabel lbRegisterEmail;
    private javax.swing.JLabel lbRegisterEmployeeAddress;
    private javax.swing.JLabel lbRegisterEmployeeEmail;
    private javax.swing.JLabel lbRegisterEmployeeName;
    private javax.swing.JLabel lbRegisterEmployeeSurname;
    private javax.swing.JLabel lbRegisterEmployeeUsername;
    private javax.swing.JLabel lbRegisterImage;
    private javax.swing.JLabel lbRegisterName;
    private javax.swing.JLabel lbRegisterPassword;
    private javax.swing.JLabel lbRegisterSurname;
    private javax.swing.JLabel lbRegisterUsername;
    private javax.swing.JLabel lbRegisteremployeePassword;
    private javax.swing.JLabel lbSender;
    private javax.swing.JLabel lbSurname;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTranasctions;
    private javax.swing.JLabel lbUserPassword;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JLabel lbWithdrawal;
    private javax.swing.JList<String> listAdminAccounts;
    private javax.swing.JList<String> listAdminEmployees;
    private javax.swing.JList<String> listDeposit;
    private javax.swing.JList<String> listEmployeeCustomer;
    private javax.swing.JList<String> listTransactions;
    private javax.swing.JPanel loginImagePanel;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JPanel newUserPanel;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JPanel registerBackPanel;
    private javax.swing.JPanel registerFormHolderPanel;
    private javax.swing.JPanel registerFormPanel;
    private javax.swing.JPanel registerImagePanel;
    private javax.swing.JPanel registerPanel;
    private javax.swing.JScrollPane scrollAdminAccounts;
    private javax.swing.JScrollPane scrollAdminEmployees;
    private javax.swing.JScrollPane scrollDeposit;
    private javax.swing.JScrollPane scrollEmployeeCustomer;
    private javax.swing.JScrollPane scrollTransactions;
    private javax.swing.JSpinner spinnerAdminEmployee;
    private javax.swing.JSpinner spinnerBirthDate;
    private javax.swing.JSpinner spinnerDeposit;
    private javax.swing.JSpinner spinnerPayment;
    private javax.swing.JSpinner spinnerWithdrawal;
    private javax.swing.JTextField tfBalance;
    private javax.swing.JTextField tfCustomerBalance;
    private javax.swing.JTextField tfCustomerIban;
    private javax.swing.JTextField tfIban;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfReceiverIban;
    private javax.swing.JTextField tfRegisterAddress;
    private javax.swing.JTextField tfRegisterEmail;
    private javax.swing.JTextField tfRegisterEmployeeAddress;
    private javax.swing.JTextField tfRegisterEmployeeEmail;
    private javax.swing.JTextField tfRegisterEmployeeName;
    private javax.swing.JTextField tfRegisterEmployeePassword;
    private javax.swing.JTextField tfRegisterEmployeePosition;
    private javax.swing.JTextField tfRegisterEmployeeSurname;
    private javax.swing.JTextField tfRegisterEmployeeUsername;
    private javax.swing.JTextField tfRegisterName;
    private javax.swing.JTextField tfRegisterPassword;
    private javax.swing.JTextField tfRegisterSurname;
    private javax.swing.JTextField tfRegisterUsername;
    private javax.swing.JTextField tfSurname;
    private javax.swing.JTextField tfUsername;
    private javax.swing.JPanel toolbarDepositPanel;
    private javax.swing.JPanel toolbarPaymentPanel;
    private javax.swing.JPanel toolbarWithdrawalPanel;
    private javax.swing.JPanel userLoginPanel;
    private javax.swing.JPanel withdrawalPanel;
    // End of variables declaration//GEN-END:variables
}
