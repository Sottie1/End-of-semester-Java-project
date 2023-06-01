package com.Package;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Connectivity {
    public static Connection connect;



    public static Connection getConnection(){
        try{
            String url = "jdbc:mysql://localhost:3306/javaApplication";
            String user = "root";
            String password = "";

            connect = DriverManager.getConnection(url,user,password);
            JOptionPane.showMessageDialog(null,"connected successfully");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return connect;
    }

    public static void createNewTable(){
        try{
            DatabaseMetaData dm = connect.getMetaData();
            ResultSet set = dm.getTables(null,null,"register",null);

            if(set.next()){
                new FormPage();
            }
            else {
                String sQL = "CREATE TABLE REGISTER("
                        + "serialNumber int (50)  NULL AUTO_INCREMENT,"
                        + "firstName varchar (20),"
                        + "lastName varchar (20),"
                        + "mobileNumber varchar (15),"
                        + "emailAddress varchar (30),"
                        + "userName varchar (20),"
                        + "password varchar (8),"
                        + "dateRegistered varchar (20))";

                PreparedStatement statement = connect.prepareStatement(sQL);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null,"Result table created successfully");

                getConnection();
                new Interphase();
                new FormPage();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Variables> getMembers(){

        ArrayList<Variables> items = new ArrayList<>();

        Variables fromDatabase;

        try{
            String sqL = "SELECT * FROM register";

            PreparedStatement statement = getConnection().prepareStatement(sqL);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                fromDatabase = new Variables(
                        resultSet.getInt("serialNumber"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("mobileNumber"),
                        resultSet.getString("emailAddress"),
                        resultSet.getString("userName"),
                        resultSet.getString("dateRegistered")
                );

                items.add(fromDatabase);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return items;
    }

    public static void main(String[] args) {
        getConnection();
        createNewTable();
    }
}
