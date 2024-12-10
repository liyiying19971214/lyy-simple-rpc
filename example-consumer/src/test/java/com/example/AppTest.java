package com.example;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Unit test for simple App.
 */
public class AppTest {


    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
         CountDownLatch countDownLatch = new CountDownLatch(1);


        Thread  a= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 线程执行一个阻塞操作（睡眠）
                    System.out.println("线程开始执行...");
                    Thread.sleep(5000);  // 线程睡眠 5 秒
                    System.out.println("线程执行完成");
                } catch (InterruptedException e) {
                    // 线程被中断时会抛出 InterruptedException
                    System.out.println("线程被中断，抛出异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        a.start();

        try {
            Thread.sleep(1000);  // 主线程睡眠 1 秒
            System.out.println("主线程调用 interrupt()...");
            a.interrupt();  // 中断线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
