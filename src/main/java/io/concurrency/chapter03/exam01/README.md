# InterruptSleepExample

```java
public static void main(String[] args) throws InterruptedException {

        Thread sleepingThread = new Thread(() -> {
            try {
                System.out.println("20초 동안 잠듭니다. 인터럽트되지 않는다면 계속 잠들어 있을 것입니다.");

                Thread.sleep(20000); // 스레드는 지정된 시간 동안 잠듭니다

                System.out.println("인터럽트 없이 잠에서 깨었습니다.");

            } catch (InterruptedException e) {
                System.out.println("잠들어 있는 동안 인터럽트 되었습니다!");
            }
        });

        sleepingThread.start();

        //메인 스레드를 1초 정도 쉬게 한 다음
        Thread.sleep(1000);

        //메인 스레드가 sleepingThread에 대해 인터럽트 발생!!
        sleepingThread.interrupt();
    }
```
- 메인 함수 내에서 실행된 Thread.sleep()은 메인 스레드를 일시 중지
- 반면 스레드 객체 내에서 람다 표현식이나 익명 클래스를 통해 생성된 별도의 실행 블럭 내에서 sleep()을 걸면, 그 실행 블럭을 수행하는 스레드가 일시 중지