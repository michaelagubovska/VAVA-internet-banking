/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import model.Customer;

/**
 * GUI class managing the account report generating menu
 * 
 */
public class ReportPopup extends javax.swing.JFrame {
    
    private java.util.ResourceBundle bundle;
    private final Customer person;
    private final DefaultListModel transactions;
    private static Logger logger;

    /**
     * Creates new form ReportPopup
     * @param person Customer instance that wants to generate an account report
     * @param transactions list of transactions from which the report will be generated
     * @param logger logger that writes logs to a file
     * @param bundle I18N ResourceBundle currently in use
     */
    public ReportPopup(Customer person, DefaultListModel transactions, Logger logger, java.util.ResourceBundle bundle) {
        this.bundle = bundle;
        initComponents();
        internationalize();
        this.person = person;
        this.transactions = transactions;
        ReportPopup.logger = logger;
    }
    
    private void internationalize() {
        setTitle(bundle.getString("RAYBANK - VÝPIS Z ÚČTU"));
        lbTitle.setText(bundle.getString("ULOŽENIE VÝPISU Z ÚČTU"));
        lbDateFrom.setText(bundle.getString("OD:"));
        lbDateTo.setText(bundle.getString("DO:"));
        btancel.setText(bundle.getString("ZRUŠIŤ"));
        btSave.setText(bundle.getString("ULOŽIŤ"));
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
        lbTitle = new javax.swing.JLabel();
        lbDateFrom = new javax.swing.JLabel();
        lbDateTo = new javax.swing.JLabel();
        spinnerDateFrom = new javax.swing.JSpinner();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 60), new java.awt.Dimension(32767, 50));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 50), new java.awt.Dimension(0, 60), new java.awt.Dimension(32767, 50));
        spinnerDateTo = new javax.swing.JSpinner();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        toolPanel = new javax.swing.JPanel();
        btancel = new javax.swing.JButton();
        btSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("view/Bundle_SVK"); // NOI18N
        setTitle(bundle.getString("RAYBANK - VÝPIS Z ÚČTU")); // NOI18N
        getContentPane().setLayout(new java.awt.GridBagLayout());

        containerPanel.setBackground(new java.awt.Color(18, 18, 18));
        containerPanel.setForeground(new java.awt.Color(240, 240, 240));
        containerPanel.setPreferredSize(new java.awt.Dimension(450, 250));
        containerPanel.setLayout(new java.awt.GridBagLayout());

        lbTitle.setBackground(new java.awt.Color(18, 18, 18));
        lbTitle.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(240, 240, 240));
        lbTitle.setText(bundle.getString("ULOŽENIE VÝPISU Z ÚČTU")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        containerPanel.add(lbTitle, gridBagConstraints);

        lbDateFrom.setBackground(new java.awt.Color(18, 18, 18));
        lbDateFrom.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbDateFrom.setForeground(new java.awt.Color(240, 240, 240));
        lbDateFrom.setText(bundle.getString("OD:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        containerPanel.add(lbDateFrom, gridBagConstraints);

        lbDateTo.setBackground(new java.awt.Color(18, 18, 18));
        lbDateTo.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        lbDateTo.setForeground(new java.awt.Color(240, 240, 240));
        lbDateTo.setText(bundle.getString("DO:")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        containerPanel.add(lbDateTo, gridBagConstraints);

        spinnerDateFrom.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        spinnerDateFrom.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        spinnerDateFrom.setEditor(new javax.swing.JSpinner.DateEditor(spinnerDateFrom, "dd.MM.yyyy HH:mm:ss"));
        spinnerDateFrom.setPreferredSize(new java.awt.Dimension(190, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        containerPanel.add(spinnerDateFrom, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        containerPanel.add(filler1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        containerPanel.add(filler2, gridBagConstraints);

        spinnerDateTo.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        spinnerDateTo.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        spinnerDateTo.setEditor(new javax.swing.JSpinner.DateEditor(spinnerDateTo, "dd.MM.yyyy HH:mm:ss"));
        spinnerDateTo.setPreferredSize(new java.awt.Dimension(190, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        containerPanel.add(spinnerDateTo, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        containerPanel.add(filler3, gridBagConstraints);

        getContentPane().add(containerPanel, new java.awt.GridBagConstraints());

        toolPanel.setBackground(new java.awt.Color(18, 18, 18));
        toolPanel.setForeground(new java.awt.Color(240, 240, 240));
        toolPanel.setPreferredSize(new java.awt.Dimension(450, 50));
        toolPanel.setLayout(new java.awt.GridLayout(1, 0));

        btancel.setBackground(new java.awt.Color(31, 31, 31));
        btancel.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btancel.setForeground(new java.awt.Color(240, 240, 240));
        btancel.setText(bundle.getString("ZRUŠIŤ")); // NOI18N
        btancel.setPreferredSize(new java.awt.Dimension(100, 35));
        btancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btancelActionPerformed(evt);
            }
        });
        toolPanel.add(btancel);

        btSave.setBackground(new java.awt.Color(31, 31, 31));
        btSave.setFont(new java.awt.Font("Dialog", 0, 16)); // NOI18N
        btSave.setForeground(new java.awt.Color(240, 240, 240));
        btSave.setText(bundle.getString("ULOŽIŤ")); // NOI18N
        btSave.setPreferredSize(new java.awt.Dimension(100, 35));
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });
        toolPanel.add(btSave);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        getContentPane().add(toolPanel, gridBagConstraints);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btancelActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        Date from = (Date) spinnerDateFrom.getValue();
        Date to = (Date) spinnerDateTo.getValue();
        if (from.after(to)) {
            JOptionPane.showMessageDialog(containerPanel, bundle.getString("BOL ZADANÝ NESPRÁVNY ROZSAH DÁTUMOV."), bundle.getString("CHYBA PRI TVORBE VÝPISU"), JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, bundle.getString("INVALID DATE RANGE"));
        } else {
            if (!person.getAccount().generateAccountReport(transactions, from, to, bundle)) {
                JOptionPane.showMessageDialog(containerPanel, bundle.getString("PRI UKLADANÍ VÝPISU DOŠLO KU CHYBE. SKÚSTE HO ULOŽIŤ EŠTE RAZ, PROSÍM."), bundle.getString("CHYBA PRI TVORBE VÝPISU"), JOptionPane.ERROR_MESSAGE);
                logger.log(Level.SEVERE, String.format(bundle.getString("ERROR WHILE SAVING TRANSACTION REPORT. (USER: %S)"), person.getUsername()));
            } else {
                JOptionPane.showMessageDialog(containerPanel, bundle.getString("VÝPIS BOL ÚSPEŠNE ULOŽENÝ."), bundle.getString("VYTVORENIE VÝPISU"), JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        }
    }//GEN-LAST:event_btSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btSave;
    private javax.swing.JButton btancel;
    private javax.swing.JPanel containerPanel;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JLabel lbDateFrom;
    private javax.swing.JLabel lbDateTo;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JSpinner spinnerDateFrom;
    private javax.swing.JSpinner spinnerDateTo;
    private javax.swing.JPanel toolPanel;
    // End of variables declaration//GEN-END:variables
}
