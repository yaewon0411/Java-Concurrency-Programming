package io.concurrency.chapter04.exam02;

public class FlagThreadStopExample {
    // volatile 키워드 추가
   //volatile boolean running = true;
    static boolean running = true;

//    public void volatileTest() {
//        new Thread(() -> {
//            int count = 0;
//            while (running) {
//                /*try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }*/
//                count++;
//            }
//            System.out.println("Thread 1 종료. Count: " + count);
//        }).start();
//
//        new Thread(() -> {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//            }
//            System.out.println("Thread 2 종료 중..");
//            running = false;
//        }).start();
//    }

    public static void main(String[] args) {

        new Thread(() -> {
            int cnt = 0;
            while(running){
                try {
                    Thread.sleep(1); //대기에 빠지면서 문맥 교환 발생.
                    //이 때, 다음 스레드가 캐시 메모리를 사용하게 하기 위해 이 스레드가 사용중이던 캐시 메모리 내용을 비워줘야 한다
                    //sleep이 끝나서 다시 이 스레드로 문맥 교환이 발생하게 되면, 이전에 사용중이던 스레드의 캐시 메모리는 마찬가지로 비워진 상태
                    //따라서 스레드가 사용할 정보를 얻기 위해 메모리에서 정보들을 가져오게 되며, 메모리에는 이전 스레드에서 설정한 정보가 어떤 시점에는 분명히 반영됐을 것이다
                    //따라서 running 플래그가 false임이 반영되어 종료된다
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                cnt++;
            }
            System.out.println("스레드 1 종료, cnt : "+cnt);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("스레드 2 종료");
            running = false; //메모리에 즉시 반영되는 게 아니라, thread2의 캐시 메모리에 반영된다. 어떤 적절한 시점에 이 내용이 메모리에 반영된다
        }).start();

        //new FlagThreadStopExample().volatileTest();
    }


}
