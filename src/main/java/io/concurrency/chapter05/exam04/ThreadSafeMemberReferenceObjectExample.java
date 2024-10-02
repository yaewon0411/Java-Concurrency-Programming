package io.concurrency.chapter05.exam04;

public class ThreadSafeMemberReferenceObjectExample {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new MyRunnable(new Company("User"))).start(); // 스레드에 안전함, 멤버변수를 공유하지 않음
        new Thread(new MyRunnable(new Company("User"))).start(); // 스레드에 안전함, 멤버변수를 공유하지 않음

        Thread.sleep(1000);
        System.out.println("============================================================");

        //객체는 레퍼런스로 생성되기 떄문에, company의 주소값이 동일하게 두 스레드의 매개변수에 들어가서 공유하게 됨
        Company company = new Company("Company"); // 스레드에 안전하지 못함, 멤버변수를 공유함
        new Thread(new MyRunnable(company)).start();
        new Thread(new MyRunnable(company)).start();

    }
}

class MyRunnable implements Runnable {
    private Company company;

    public MyRunnable(Company company) {
        this.company = company;
    }

    @Override
    public void run() {
        company.changeName(Thread.currentThread().getName());
    }
}

class Company {
    private Member member;

    public Company(String name) {
        this.member = new Member(name);
    }

    //지역 변수가 아니라 멤버 변수를 수정하고 있음
    public synchronized void changeName(String name) {
        String oldName = member.getName();
        member.setName(name);
        System.out.println(oldName + ": " + member.getName());
    }
}

class Member {
    private String name;

    public Member(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
