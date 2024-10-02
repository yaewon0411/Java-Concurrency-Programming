package io.concurrency.chapter06.exam01;

//상호 배제 원리를 구현한 클래스
public class Mutex {
    private boolean lock= false;

    public synchronized void acquired(){
        while(lock){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        this.lock = true; //임계 영역 진입
    }
    public synchronized void release(){
        this.lock = false;
        this.notify(); //대기하고 있는 스레드를 깨움
    }
}
