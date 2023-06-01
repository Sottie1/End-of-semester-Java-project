package com.Package;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.WindowConstants;



public class Interphase {
    private JPanel panel1;
    private JTextField userName;
    private JPasswordField password;
    private JButton createButton;
    private JButton loginButton;

    private JMenuBar content(){

        JMenuBar subjects = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_PAGE_UP);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_HOME);

        subjects.add(fileMenu);
        subjects.add(helpMenu);


        JMenuItem openMenu = new JMenuItem("Open");
        openMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10,ActionEvent.CTRL_MASK));

        JMenuItem exitMenu = new JMenuItem("Exit");
        exitMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,ActionEvent.ALT_MASK));

        JMenuItem refreshMenu = new JMenuItem("Refresh");
        refreshMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,ActionEvent.CTRL_MASK));

        JMenuItem  aboutMenu = new JMenuItem("About");
        aboutMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,ActionEvent.CTRL_MASK));

        fileMenu.add(openMenu);
        fileMenu.add(refreshMenu);
        fileMenu.add(exitMenu);
        helpMenu.add(aboutMenu);

        openMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Registered");
                frame.setContentPane(new Registered().panel1);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setBounds(200,400,650,300);
                frame.setLocationRelativeTo(null);
                frame.setSize(700,700);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });



        aboutMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JWindow window = new JWindow();
                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setText("\n  " +
                        "\t COPYRIGHT © 2023 \n " +
                        "\t ALL RIGHTS RESERVED GROUP 11 \n \n" +
                        "\t ADJATEY SOLOMON - 01212135D\n \t SOTTIE BOAZ- 01211849D\n " +
                        "\t ABIGAIL AGBESI - 01210177D\n \t NARTEY RANSFORD - 01211976D\n" +
                        " \t CHARLES KWEKU ABBAN - 01210046D\n \t DUSI PIOUS - 01212617D\n\n\n\n" +
                        "\t COURSE TITLE:\n \n \t CPS JAVA II\n \n \t 2023 ACADEMIC YEAR");

                JScrollPane scrollPane = new JScrollPane(textArea);
                window.add(scrollPane);

                JButton closeButton = new JButton("Close");
                closeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        window.dispose();
                    }
                });
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(closeButton);
                window.add(buttonPanel, BorderLayout.SOUTH);

                window.setSize(400, 300);
                window.setLocationRelativeTo(null);
                window.setVisible(true);
            }
        });




        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        return subjects;
    }

    public Interphase() {
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormPage form = new FormPage();
                form.setTitle("Create Account");
                form.setLocationRelativeTo(null);
                form.action = "create";
                form.setBounds(50,50,600,600);
                form.setVisible(true);
            }
        });

        Connectivity connectivity = new Connectivity();
        Connection take = connectivity.getConnection();
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String sQL = "SELECT * FROM register where userName = ? and password = ? ";
                    PreparedStatement statement = take.prepareStatement(sQL);

                    statement.setString(1,userName.getText());
                    statement.setString(2,password.getText());

                    ResultSet resultSet = statement.executeQuery();

                    if(resultSet.next()){
                        JOptionPane.showMessageDialog(null,"Login successful");

                        userName.setText("");
                        password.setText("");
                        JFrame frame = new JFrame("Registered");
                        frame.setContentPane(new Registered().panel1);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setBounds(200,50,650,600);
                        frame.setVisible(true);

                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Invalid username or password");
                    }
                }
                catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Interphase");
        frame.setContentPane(new Interphase().panel1);
        frame.setJMenuBar(new Interphase().content());
        frame.setSize(900,900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
