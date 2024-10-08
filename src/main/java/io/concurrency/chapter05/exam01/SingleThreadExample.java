package io.concurrency.chapter05.exam01;

public class SingleThreadExample {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        int sum = 0;
        for (int i = 1; i <= 1000; i++) {
            sum += i;
            try {
                Thread.sleep(1);
                throw new RuntimeException("error"); //싱글 스레드니까 에러 터지면 애플리케이션 종료
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("합계: " + sum);
        System.out.println("싱글 스레드 처리 시간: " + (System.currentTimeMillis() - start) + "ms");
    }
}
