/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.Account;
import model.Admin;
import model.User;

/**
 * GUI class showing information about a customer and related account
 * 
 */
public class AccountDetail extends javax.swing.JFrame {
    
    private java.util.ResourceBundle bundle;
    private String password;
    /**
     * Creates new form AccountDetail
     * @param person instance of an admin or employee currently accessing this popup
     * @param account instance of a customer's account to be shown in detail
     * @param bundle I18N ResourceBundle currently in use
     */
    public AccountDetail(User person, Account account, java.util.ResourceBundle bundle) {
        this.bundle = bundle;
        initComponents();
        internationalize();
        password = account.getOwner().getPassword();
        tfIban.setText(account.getIban());
        tfType.setText(account.getType(bundle));
        tfBalance.setText(account.getBalance().toString());
        
        tfUsername.setText(account.getOwner().getUsername());
        tfPassword.setText(password.replaceAll(".", "*"));
        tfName.setText(account.getOwner().getName());
        tfSurname.setText(account.getOwner().getSurname());
        tfBirthDate.setText(account.getOwner().getStringBirthdate());
        tfAddress.setText(account.getOwner().getAddress());
        tfEmail.setText(account.getOwner().getEmail());
        
        if (person instanceof Admin) {
            chbSeePassword.setEnabled(true);
        } else {
            chbSeePassword.setEnabled(false);
        }
    }
    
