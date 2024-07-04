package io.concurrency.chapter02.exam01;

public class ExtendThreadExample {
    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();

        YwThread thread = new YwThread();
        thread.start();
    }
 }

 class YwThread extends Thread{
     @Override
     public void run() {
         System.out.println(Thread.currentThread().getName() + " : 스레드 실행 중..");
     }
 }

 class MyThread extends Thread{
     @Override
     public void run() {
         System.out.println(Thread.currentThread().getName() + " :스레드 실행 중.. ");
     }
 }