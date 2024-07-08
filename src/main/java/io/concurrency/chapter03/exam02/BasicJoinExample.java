package io.concurrency.chapter03.exam02;

public class BasicJoinExample {

    public static void main(String[] args) { //메인 함수의 메인 스레드가 일종의 부모 스레드

        Thread thread = new Thread(() -> { //메인 스레드의 자식 스레드
            try {
                System.out.println("스레드가 3초 동안 작동합니다.");
                Thread.sleep(5000);
                System.out.println("스레드 작동 완료.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();

        System.out.println("메인 스레드가 다른 스레드의 완료를 기다립니다.");


        try {
            //메인 스레드가 대상(자식) 스레드에게 join()을 걸고 있다!
            thread.join(); //자식 스레드가 완료될 때까지 대기

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("메인 스레드가 계속 진행합니다");

    }
}
