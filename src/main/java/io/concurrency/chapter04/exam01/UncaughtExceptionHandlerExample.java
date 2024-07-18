package io.concurrency.chapter04.exam01;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UncaughtExceptionHandlerExample {
    private static final Logger LOGGER = Logger.getLogger(UncaughtExceptionHandlerExample.class.getName());

    public static void main(String[] args) {


        Thread thread1 = new Thread(() -> {
            System.out.println("스레드 1 시작!");

            // 예기치 않은 예외 발생
            throw new RuntimeException("예기치 않은 예외!");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("스레드 2 시작!");

            // 예기치 않은 예외 발생
            throw new RuntimeException("예기치 않은 예외!");
        });

        thread1.setUncaughtExceptionHandler((t, e) -> {
            LOGGER.log(Level.SEVERE, t.getName() + "에서 예외 발생 : "+e);
            sendNotificationToAdmin(e);
        });

        thread2.setUncaughtExceptionHandler((t, e) -> {
            LOGGER.log(Level.SEVERE, t.getName()+"에서 예외 발생했긔!!! : "+e);
            sendNotificationToAdmin(e);
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName()+"에서 발생한 예외 전역적으로 캐치햇긔!! : "+e);
            }
        });

        // 스레드의 UncaughtExceptionHandler 설정
//        thread.setUncaughtExceptionHandler((t, e) -> {
//            LOGGER.log(Level.SEVERE, t.getName() + " 에서 예외가 발생했습니다.", e);
//
//            // 오류가 발생한 경우 알림 서비스 호출 (예: 이메일 또는 Slack 알림)
//            sendNotificationToAdmin(e);
//        });

        thread1.start();
        thread2.start();
    }

    // 알림 서비스를 호출하는 메서드
    private static void sendNotificationToAdmin(Throwable e) {
        System.out.println("관리자에게 알림: " + e.getMessage());
    }
}
