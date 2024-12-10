package com.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Unit test for simple App.
 */
public class AppTest{
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Thread thread = new Thread(() -> {
            System.out.println("1aaa1");
        });


        Thread thread1 = new Thread(() -> {
            System.out.println("12222");
        });

        try {
            thread.start();


            thread1.start();


            thread.join();

            System.out.println("ssssss");


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}

