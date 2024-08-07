package io.concurrency.chapter01.exam02;

public class ContextSwitchExample {
    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1: " + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 2: " + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 3: " + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //각 스레드가 순서 없이 번갈아 가며 값을 출력함
        //CPU 스케줄러가 thread3을 가져왔다가 처리해서 thread 1을 가져오고 처리하고 thread 2를 가져와 처리하고..
        //이런 식으로 문맥 교환이 일어나는 것임

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
