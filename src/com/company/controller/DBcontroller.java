package com.company.controller;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBcontroller {
    // JDBC URL, username and password of MySQL server
    public static final String url = "jdbc:mysql://localhost:3306/db_lab3?useSSL=false";
    public static final String user = "root";
    public static final String password = "root";

    // JDBC variables for opening and managing connection
    public static Connection con;
    public static Statement stmt;
    public static ResultSet rs;
}