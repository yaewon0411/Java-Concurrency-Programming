package io.concurrency.chapter02.exam02;

public class ThreadStartRunExample {
    public static void main(String[] args) {


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " : 스레드 실행중..");
            }
        });


        thread1.start();

        MyRunnable myRunnable = new MyRunnable();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " :스레드 실행중..");
            }
        });

        thread2.start();

        myRunnable.run();

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " : 스레드 실행중..");
            }
        });

        thread3.start();
        System.out.println(Thread.currentThread().getName() + ": 메인 스레드 종료");
    }


    static class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": 스레드 실행 중...");
        }
    }
}
