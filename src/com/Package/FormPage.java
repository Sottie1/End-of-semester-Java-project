package com.Package;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormPage extends JDialog{
    public JPanel panel1;
    public JTextField lastField;
    public JTextField userField;
    public JPasswordField passField;
    public JFormattedTextField phoneField;
    public JTextField emailField;
    private JButton saveButton;
    private JButton cancelButton;
    public JTextField firstField;

    public String action;
    public JTextField serialNumberField;

    Registered forRefresh;
    Connectivity link = new Connectivity();
    Connection myLink = link.getConnection();

    public FormPage() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        panel1.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        serialNumberField = new JTextField();
        setContentPane(panel1);
        setModal(true);
        getRootPane().setDefaultButton(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (action.equals("create")) {
                    insertData();
                } else {
                    updateDatabase();
                }
                onSave();
            }
        });
    }


    private void onSave(){
        dispose();
    }

    private void onCancel(){
        dispose();
    }

    private void insertData(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format.format(today);

        try{
            DatabaseMetaData dMD = myLink.getMetaData();
            ResultSet resultSet = dMD.getTables(null,null,"register",null);

            String sQL = "INSERT INTO register VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement statement = myLink.prepareStatement(sQL);

            int dataInserted = 0;

            if(resultSet.next()){
                statement.setInt(1,0);
                statement.setString(2,firstField.getText());
                statement.setString(3,lastField.getText());
                statement.setString(4,phoneField.getText());
                statement.setString(5,emailField.getText());
                statement.setString(6,userField.getText());
                statement.setString(7,passField.getText());
                statement.setString(8,dateString);
                dataInserted = statement.executeUpdate();
            }
            if(dataInserted >=0){
                JOptionPane.showMessageDialog(null,"Data inserted successfully");
            }
            else {
                JOptionPane.showMessageDialog(null,"Error! Data insertion failed");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateDatabase(){
        String sQl = "UPDATE register SET firstName = '"+firstField.getText() +"' , "
                +"lastName = '"+ lastField.getText() + "' ,"
                +"mobileNumber = '"+ phoneField.getText() + "' ,"
                +"emailAddress = '"+ emailField.getText() + "' ,"
                +"userName = '"+ userField.getText() + "' ,"
                +"password = '"+ passField.getText() + "' ,"
                +"WHERE serialNumber = '" + Integer.parseInt(serialNumberField.getText());

        try{
            PreparedStatement statement = myLink.prepareStatement(sQl);

            int update = statement.executeUpdate();

            if(update > 0){
                JOptionPane.showMessageDialog(null,"Register updates successfully");
                System.out.println(serialNumberField);
            }
            else {
                JOptionPane.showMessageDialog(null,"Updates failed");
                System.out.println(serialNumberField);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        FormPage at = new FormPage();
        at.setTitle("FormPage");
        at.setSize(700,700);
        at.action = "create";
        at.setLocationRelativeTo(null);
        at.setVisible(true);

    }

}
