# UncaughtException

run() 메서드는 스레드 예외를 캐치하지 못하기 떄문에 스레드에서 발생한 예외를 캐치하려면 UncaughtExceptionHandler를 사용해야 한다

### setDefaultUncaughtExceptionHandler
- static 메서드로 전역적으로 모든 스레드의 예외를 캐치함
- 메인 스레드에서 다른 스레드들의 예외를 캐치하는 것 가능 -> 즉 외부에서 여러 스레드들의 예외 캐치 가능!!
```java
        // 모든 스레드의 예외에 대한 기본 핸들러 설정
        //이 핸들러를 호출하는 것은 메인 스레드! 따라서 메인(외부)에서 여러 스레드의 전역적인 예외 캐치 가능하다
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName() + " 에서 예외 발생 " + e);
            }
        });

        // 예외를 발생시키는 여러 스레드
        Thread thread1 = new Thread(() -> {
            throw new RuntimeException("스레드 1 예외!");
        });

        Thread thread2 = new Thread(() -> {
            throw new RuntimeException("스레드 2 예외!");
        });

        thread1.start();
        thread2.start();
```

### setUncaughtExceptionHandler
- 대상 스레드에서 발생하는 UncaughtException을 처리하는 인스턴스 메서드
- setDefaultUncaughtExceptionHandler보다 우선 순위 높음 -> 따라서 setDefaultUncaughtExceptionHandler를 구현했더라도, 대상 스레드에서 구현한 setUncaughtExceptionHandler이 있다면 이게 실행됨
```java

private static final Logger LOGGER = Logger.getLogger(UncaughtExceptionHandlerExample.class.getName());

public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("스레드 1 시작!");
            throw new RuntimeException("예기치 않은 예외!");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("스레드 2 시작!");
            throw new RuntimeException("예기치 않은 예외!");
        });

        thread1.setUncaughtExceptionHandler((t, e) -> {
            LOGGER.log(Level.SEVERE, t.getName() + "에서 예외 발생 : "+e);
            sendNotificationToAdmin(e);
        });

        thread2.setUncaughtExceptionHandler((t, e) -> {
            LOGGER.log(Level.SEVERE, t.getName()+"에서 예외 발생했긔!!! : "+e);
            sendNotificationToAdmin(e);
        });

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName()+"에서 발생한 예외 전역적으로 캐치햇긔!! : "+e);
            }
        });

        thread1.start();
        thread2.start();
    }

    // 알림 서비스를 호출하는 메서드
    private static void sendNotificationToAdmin(Throwable e) {
        System.out.println("관리자에게 알림: " + e.getMessage());
    }

```
- thread1 예외 발생 시 -> SEVERE: Thread-0에서 예외 발생 : java.lang.RuntimeException: 예기치 않은 예외! 
- thread2 예외 발생 시 -> SEVERE: Thread-1에서 예외 발생했긔!!! : java.lang.RuntimeException: 예기치 않은 예외! 