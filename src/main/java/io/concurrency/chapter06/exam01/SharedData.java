package io.concurrency.chapter06.exam01;
public class SharedData {
    private int sharedValue = 0;
    private Mutex mutex;
    public SharedData(Mutex mutex) {
        this.mutex = mutex;
    }
    public void sum() {
        try {
            mutex.acquired(); // Lock 을 획득

            for (int i = 0; i < 10_000_0000; i++) {
                sharedValue++;
            }

        } finally { //for 구문 돌다가 오류날 수 있으므로 반드시 try-finally를 통해 락을 해제해준다
            mutex.release(); // Lock 해제
        }
    }
    public int getSum() {
        return sharedValue;
    }
}