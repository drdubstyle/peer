package com.company.model;

import com.company.controller.DBcontroller;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class Peer  extends DBcontroller implements Serializable{
    private int id;
    private String ip;
    private String port;
    private String name;
    private boolean  local;
    private String date_last_synhronyze;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getDate_last_synhronyze() {
        return date_last_synhronyze;
    }

    public void setDate_last_synhronyze(String date_last_synhronyze) {
        this.date_last_synhronyze = date_last_synhronyze;
    }

    public Map read(){
        String query = "select * from peer";
        Map<Integer,Peer> peerMap = new HashMap<Integer,Peer>();
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Peer peer_toMap = new Peer();
                peer_toMap.id = rs.getInt(1);
                peer_toMap.ip = rs.getString(2);
                peer_toMap.port = rs.getString(3);
                peer_toMap.name = rs.getString(4);
                peer_toMap.local = rs.getBoolean(5);
                peer_toMap.date_last_synhronyze = rs.getString(6);
                peerMap.put(peer_toMap.id, peer_toMap);
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }

        return peerMap;
    }
    public void update(String dt, int id){
        String query = " UPDATE `peer` SET `last_date_synhronyze` = ' " +dt + " 'WHERE  `id_peer` = "+ id +";";
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            //rs = stmt.executeQuery(query);/
            int z = stmt.executeUpdate(query);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }
}