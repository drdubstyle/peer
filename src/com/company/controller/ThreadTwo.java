package com.company.controller;

import com.company.model.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class ThreadTwo implements Runnable {
    @Override
    public void run() {

        Peer localPeer = new Peer();
        DBcontroller DC = new DBcontroller();

        Map <Integer,Peer> peerMap = new Peer().read();
        Set<Map.Entry<Integer, Peer>> set1 = peerMap.entrySet();

        java.util.Date dt1 = null;
        java.util.Date dt = new Date();
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(dt);
        int port = 0;
        for (Map.Entry<Integer, Peer> me : set1) {
            localPeer = me.getValue();
            if(localPeer.isLocal()){
                System.out.println("Это узел "+ localPeer.getName());
                port = Integer.parseInt(localPeer.getPort());
                SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    dt1=  simdf.parse(localPeer.getDate_last_synhronyze());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }


        boolean isIn_tablePeer = false;
        ServerSocket serv_soc = null;
        try {
            serv_soc = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Ожидание подключения...");
        Socket socket = null;
        try {
            socket = serv_soc.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Peer с адресом " + socket.getInetAddress() + " подключен!");
        Set<Map.Entry<Integer, Peer>> set = peerMap.entrySet();
        for (Map.Entry<Integer, Peer> me : set) {
            Peer p1 = me.getValue();
            if(socket.getInetAddress().toString().substring(1).equals(p1.getIp())){
                isIn_tablePeer = true;
                //если адрес есть в таблицы пиров, то обрабатывается подключеие, если нет, то проверяется а соответствие следующая запись из таблицы пиров
                if (!p1.isLocal()) {
                    p1.update(String.valueOf(currentTime),p1.getId());
                }
            }

        }
        if(!isIn_tablePeer){
            System.out.println("Peer с адресом " + socket.getInetAddress() + " не найден в таблице данных! Отключение...");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map <Integer, Document> documentMap = new Document().read();
        Set<Map.Entry<Integer, Document>> setD = documentMap.entrySet();

        Map<Integer,Document> docForSynh = new HashMap<Integer, Document>();

        Map<Integer, Docs> docs = new Docs().read();
        Set<Map.Entry<Integer, Docs>> setDocs = docs.entrySet();

        for (Map.Entry<Integer, Document> me : setD) {
            Document d = new Document();
            d = me.getValue();

            SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt2 = null;
            try {
                dt2 = simdf.parse(d.getDate_mod());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(dt2.after(dt1)){
                docForSynh.put(d.getId_document(), d);
            }

        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(docForSynh);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
