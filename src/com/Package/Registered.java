package com.Package;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Registered {
    public JPanel panel1;
    private JTextField textField1;
    private JButton searchButton;
    private JTable table1;
    private JButton deleteButton;

    DefaultTableModel model = new DefaultTableModel();
    FormPage form = new FormPage();

    public Registered() {
        createRegisterTable();
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lastName = textField1.getText();
                filter(lastName);
                refreshTable();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFromDatabase();
            }
        });
    }

    private void createRegisterTable(){
        Object[] columnName = new Object[7];

        columnName [0] = "Serial number";
        columnName [1] = "First name";
        columnName [2] = "Last name";
        columnName [3] = "Mobile number";
        columnName [4] = "Email address";
        columnName [5] = "User name";
        columnName [6] = "Date registered";

        model.setColumnIdentifiers(columnName);

        Object [] rowData = new Object[7];

        Connectivity control = new Connectivity();
        ArrayList<Variables> list = control.getMembers();

        for(int i = 0; i <list.size(); i++){
            rowData[0] = list.get(i).getSerialNumber();
            rowData[1] = list.get(i).getFirstName();
            rowData[2] = list.get(i).getLastName();
            rowData[3] = list.get(i).getMobileNumber();
            rowData[4] = list.get(i).getEmailAddress();
            rowData[5] = list.get(i).getUserName();
            rowData[6] = list.get(i).getDate();

            model.addRow(rowData);
        }
        table1.setModel(model);
    }
    private void filter(String lastName){
        TableRowSorter<DefaultTableModel> searchItem = new TableRowSorter<>(model);
        table1.setRowSorter(searchItem);
        searchItem.setRowFilter(RowFilter.regexFilter(lastName));
    }

    public void fillUpdateDialog(){
        int i = table1.getSelectedRow();

        if(i >= 0){
            form.serialNumberField.setText(table1.getValueAt(i,0).toString());
            form.firstField.setText(table1.getValueAt(i,1).toString());
            form.lastField.setText(table1.getValueAt(i,2).toString());
            form.phoneField.setText(table1.getValueAt(i,4).toString());
            form.emailField.setText(table1.getValueAt(i,5).toString());
            form.userField.setText(table1.getValueAt(i,6).toString());
            form.passField.setText("**********");
        }
    }

    public  void deleteFromDatabase(){
        int j = table1.getSelectedRow();
        if(j >= 0){
            int serialNumber = Integer.parseInt(table1.getValueAt(j,0).toString());

            try{
                Connection link = new Connectivity().getConnection();
//
                String sQL = "DELETE FROM register WHERE serialNumber = '" + serialNumber + "'";

                PreparedStatement statement = link.prepareStatement(sQL);

                int update = statement.executeUpdate();

                if(update >= 0 ){
                    JOptionPane.showMessageDialog(null,"Register updates successfully");
                }
                else {
                    JOptionPane.showMessageDialog(null,"Update failed");
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void  refreshTable(){
        model.fireTableDataChanged();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Registered");
        frame.setContentPane(new Registered().panel1);
        frame.setSize(500,500);
        frame.pack();
        frame.setVisible(true);
    }
}
