package io.concurrency.chapter05.exam04;

public class ImmutableExample  implements Runnable{
    private ImmutablePerson person;

    public ImmutableExample(ImmutablePerson person) {
        this.person = person;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " - 이름: " + person.getName() + ", 나이: " + person.getAge());
    }

    public static void main(String[] args) {

        ImmutablePerson person = new ImmutablePerson("홍길동", 25);

        //여러 스레드에서 하나의 참조 객체를 공유하게 되면 잘못된 결과가 나올 수 있다
        //그러나 이 경우는 스레드 안전한데,
        //그 이유는 공유하고 있는 참조 객체가 final, 즉 불변 객체이기 때문이다
        for (int i = 0; i < 10; i++) {
            new Thread(new ImmutableExample(person)).start();
        }
    }
}

//상속 못 함
final class ImmutablePerson {
    private final String name;
    private final int age;

    public ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
