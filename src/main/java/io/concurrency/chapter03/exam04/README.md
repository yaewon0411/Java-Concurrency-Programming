# ThreadNamingExample

```java
public class ThreadNamingExample {
    public static void main(String[] args) throws InterruptedException {


        // 스레드 이름을 생성자에 전달하여 설정
        Thread myThread = new Thread(() -> {
            System.out.println("현재 스레드 이름: " + Thread.currentThread().getName());
        }, "myThread");
        myThread.start();

        // setName() 메서드를 사용하여 스레드 이름 설정
        Thread yourThread = new Thread(() -> {
            System.out.println("현재 스레드 이름: " + Thread.currentThread().getName());
        });
        yourThread.setName("yourThread");
        yourThread.start();

        // 여러 개의 스레드를 생성하여 기본 스레드 이름을 출력
        for (int i = 0; i < 5; i++) {
            Thread defaultThread = new Thread(() -> {
                System.out.println("현재 스레드 이름: " + Thread.currentThread().getName());
            });
            defaultThread.start();
        }

        Thread.sleep(2000);
    }
}
```
- 스레드의 이름을 지정하지 않으면 Thread-0...Thread-n까지 이렇게 0부터 시작해서 이름을 부여하는데,
- 위 경우 yourThread는 처음에 이름을 지정하지 않고 생성되었기 떄문에 Thread-0이라는 이름을 가지는데, setNamed()을 통해 이름을 수정함
- 따라서 이미 한번 nextThreadNum()이 호출되었기 때문에 threadSeqNumber가 1의 값을 갖고 있고, 이후 defaultThread는 Thread-1부터 시작해서 이름을 부여받게 됨
- 생성자에 이름을 같이 전달하면 nextThreadNum()이 호출되지 않고 바로 이름 부여됨

# CurrentThreadExample
스레드의 이름을 가져오는 3가지 경우를 살펴본다

1. 현재 실행 중인 스레드 정보 가져오기
```java
// 현재 실행 중인 스레드를 가져와 이름 출력
System.out.println("현재 스레드(main): " + Thread.currentThread());
System.out.println("현재 스레드 이름(main): " + Thread.currentThread().getName());
```
2. Thread 객체 내부에서 스레드 정보 가져오기
```java
    Thread thread1 = new Thread() {
    @Override
    public void run() {
        System.out.println("현재 스레드 (Thread 객체 사용): " + this);
        // 현재 스레드의 이름을 출력
        System.out.println("현재 스레드 이름 (Thread 객체 사용): " + getName());
    }
};
```
  - 위와 같은 경우는, 해당 스레드 내부에서 이름을 가져오는 것이기 때문에 바로 this 자체가 현재 스레드이다
3. Runnable 내부에서 스레드 정보 가져오기
```java
    Thread thread2 = new Thread(new ThreadName());

    static class ThreadName implements Runnable {

        @Override
        public void run() {
            System.out.println("현재 스레드 (Runnable 사용): " + Thread.currentThread());
            System.out.println("현재 스레드 이름 (Runnable 사용): " + Thread.currentThread().getName());
        }
    }
```
  - Runnable은 여러 스레드 간 사용 가능하기 때문에 반드시 Thread.currentThread()를 지칭해서 현재 CPU를 할당받은 스레드 정보를 가져오도록 참조해야한다