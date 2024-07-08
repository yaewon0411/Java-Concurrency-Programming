# InterruptedJoinExample

조인을 호출하는 스레드와, 조인의 대상이 되는 스레드와, 조인에서 인터럽트 되는 스레드를 잘 구분 지어야 한다

가정)<br>
메인 스레드 -> 조인을 호출하는 스레드 <br>
스레드 1 -> 조인의 대상이 되는 스레드. 인터럽트 되면 자신을 호출한 메서드를 인터럽트 시킴<br>
스레드 2 -> 스레드 1을 인터럽트 시키는 스레드<br>


```java

try{
    //메인 스레드 코드 블럭
    스레드1.start(); // 스레드 1 실행
    
    스레드1.join(); //메인 스레드가 일시 중지 상태에 들어감

    스레드2.start(); //스레드 1을 인터럽트 시키는 스레드 실행

    //스레드 1 인터럽트 발생 -> InterruptedException 발생

    //스레드 1의 InterruptedException에 의해 메인 스레드 인터럽트

    // 메인 스레드의 인터럽트 발생 -> InterruptedException 발생

    System.out.println("메인 스레드가 작업이 정상적으로 종료되었습니다");
    
    }catch (InterruptedException e){
        System.out.println("메인 스레드가 인터럽트 되었습니다"); //해당 문구가 출력됨
    }
```
이에 따라 스레드 1은 메인 스레드를 인터럽트하는 코드를 갖고 있어야 한다

```java
        Thread mainThread = Thread.currentThread();

        Thread longRunningThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    System.out.println("긴 스레드가 계속 실행 중...");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                mainThread.interrupt();
                System.out.println("긴 작업 스레드가 인터럽트 되었습니다");
            }
        });
```

### join을 호출한 스레드가 인터럽트를 받아야 wait()에서 빠져나오고 Runnable로 전환