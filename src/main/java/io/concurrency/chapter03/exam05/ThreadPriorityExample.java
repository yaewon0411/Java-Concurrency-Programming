package io.concurrency.chapter03.exam05;

public class ThreadPriorityExample {

    public static void main(String[] args) throws InterruptedException {

        CountingThread maxThread = new CountingThread("우선 순위가 높은 스레드", Thread.MAX_PRIORITY);
        CountingThread normThread = new CountingThread("우선 순위가 기본인 스레드", Thread.NORM_PRIORITY);
        CountingThread minThread = new CountingThread("우선 순위가 낮은 스레드", Thread.MIN_PRIORITY);

        normThread.start();
        maxThread.start();
        minThread.start();

        normThread.join();//메인 스레드가 normThread가 끝날 때까지 대기
        minThread.join();//메인 스레드가 minThread가 끝날 때까지 대기
        maxThread.join(); //메인 스레드가 maxThread가 끝날 때까지 대기

        System.out.println("작업 완료");

    }

    static class CountingThread extends Thread {
        private final String threadName;
        private int count = 0;

        public CountingThread(String threadName, int priority) {
            this.threadName = threadName;
            setPriority(priority);
        }

        @Override
        public void run() {
            while (count < 10000000) {
                count++;
            }
            System.out.println(threadName + ": " + count);
        }
    }
}
