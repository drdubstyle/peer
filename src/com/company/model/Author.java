package com.company.model;


import com.company.controller.DBcontroller;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Author extends DBcontroller implements Serializable{
    private int id;
    private String peer_mod;
    private String date_mod;
    private String name_auther;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeer_mod() {
        return peer_mod;
    }

    public void setPeer_mod(String peer_mod) {
        this.peer_mod = peer_mod;
    }

    public String getDate_mod() {
        return date_mod;
    }

    public void setDate_mod(String date_mod) {
        this.date_mod = date_mod;
    }

    public String getName_auther() {
        return name_auther;
    }

    public void setName_auther(String name_auther) {
        this.name_auther = name_auther;
    }

    public Map read(){
        String query = "select * from author";
        Map<Integer,Author> authorToMap = new HashMap<Integer,Author>();
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Author author_toMap = new Author();
                author_toMap.id = rs.getInt(1);
                author_toMap.peer_mod = rs.getString(2);
                author_toMap.date_mod = rs.getString(3);
                author_toMap.name_auther = rs.getString(4);
                authorToMap.put(author_toMap.id, author_toMap);

            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return authorToMap;
    }
    public void write(){
        String query = "insert into `Author` (`ip`,`port`,`name`,`local`,`last_date_synhronyze`) values('127.0.0.1','3306','peer457',false,'2018-01-08');";

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            //rs = stmt.executeQuery(query);

            int z = stmt.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        }

    }
    public void update(){

    }
}