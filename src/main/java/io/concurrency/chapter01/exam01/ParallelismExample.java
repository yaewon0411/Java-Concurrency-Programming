package io.concurrency.chapter01.exam01;

import java.util.ArrayList;
import java.util.List;

public class ParallelismExample {
    public static void main(String[] args) {
//        int cpuCores = 1;
        //내 컴퓨터의 CPU 코어 개수 가져오기. 내 컴퓨터는 코어 개수 16
        int cpuCores = Runtime.getRuntime().availableProcessors();
        System.out.println("컴퓨터 코어 개수 = " + cpuCores);

        // CPU 개수만큼 데이터를 생성
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < cpuCores; i++) {
            data.add(i);
        }

        // CPU 개수만큼 데이터를 병렬로 처리
        long startTime1 = System.currentTimeMillis();

        //0부터 n까지의 데이터를 각각 쪼개서 하나의 데이터를
        //하나의 스레드가 맡고, 그 작업을 동시에 독립적으로 처리
        //즉, n+1개의 스레드가 생성
        long sum1 = data.parallelStream()
                .mapToLong(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i * i;
                })
                .sum();

        long endTime1 = System.currentTimeMillis();

        System.out.println("CPU 개수만큼 데이터를 병렬로 처리하는 데 걸린 시간: " + (endTime1 - startTime1) + "ms");
        System.out.println("결과1: " + sum1);

    }
}