    private void internationalize() {
        setTitle(bundle.getString("RAYBANK - DETAIL ÚČTU"));
        lbTitle.setText(bundle.getString("DETAIL ÚČTU"));
        lbIban.setText(bundle.getString("IBAN:"));
        lbType.setText(bundle.getString("TYP ÚČTU:"));
        lbBalance.setText(bundle.getString("ZOSTATOK:"));
        lbOwner.setText(bundle.getString("INFORMÁCIE O VLASTNÍKOVI"));
        lbName.setText(bundle.getString("KRSTNÉ MENO:"));
        lbSurname.setText(bundle.getString("PRIEZVISKO:"));
        lbBirthDate.setText(bundle.getString("DÁTUM NARODENIA:"));
        lbAddress.setText(bundle.getString("ADRESA BYDLISKA:"));
        lbEmail.setText(bundle.getString("EMAILOVÁ ADRESA:"));
        btClose.setText(bundle.getString("ZATVORIŤ"));
        lbUsername.setText(bundle.getString("PRIHLASOVACIE MENO:"));
        lbPassword.setText(bundle.getString("HESLO:"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * 
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        accountDetailPanel = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        lbIban = new javax.swing.JLabel();
        tfIban = new javax.swing.JTextField();
        lbType = new javax.swing.JLabel();
        tfType = new javax.swing.JTextField();
        lbBalance = new javax.swing.JLabel();
        tfBalance = new javax.swing.JTextField();
        lbOwner = new javax.swing.JLabel();
        lbName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lbSurname = new javax.swing.JLabel();
        tfSurname = new javax.swing.JTextField();
        lbBirthDate = new javax.swing.JLabel();
        tfBirthDate = new javax.swing.JTextField();
        lbAddress = new javax.swing.JLabel();
        tfAddress = new javax.swing.JTextField();
        lbEmail = new javax.swing.JLabel();
        tfEmail = new javax.swing.JTextField();
        btClose = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 50));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 30), new java.awt.Dimension(0, 30), new java.awt.Dimension(32767, 50));
        lbUsername = new javax.swing.JLabel();
        tfUsername = new javax.swing.JTextField();
        lbPassword = new javax.swing.JLabel();
        tfPassword = new javax.swing.JTextField();
        chbSeePassword = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("view/Bundle_SVK"); // NOI18N
        setTitle(bundle.getString("RAYBANK - DETAIL ÚČTU")); // NOI18N
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        accountDetailPanel.setBackground(new java.awt.Color(18, 18, 18));
        accountDetailPanel.setForeground(new java.awt.Color(240, 240, 240));
        accountDetailPanel.setPreferredSize(new java.awt.Dimension(450, 600));
        accountDetailPanel.setLayout(new java.awt.GridBagLayout());

        lbTitle.setBackground(new java.awt.Color(18, 18, 18));
        lbTitle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(240, 240, 240));
        lbTitle.setText(bundle.getString("DETAIL ÚČTU")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbTitle, gridBagConstraints);

        lbIban.setBackground(new java.awt.Color(18, 18, 18));
        lbIban.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbIban.setForeground(new java.awt.Color(240, 240, 240));
        lbIban.setText(bundle.getString("IBAN:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbIban, gridBagConstraints);

        tfIban.setEditable(false);
        tfIban.setBackground(new java.awt.Color(31, 31, 31));
        tfIban.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfIban.setForeground(new java.awt.Color(240, 240, 240));
        tfIban.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfIban, gridBagConstraints);

        lbType.setBackground(new java.awt.Color(18, 18, 18));
        lbType.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbType.setForeground(new java.awt.Color(240, 240, 240));
        lbType.setText(bundle.getString("TYP ÚČTU:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbType, gridBagConstraints);

        tfType.setEditable(false);
        tfType.setBackground(new java.awt.Color(31, 31, 31));
        tfType.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfType.setForeground(new java.awt.Color(240, 240, 240));
        tfType.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfType, gridBagConstraints);

        lbBalance.setBackground(new java.awt.Color(18, 18, 18));
        lbBalance.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbBalance.setForeground(new java.awt.Color(240, 240, 240));
        lbBalance.setText(bundle.getString("ZOSTATOK:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbBalance, gridBagConstraints);

        tfBalance.setEditable(false);
        tfBalance.setBackground(new java.awt.Color(31, 31, 31));
        tfBalance.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfBalance.setForeground(new java.awt.Color(240, 240, 240));
        tfBalance.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfBalance, gridBagConstraints);

        lbOwner.setBackground(new java.awt.Color(18, 18, 18));
        lbOwner.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbOwner.setForeground(new java.awt.Color(240, 240, 240));
        lbOwner.setText(bundle.getString("INFORMÁCIE O VLASTNÍKOVI")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbOwner, gridBagConstraints);

        lbName.setBackground(new java.awt.Color(18, 18, 18));
        lbName.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbName.setForeground(new java.awt.Color(240, 240, 240));
        lbName.setText(bundle.getString("KRSTNÉ MENO:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbName, gridBagConstraints);

        tfName.setEditable(false);
        tfName.setBackground(new java.awt.Color(31, 31, 31));
        tfName.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfName.setForeground(new java.awt.Color(240, 240, 240));
        tfName.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfName, gridBagConstraints);

        lbSurname.setBackground(new java.awt.Color(18, 18, 18));
        lbSurname.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbSurname.setForeground(new java.awt.Color(240, 240, 240));
        lbSurname.setText(bundle.getString("PRIEZVISKO:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbSurname, gridBagConstraints);

        tfSurname.setEditable(false);
        tfSurname.setBackground(new java.awt.Color(31, 31, 31));
        tfSurname.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfSurname.setForeground(new java.awt.Color(240, 240, 240));
        tfSurname.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfSurname, gridBagConstraints);

        lbBirthDate.setBackground(new java.awt.Color(18, 18, 18));
        lbBirthDate.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbBirthDate.setForeground(new java.awt.Color(240, 240, 240));
        lbBirthDate.setText(bundle.getString("DÁTUM NARODENIA:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbBirthDate, gridBagConstraints);

        tfBirthDate.setEditable(false);
        tfBirthDate.setBackground(new java.awt.Color(31, 31, 31));
        tfBirthDate.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfBirthDate.setForeground(new java.awt.Color(240, 240, 240));
        tfBirthDate.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfBirthDate, gridBagConstraints);

        lbAddress.setBackground(new java.awt.Color(18, 18, 18));
        lbAddress.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbAddress.setForeground(new java.awt.Color(240, 240, 240));
        lbAddress.setText(bundle.getString("ADRESA BYDLISKA:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbAddress, gridBagConstraints);

        tfAddress.setEditable(false);
        tfAddress.setBackground(new java.awt.Color(31, 31, 31));
        tfAddress.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfAddress.setForeground(new java.awt.Color(240, 240, 240));
        tfAddress.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfAddress, gridBagConstraints);

        lbEmail.setBackground(new java.awt.Color(18, 18, 18));
        lbEmail.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbEmail.setForeground(new java.awt.Color(240, 240, 240));
        lbEmail.setText(bundle.getString("EMAILOVÁ ADRESA:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbEmail, gridBagConstraints);

        tfEmail.setEditable(false);
        tfEmail.setBackground(new java.awt.Color(31, 31, 31));
        tfEmail.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfEmail.setForeground(new java.awt.Color(240, 240, 240));
        tfEmail.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfEmail, gridBagConstraints);

        btClose.setBackground(new java.awt.Color(31, 31, 31));
        btClose.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btClose.setForeground(new java.awt.Color(240, 240, 240));
        btClose.setText(bundle.getString("ZATVORIŤ")); // NOI18N
        btClose.setPreferredSize(new java.awt.Dimension(100, 35));
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(btClose, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        accountDetailPanel.add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        accountDetailPanel.add(filler2, gridBagConstraints);

        lbUsername.setBackground(new java.awt.Color(18, 18, 18));
        lbUsername.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbUsername.setForeground(new java.awt.Color(240, 240, 240));
        lbUsername.setText(bundle.getString("PRIHLASOVACIE MENO:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbUsername, gridBagConstraints);

        tfUsername.setEditable(false);
        tfUsername.setBackground(new java.awt.Color(31, 31, 31));
        tfUsername.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfUsername.setForeground(new java.awt.Color(240, 240, 240));
        tfUsername.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfUsername, gridBagConstraints);

        lbPassword.setBackground(new java.awt.Color(18, 18, 18));
        lbPassword.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbPassword.setForeground(new java.awt.Color(240, 240, 240));
        lbPassword.setText(bundle.getString("HESLO:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(lbPassword, gridBagConstraints);

        tfPassword.setEditable(false);
        tfPassword.setBackground(new java.awt.Color(31, 31, 31));
        tfPassword.setFont(new java.awt.Font("Dialog", 0, 15)); // NOI18N
        tfPassword.setForeground(new java.awt.Color(240, 240, 240));
        tfPassword.setPreferredSize(new java.awt.Dimension(220, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(tfPassword, gridBagConstraints);

        chbSeePassword.setBackground(new java.awt.Color(18, 18, 18));
        chbSeePassword.setForeground(new java.awt.Color(240, 240, 240));
        chbSeePassword.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chbSeePasswordStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        accountDetailPanel.add(chbSeePassword, gridBagConstraints);

        getContentPane().add(accountDetailPanel, new java.awt.GridBagConstraints());

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btCloseActionPerformed

    private void chbSeePasswordStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chbSeePasswordStateChanged
        if (chbSeePassword.isSelected()) {
            tfPassword.setText(password);
        } else {
            tfPassword.setText(password.replaceAll(".", "*"));
        }
    }//GEN-LAST:event_chbSeePasswordStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel accountDetailPanel;
    private javax.swing.JButton btClose;
    private javax.swing.JCheckBox chbSeePassword;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JLabel lbAddress;
    private javax.swing.JLabel lbBalance;
    private javax.swing.JLabel lbBirthDate;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbIban;
    private javax.swing.JLabel lbName;
    private javax.swing.JLabel lbOwner;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbSurname;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbType;
    private javax.swing.JLabel lbUsername;
    private javax.swing.JTextField tfAddress;
    private javax.swing.JTextField tfBalance;
    private javax.swing.JTextField tfBirthDate;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfIban;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPassword;
    private javax.swing.JTextField tfSurname;
    private javax.swing.JTextField tfType;
    private javax.swing.JTextField tfUsername;
    // End of variables declaration//GEN-END:variables
}
