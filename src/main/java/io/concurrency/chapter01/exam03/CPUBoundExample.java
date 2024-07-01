package io.concurrency.chapter01.exam03;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CPUBoundExample {
    public static void main(String[] args) throws InterruptedException {

        //컴퓨터의 CPU 코어 개수 가져오기. 내 경우 16개 (병렬성 적용 위함)
        int numThreads = Runtime.getRuntime().availableProcessors();

        //ExecutorService는 스레드를 만드는 생성기 (16개 스레드 생성)
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        long startTime2 = System.currentTimeMillis();
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            Future<?> future = executorService.submit(() -> {

                // Cpu 연산이 집중되고 오래 걸리는 작업 (가정)
                long result = 0;
                for (long j = 0; j < 1000000000L; j++) {
                    result += j;
                }

                // 아주 잠깐 대기함
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("스레드: " + Thread.currentThread().getName() + ", " + result); // Cpu Bound 일때 ContextSwitching 은 크게 발생하지 않는다
            });
            futures.add(future);
        }
        futures.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        long endTime2 = System.currentTimeMillis();
        System.out.println("CPU 개수만큼의 데이터를 병렬로 처리하는 데 걸린 시간: " + (endTime2 - startTime2) + "ms");
        executorService.shutdown();
    }
}
