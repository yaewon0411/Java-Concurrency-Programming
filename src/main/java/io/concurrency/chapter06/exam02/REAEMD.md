## Semaphore

- 세마포어: 공유 자원에 대한 접근 제어를 위한 신호 전달 메커니즘 동기화 도구
  - S: 공유 자원 개수
    - S > 0 -> 공유 자원 접근 허용
    - S <= 0 -> 공유 자원 접근 거부
  - P: 스레드 진입 여부 결정 (wait 연산)
  - V: 대기 중인 스레드를 깨우는 (signal 연산) <br>

---> 같은 세마포어의 P,V 함수의 S 연산은 원자적 실행이 보장되어야 한다(보장된다)


Mutex는 락을 획득하고 해제한 스레드가 동일해야 하지만<br>
Semaphore는 락을 획득하고 해제한 스레드가 같지 않아도 된다

Mutex는 기본적으로 잠긴 상태로 초기 시작되지만, 세마포어는 초기값 설정을 통해 공유 자원에 몇 개의 동시 스레드를 허용할 것인지 설정할 수 있다

이진 세마포어를 구현한다면 Mutex와 동일하게 구현 가능

Mutex가 상호 배제 목적에 더 부합하고, Semaphore는 주로 리소스의 한정적 사용을 제한하는데 부합하다


ex) 이진 세마포어

```java
public class BinarySemaphore implements CommonSemaphore {
    private int signal = 1;

    public synchronized void acquired() {
        while (this.signal == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 현재 스레드의 인터럽트 상태를 설정
            }
        }
        this.signal = 0;
    }

    public synchronized void release() {
        this.signal = 1; //다른 스레드들이 사용 가능함을 설정
        this.notify(); // 모든 대기 스레드 중 임의로 선택한 스레드 하나만 깨움.
    }
}
```

synchronized가 붙은 메서드가 호출되면 해당 객체의 다른 synchronized가 붙은 어떤 메서드들도 호출될 수 없다
따라서 release()의 원자적 실행이 보장되며, 이는 V연산(signal)의 원자적 실행을 보장하는 것이다
임계 영역을 끝낸 스레드는 acquired()에 의해 wait 중인 대기 스레드들 중 하나를 notify()함으로써 다른 스레드의 진입을 허용한다


