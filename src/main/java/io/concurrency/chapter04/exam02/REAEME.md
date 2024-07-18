# FlagThreadStopExample

자바의 스레드와 메모리 가시성 문제를 살펴본다

1) 첫 번째 경우
```java
boolean running = true;    
    new Thread(() -> {
            int cnt = 0;
            while(running){
                cnt++;
            }
            System.out.println("스레드 1 종료, cnt : "+cnt);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("스레드 2 종료");
            running = false;
        }).start();
```
두 번째 스레드에서 running = false를 할당했지만, 스레드 1이 종료되었다는 문구가 출력되지 않는다<br>
스레드 2의 캐시 메모리에서만 새 값이 할당되고, 메모리에는 아직 반영되지 않았기 때문이다<br>
이는 어떤 적절한 시점에 메모리에 반영되며, 이 변수가 갱신되는 것을 스레드 1은 즉시 볼 수 없다<br>
자바 메모리에서는 volatile 키워드가 없는 한, 스레드에서 변경한 값이 다른 스레드에게 보장된 시간 안에 가시화되지 않는다

2) 두 번째 경우
```java
boolean running = true;    
    new Thread(() -> {
            int cnt = 0;
            while(running){
                try {
                    Thread.sleep(1); 
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cnt++;
            }
            System.out.println("스레드 1 종료, cnt : "+cnt);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("스레드 2 종료");
            running = false;
        }).start();

```
running 변수의 값이 false로 변경된 후 스레드 1이 거의 즉시 종료된다<br>
이는 스레드 1의 Thread.sleep() 때문이다<br>
스레드가 일시 정지 되며, 문맥 교환을 유발하고 CPU가 다른 작업을 수행하게 된다<br>
스레드가 일시 중지 상태일 때, CPU는 다른 스레드를 실행할 수 있는데, 이 과정에서 메모리 캐시가 갱신되거나 메모리와의 동기화가 일어난 것으로 보인다<br>
따라서 스레드 2에서 스레드 1로 다시 전환될 때, running = false가 메인 메모리에 반영되어 스레드 1이 이를 볼 수 있다

인강에서 말한 내용은 이러함
```text
//대기에 빠지면서 문맥 교환 발생.
//이 때, 다음 스레드가 캐시 메모리를 사용하게 하기 위해 스레드 간 캐시 메모리 전환이 필요하다
//sleep()이 끝나서 다시 스레드1로 문맥 교환이 발생하게 되면, 이전에 사용중이던 스레드의 캐시 메모리에서 스레드 1을 위한 캐시 메모리로 전환 발생
//따라서 스레드가 사용할 정보를 얻기 위해 메모리에서 정보들을 가져오게 되며, 메모리에는 이전 스레드에서 설정한 정보가 어떤 시점에는 분명히 반영됐을 것이다
//따라서 running 플래그가 false임이 반영되어 종료된다
```

+) GPT
```text
문맥 교환 과정에서 발생하는 캐시 메모리의 처리는 다음과 같이 진행됩니다:

문맥 교환: 운영 체제는 현재 실행 중인 스레드의 상태(레지스터 상태, 프로그램 카운터 등)를 저장하고, 다음 실행할 스레드의 상태를 복원합니다.

CPU 캐시: 문맥 교환 시 CPU의 캐시 내용은 자동으로 교체될 수 있습니다. 새로운 스레드가 실행될 때 필요한 데이터가 캐시에 없다면, 메모리에서 해당 데이터를 가져와 캐시를 새롭게 채우게 됩니다. 이 과정을 캐시 미스라고 하며, 캐시에 적중하지 않은 요청에 대해 메인 메모리에서 데이터를 읽어야 합니다.

캐시 코히런시: 다중 코어 시스템에서는 여러 코어가 동시에 다른 스레드를 실행할 수 있으며, 이 경우 각 코어의 캐시 간 일관성을 유지하기 위해 캐시 코히런시 메커니즘이 작동합니다.
```

# InterruptedThreadStopExample4
```java
        Thread worker = new Thread(() -> {
            try {
                while (true) {
                    // 스레드의 작업을 수행합니다.
                    System.out.println("작업 스레드가 실행 중입니다.");
                    System.out.println("인트럽트 상태 1 : " + Thread.currentThread().isInterrupted());

                    //임의적으로 InterruptedException을 날리면 인터럽트 상태가 초기화되지 않는다
                    if(Thread.currentThread().isInterrupted()) throw new InterruptedException("thread is interrupted");
                }
            } catch (InterruptedException e) {
                // Thread.currentThread().isInterrupted() 는 interrupt 상태를 유지한다.
                // 그래서 interrupt() 를 호출할 필요가 없다
                System.out.println("인트럽트 상태 2 : " + Thread.currentThread().isInterrupted());
            }
            System.out.println("작업 스레드가 중단되었습니다.");
            System.out.println("인트럽트 상태 2 : " + Thread.currentThread().isInterrupted());
        });
```
대기 상태로 전환될 때의 InterruptedException 구현이 아니라, 임의적으로 구현해서 인터럽트 예외를 날리게 되면 인터럽트 상태가 바뀌지 않는다