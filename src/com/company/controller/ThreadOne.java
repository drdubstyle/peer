package com.company.controller;

import com.company.model.Author;
import com.company.model.Docs;
import com.company.model.Document;
import com.company.model.Peer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ThreadOne  implements Runnable  {

    public static int delayTimeSynh = 5000;//5 секунд
    public static int countPeerSynhr = 0;

    public void run() {
        while (true) {
            try {
                Thread.sleep(delayTimeSynh);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DBcontroller DC = new DBcontroller();
            Map<Integer, Peer> peerMap = new Peer().read();
            try {
                synhronize(peerMap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static void synhronize(Map peerMap) throws IOException {
        System.out.println("Начало синхронизации");
        Set<Map.Entry<Integer, Peer>> set = peerMap.entrySet();
        for (Map.Entry<Integer, Peer> me : set) {
            Peer p2 = me.getValue();
            if(!p2.isLocal()) {
                connection(p2.getIp(), p2.getPort(), p2.getName());
            }
        }
        System.out.println("Синхронизация завершена. Синхронизированно "+countPeerSynhr+" узлов. Следующая синхронизация через " + delayTimeSynh/1000 +" секунд!");
        countPeerSynhr = 0;

    }
    static void connection(String ip, String port,String name) throws IOException {

        try {
            Socket socket = new Socket(ip, Integer.parseInt(port)); // создаем сокет используя IP-адрес и порт сервера.
            if(socket.isConnected()){

                countPeerSynhr++;

                System.out.println("Узел " + name + " is connected.\n");
                Map <Integer, Author> authorMap = new Author().read();

                Set<Map.Entry<Integer, Author>> set = authorMap.entrySet();
                for (Map.Entry<Integer, Author> me : set) {
                    Author p2 = me.getValue();
                }

                Map <Integer, Docs> docsMap = new Docs().read();
                Set<Map.Entry<Integer, Docs>> set1 = docsMap.entrySet();
                for (Map.Entry<Integer, Docs> me : set1) {
                    Docs p2 = me.getValue();
                }

                Map <Integer, Document> documentMap = new Document().read();
                Set<Map.Entry<Integer, Document>> set2 = documentMap.entrySet();
                for (Map.Entry<Integer, Document> me : set2) {
                    Document p2 = me.getValue();
                }

                Map<Integer,Document> docForSynh1 = new HashMap<Integer, Document>();
                try {
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    docForSynh1 = (Map) in.readObject();
                    in.close();


                }catch (Exception e){e.printStackTrace();}

                Set<Map.Entry<Integer, Document>> set3 = docForSynh1.entrySet();
                for (Map.Entry<Integer, Document> me : set3) {

                    Document p2 = new Document();
                    p2.update(me.getValue());
                }
                System.out.println("Синхронизация с "+name+ " завершена! Синхронизированно "+ docForSynh1.size() +" записей");
                //втсавка данных в таблицу
            }
            else{
                System.out.println(name + "не подключен!");
            }
        }catch (IOException e){
            System.out.println("Узел с адресом " + ip + "/" + port + " " + name + " не найден");
        }
        ;
    }
}