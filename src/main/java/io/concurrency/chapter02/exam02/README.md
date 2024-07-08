# ThreadStartRunExample

스레드는 start()를 통해서 실행시켜야 한다!<br>
run()은 JVM이 자동으로 호출하는 실행 메서드이기 때문에 직접 스레드에 run()을 호출해주게 되면 그냥 메인 스레드 스택에 run()이 생성되며, 별도의 스레드가 생성되지 않는다<br>

```java

public static void main(String[] args) {

    Thread thread1 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : 스레드 실행중..");
        }
    });

    Thread thread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : 스레드 실행중..");
        }
    });

    MyRunnable my = new MyRunnable();
    Thread thread3 = new Thread(my);

    thread1.start();
    thread2.start();
    thread3.run();


    System.out.println(Thread.currentThread().getName() + ": 메인 스레드 종료");
}

static class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": 스레드 실행 중..");
    }
}

```
지금 start()와 run()을 같이 사용해주고 있다<br>
출력 결과는 다음과 같다
```java
Thread-1 :스레드 실행중..
Thread-0 : 스레드 실행중..
main: 스레드 실행 중...
main: 메인 스레드 종료
Thread-2 : 스레드 실행중..
```
 - 스레드는 독립적으로 동작하기 때문에 메인 스레드가 종료된 후에도 새로운 스레드가 생성됨을 확인 가능
 - thread3.run()을 했기 때문에 main 스레드에서 run()이 동작했음을 확인 가능 (즉 새로운 스레드 생성 x)

# ThreadStackExample

문자열 리터럴은 불변 객체로서, JVM에 힙 영역 내의 String Pool에 저장<br>
따라서 스레드가 문자열 리터럴 객체를 생성하는 함수를 실행하면, 문자열 객체는 스레드 스택이 아니라 힙에 생성된다<br>
스레드 스택은 문자열 객체의 참조를 갖게 된다
ex)
```java
private void secondMethod(int localValue) {
    String objectReference = threadId +" : Hello World";
    System.out.println(Thread.currentThread().getName() + " : 스레드 ID : "+threadId + " Value : "+localValue);
}
```

# MultiThreadAppTerminatedExample

```java
메인 스레드 종료
Thread-1: 스레드 실행 중...
Thread-2: 스레드 실행 중...
Thread-0: 스레드 실행 중...
Thread-1 : 스레드 ID : 1, Value:101
Thread-0 : 스레드 ID : 0, Value:100
Thread-2 : 스레드 ID : 2, Value:102
```
메인 스레드가 먼저 종료되어도 애플리케이션 자체는 종료되지 않는다<br>
메인 스레드가 생성한 사용자 스레드가 완전히 종료돼야 어플리케이션이 종료된다<br>

# SingleThreadAppTerminatedExample

메인 스레드가 종료되면 어플리케이션 종료
