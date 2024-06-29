# ContextSwitchExample

간단한 스레드 문맥 교환을 살펴봄

```java
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

        thread1.start();
        thread2.start();
        thread3.start();
```
- 출력 결과를 보면 다음과 같음
```java
Thread 2: 0
Thread 1: 0
Thread 3: 0
Thread 2: 1
Thread 1: 1
Thread 3: 1
Thread 2: 2
Thread 3: 2
Thread 1: 2
Thread 2: 3
Thread 1: 3
Thread 3: 3
Thread 2: 4
Thread 3: 4
Thread 1: 4
```
- 각 스레드가 순서 없이 번갈아 가며 값을 출력하고 있음 -> 문맥 교환!!
- CPU 스케줄러가 스레드 2를 가져가서 작업 처리한 후 스레드 1을 가져가 작업 처리하고, 스레드 3을 가져가 작업 처리하고...
- 지금은 스레드 개수가 적어서 약간의 패턴이 보이긴 하지만, 스레드 개수가 많아지면 한 스레드의 작업이 길게 이어지기도 하고 어떤 규칙성이 안보임