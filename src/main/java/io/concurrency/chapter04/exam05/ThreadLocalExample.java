package io.concurrency.chapter04.exam05;

public class ThreadLocalExample {
    // ThreadLocal 변수 생성. 초기값은 null
    //private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "Hello World");
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>(); //이렇게 하면 아무것도 set하지 않은 상태에서 get() 했을 때 null 나오게 됨


    public static void main(String[] args) {

        // 첫 번째 스레드: ThreadLocal 값을 설정하고 출력
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            threadLocal.set("스레드 1의 값");
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            threadLocal.remove(); //원래 초기값만 남겨두고 삭제함
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get()); //초기값 튀어나옴
        }, "Thread-1");



        // 두 번째 스레드: ThreadLocal 값을 설정하고 출력
        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            threadLocal.set("스레드 2의 값");
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
