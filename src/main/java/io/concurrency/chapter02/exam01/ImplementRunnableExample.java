package io.concurrency.chapter02.exam01;

public class ImplementRunnableExample {
    public static void main(String[] args) {

        MyRunnable task = new MyRunnable();
        Thread thread = new Thread(task);
        thread.start();

        YwRunnable yw = new YwRunnable();
        Thread thread2 = new Thread(yw);
        thread2.start();
    }
}

class YwRunnable implements Runnable{
    public void run(){
        System.out.println(Thread.currentThread().getName() + " : 실행 중..");
    }
}
class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": 스레드 실행 중");
    }
}
