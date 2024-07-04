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