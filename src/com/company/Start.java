package com.company;

import com.company.controller.ThreadOne;
import com.company.controller.ThreadTwo;

public class Start{
    public static void main(String[] args){
        Runnable t1 = new ThreadOne();
        Runnable t2 = new ThreadTwo();
        Thread th1 = new Thread(t1);
        Thread th2 = new Thread(t2);
        th1.start();
        th2.start();
    }
}