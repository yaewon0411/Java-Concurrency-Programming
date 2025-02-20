## SpinLock

다음 동기화 기법 중에서
- Mutex
- Semaphore
- SpinLock


Mutex와 Semaphore는 락에 획득하지 못한 스레드가 대기에 빠지지만 <br>
SpinLock의 경우 락에 획득하지 못한 스레드는 대기에 빠지지 않고 Busy Waiting을 하게 된다


Busy Waiting은 스레드가 락 획득을 위해 반복적으로 바쁘게 락 획득을 시도하는 것을 의미하며, <br>
이를 위한 무한 루프 동안 CPU 점유가 발생하게 된다<br>

그렇지만 스레드가 블로킹되지 않고 바로 공유 자원에 접근을 시도하기 때문에 <br>
컨텍스트 스위칭 시간보다 임계 영역의 대기 시간이 더 짧은 경우 
`컨텍스트 스위칭 비용 감소` & `대기 시간 감소`라는 장점을 갖는다


### 싱글 코어 & 멀티 코어
싱글 코어에서 스핀락을 사용하면 해당 스레드가 무한 루프를 돌면서 다른 스레드가 CPU를 점유할 기회를 주지 않기 때문에
싱글 코어 환경에서는 일반적으로 busy waiting으로 인해 성능이 저하될 수 있으므로 멀티 코어 환경에서 사용하는 것이 더 효율적이다



* 스핀락은 멀티 코어 환경에 상관없이 대기 시간이 긴 경우나 공유 자원에 대한 경쟁이 많은 경우에는
다른 동기화 기법을 고려하는 것이 좋다