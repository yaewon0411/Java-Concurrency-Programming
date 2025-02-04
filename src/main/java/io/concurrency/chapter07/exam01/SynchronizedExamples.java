package io.concurrency.chapter07.exam01;

public class SynchronizedExamples {

    private int instanceCount = 0;
    private static int staticCount = 0;

    //메서드 동기화 방식 - 인스턴스 메서드 -> 모니터 M1
    public synchronized void instanceMethod(){
        instanceCount++;
        System.out.println("인스턴스 메서드 동기화: "+ instanceCount);
    }

    //메서드 동기화 방식 - 클래스 객체 -> 모니터 M2
    public synchronized static void staticMethod(){
        staticCount++;
        System.out.println("정적 메서드 동기화: "+staticCount);
    }

    //블록 동기화 방식 - 인스턴스 객체 -> 모니터 M1
    public void instanceBlock(){
        synchronized (this){
            instanceCount++;
            System.out.println("인스턴스 블록 동기화: "+instanceCount);
        }
    }

    //블록 동기화 방식 - 클래스 객체 -> 모니터 M2
    public static void staticBlock(){
        synchronized (SynchronizedExamples.class){
            staticCount++;
            System.out.println("정적 블록 동기화: "+staticCount);
        }
    }

//    private int instanceCount = 0;
//    private static int staticCount = 0;
//
//    public synchronized void instanceMethod() {
//        instanceCount++;
//        System.out.println("인스턴스 메서드 동기화: " + instanceCount);
//    }
//
//    public static synchronized void staticMethod() {
//        staticCount++;
//        System.out.println("정적 메서드 동기화: " + staticCount);
//    }
//
//    public void instanceBlock() {
//        synchronized (this) {
//            instanceCount++;
//            System.out.println("인스턴스 블록 동기화: " + instanceCount);
//        }
//    }
//
//    public static void staticBlock() {
//        synchronized (SynchronizedExamples.class) {
//            staticCount++;
//            System.out.println("정적 블록 동기화: " + staticCount);
//        }
//    }

    public static void main(String[] args) {
        SynchronizedExamples example = new SynchronizedExamples();

        new Thread(example::instanceMethod).start();
        new Thread(example::instanceBlock).start();
        new Thread(SynchronizedExamples::staticMethod).start();
        new Thread(SynchronizedExamples::staticBlock).start();
    }
}
