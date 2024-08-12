## ThreadLocalExample

```java
private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "Hello World");
```
- 위처럼 초기값을 세팅한후 threadLocal을 계속 사용하다가 threadLocal.remove()를 하게 되면, 초기값만 남겨두고 다 삭제한다. 따라서 이후 get() 하면 초기값이 튀어나옴


## ThreadPoolThreadLocalExample
- 스레드풀은 많은 요청에 대해서 이미 생성된 스레드를 재사용하기 때문에 tl에 저장된 값을 반드시 remove 해줘야 함
- 

