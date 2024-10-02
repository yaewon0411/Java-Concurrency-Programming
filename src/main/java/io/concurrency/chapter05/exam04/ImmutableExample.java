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
//모든 필드 또한 final로 선언함으로써 필드가 변경될 수 있는 여지를 원천 차단함
final class ImmutablePerson {
    private final String name;
    private final int age;

    //생성과 동시에 값이 할당되며, 수정 못함
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
