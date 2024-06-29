#  ParallelismExample
병렬성 확인

```java
//컴퓨터 CPU 코어 개수 가져오기. 내 경우 코어 개수 16개
int cpuCores = Runtime.getRuntime().availableProcessors();

// CPU 개수(16)만큼 데이터를 생성
List<Integer> data = new ArrayList<>();
for (int i = 0; i < cpuCores; i++) {
    data.add(i);
}

long startTime1 = System.currentTimeMillis();

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
```
- parallelStream을 통해 data 개수(CPU 개수)만큼의 스레드를 생성
- 하나의 데이터를 하나의 스레드가 맡고, 그 합을 더하는 작업을 동시에 독립적으로 처리
-
```java
컴퓨터 코어 개수 = 16
CPU 개수만큼 데이터를 병렬로 처리하는 데 걸린 시간: 534ms
결과1: 1240
```
- 병렬로 처리해서 0.5초 정도 걸림
- 그럼 parallelStream을 stream으로 변경해서 수행해보면? (단일 스레드에서 연속적으로 연산 수행)
```java
컴퓨터 코어 개수 = 16
CPU 개수만큼 데이터를 병렬로 처리하는 데 걸린 시간: 8117ms
결과1: 1240
```


# ConcurrencyExample
동시성 확인
- CPU 코어 2배 만큼의 데이터를 생성하고, CPU 개수를 초과하는 데이터를 병려롤 처리하면?
```java
long startTime2 = System.currentTimeMillis();
        long sum2 = data.parallelStream()
                .mapToLong(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i * i;
                })
                .sum();

        long endTime2 = System.currentTimeMillis();

        System.out.println("CPU 개수를 초과하는 데이터를 병렬로 처리하는 데 걸린 시간: " + (endTime2 - startTime2) + "ms");
        System.out.println("결과2: " + sum2);
```
```java
CPU 개수를 초과하는 데이터를 병렬로 처리하는 데 걸린 시간: 1046ms
결과2: 10416
```

- 병렬성 확인에서 보다 2배의 데이터를 처리하므로 시간이 더 소요된 것이라 볼 수도 있음.
- 그럼 데이터 개수를 코어 개수보다 한 많은 17로 했을 떄, 병렬과 유사한 0.6초 정도 걸려야 함
```java
CPU 개수를 초과하는 데이터를 병렬로 처리하는 데 걸린 시간: 1020ms
결과2: 1496
```
- 그러나 여전히 1초 정도 걸림
  - 병렬성이라는 것은 정말 CPU 하나 당 스레드가 하나씩 붙어서 동시적으로 일괄 처리하는 상태를 말함. 그렇지만 CPU 개수보다 더 많은 작업을 처리해버리면 그 때는 병렬 이점을 가져가더라도 동시성도 적용되어버림
  - 즉, 하나의 CPU가 동시에 2개의 스레드를 실행시키지 못하기 때문에, 한 스레드가 작업 중에 다른 스레드와 문맥 교환되어야 함