package io.concurrency.chapter03.exam03;

public class InterruptedExceptionExample {
    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("인터럽트 상태 1: " + Thread.currentThread().isInterrupted());
                Thread.sleep(5000);
                System.out.println("스레드 잠자기 완료");
            } catch (InterruptedException e) { // InterruptedException 예외가 발생하면 인터럽트 상태가 초기화 된다 : false
                System.out.println("스레드가 인터럽트 되었습니다.");
                System.out.println("인터럽트 상태 2: " + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
            }
        });

        thread1.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread1.interrupt();
        thread1.join(); //메인 스레드가 thread1에 조인. 즉 thread1의 작업이 끝날 때까지 대기
        System.out.println("인터럽트 상태 3: " + thread1.isInterrupted());
    }
}
