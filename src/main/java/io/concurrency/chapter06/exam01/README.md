
## MutexExample

1. sharedData::sum를 스레드가 매개변수로 받을 수 있는 이유<br>

```java
SharedData sharedData = new SharedData(new Mutex()); //Runnable을 구현한 클래스가 아님

Thread th1 = new Thread(sharedData::sum);
```
Thread 클래스는 Runnable 인터페이스를 받는 생성자를 제공한다<br>
Runnable 인터페이스는 하나의 메서드만 있는 함수형 인터페이스로, run() 메서드를 정의한다

```java
@FunctionalInterface
public interface Runnable {
    void run();
}
```
이렇게 함수형 인터페이스로 선언되어 있기 때문에 람다 표현식이나 메서드 참조를 통해 run()을 바로 구현하는 것이 가능하다

따라서 Thread 생성자가 이를 Runnable 인터페이스로 변환하여 내부적으로 처리하기 때문에, 굳이 Runnable을 직접 구현하지 않아도 메서드 참조를 사용해 스레드를 생성할 수 있게 되는 것!!