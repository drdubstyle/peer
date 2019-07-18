package com.company.model;


import com.company.controller.DBcontroller;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Docs extends DBcontroller implements Serializable{
    private int id_doc;
    private int id_author;

    public int getId_doc() {
        return id_doc;
    }

    public void setId_doc(int id_doc) {
        this.id_doc = id_doc;
    }

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }



    public Map read(){
        String query = "select * from docs";
        Map<Integer,Docs> docsMap = new HashMap<Integer,Docs>();
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Docs docs_toMap = new Docs();
                docs_toMap.id_doc = rs.getInt(1);
                docs_toMap.id_author = rs.getInt(2);
                docsMap.put(docs_toMap.id_doc, docs_toMap);

            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return docsMap;
    }
    public void write(){
        String query = "insert into `docs` (`ip`,`port`,`name`,`local`,`last_date_synhronyze`) values('127.0.0.1','3306','peer457',false,'2018-01-08');";

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