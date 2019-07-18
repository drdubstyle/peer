package com.company.model;

import com.company.controller.DBcontroller;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Document extends DBcontroller implements Serializable {

    private int id_document;
    private String peer_mod;
    private String date_mod;
    private String name;
    private String key_words;
    private String lang;
    private String descript;

    public int getId_document() {
        return id_document;
    }

    public void setId_document(int id_document) {
        this.id_document = id_document;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public Map read(){
        String query = "select * from document";
        Map<Integer,Document> documentMap = new HashMap<Integer,Document>();
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Document document_toMap = new Document();
                document_toMap.id_document = rs.getInt(1);
                document_toMap.peer_mod = rs.getString(2);
                document_toMap.date_mod = rs.getString(3);
                document_toMap.name = rs.getString(4);
                document_toMap.key_words = rs.getString(5);
                document_toMap.lang = rs.getString(6);
                document_toMap.descript = rs.getString(7);
                documentMap.put(document_toMap.id_document, document_toMap);

            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return documentMap;
    }
    public void write(){
        String query = "insert into `document` (`ip`,`port`,`name`,`local`,`last_date_synhronyze`) values('127.0.0.1','3306','peer457',false,'2018-01-08');";

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
    public void update(Document value){
        String query = "update `document`" +
                " set `peer_mod` = '"+ value.peer_mod+ "', `date_mod`= '"+value.date_mod+"'," +
                " `name_document`= '"+value.name+"', `key_words`= '"+value.key_words+"', " +
                "`lang` = '"+value.lang+"', `discription`= '"+value.descript+"'" +
                " where `id_doc` = "+value.id_document+";";
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            int rs = stmt.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }
}