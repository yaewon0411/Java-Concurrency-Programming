package io.concurrency.chapter02.exam01;

public class AnonymousRunnableClassExample {
    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": 스레드 실행 중..");
            }
        });

        thread.start();

        Thread ywThread = new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName() + " : yw스레드 실행중..");
            }
        });
        ywThread.start();

    }
}
