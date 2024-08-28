# CPUBoundExample & IOBoundExample

CPU 바운드와 I/O 바운드는 병렬성과 동시성에서 약간의 차이가 있음

CPU 바운드의 경우 CPU Burst, 즉 CPU 치중 작업이 많기 때문에 최대한 코어 수와 비슷하게 스레드 수를 생성해서 병렬성의 이점을 볼 수 있도록 구성하는 것이 좋음

I/O 바운드의 경우 I/O Burst, 즉 CPU 치중 작업이 많지 않기 때문에 I/O 작업을 만나 CPU가 연산 작업을 하지 않는 상태가 많이 발생함. 따라서 이 때, 놀고 있는 CPU에게 (스레드를 할당할 수 있도록) 동시성의 이점을 볼 수 있도록 구성하는 것이 좋음