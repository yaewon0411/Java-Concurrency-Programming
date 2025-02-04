## synchronized 기본

자바에 내장된 락으로서 암묵적인 락 또는 모니터 락이라 한다

즉 synchronized 안에 모니터가 들어간 것이라 보면 됨
synchronized를 사용한 동기화 방법 4가지
- synchronized method
- static synchronized method
- synchronized block
- static synchronized block


1. 메서드 동기화 방식 (synchronized method)
- 메서드 전체가 임계 영역이 되므로 메서드 내 모든 코드가 동기화됨
- 동시성 문제를 한번에 제어할 수 있지만 메서드 내 코드의 세부적인 동기화 구조를 갖기 어려움
- 따라서 동기화 영역이 큰 경우 성능 저하 가능
- 인스턴스 메서드 동기화 방식과 정적 메서드 동기화 방식이 있다

2. 블록 동기화 방식 (synchronized block)
- 특정 블록을 정해서 임계 영역을 구성 -> 즉 블록 내 코드만 동기화
- 메서드 동기화 방식에 비해 좀 더 세부적이고 효율적으로 임계 영역 설정 가능
- 인스턴스 블록 동기화 방식과 정적 블록 동기화 방식이 있다


ex)
```java

public class A {
    
    public synchronized void method1(){
        
    }
    
    public static synchronized method2(){
        
    }
    
    public void method3(){
        synchronized (this){
            
        }
    }
    
    public static void method4(){
        synchronized (A.class){
            
        }
    }
}

```
class A에 존재하는 모니터는 2개이다<br>
method1과 method3은 같은 모니터를 M1을 갖고 (개별 인스턴스 레벨의 동기화를 담당)<br>
method2와 method4는 같은 모니터 M2를 갖는다 (클래스 레벨의 전역적인 동기화를 담당)<br>

따라서 method1에 한 스레드가 접근한다면, 동시에 다른 스레드가 method2에 접근할 수 있다

```java
A a1 = new A();
A a2 = new A();
```


스레드 1이 a1.method1()을 실행할 떄, 다른 스레드는 a1.method1() 또는 a1.method3()을 실행할 수 없다<br>
하지만 a2.method1() 또는 a2.method3()은 실행 가능한 것<br>

스레드 1이 A.method2()를 실행 중일 때<br>
다른 스레드는 A.method2() 또는 A.method4()를 실행할 수 없고<br>
이는 a1, a2에 모두 적용된다