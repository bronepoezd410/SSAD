package Laba3;

import java.util.concurrent.locks.ReentrantLock;

public class Lab8ReentrantLock {
    static int counter = 0;
    static final ReentrantLock lock = new ReentrantLock();

    public static void increment() {
        lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }
    }

    public static void decrement() {
        lock.lock();
        try {
            counter--;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        Thread[] incrementThreads = new Thread[n];
        Thread[] decrementThreads = new Thread[m];

        for (int i = 0; i < n; i++) {
            incrementThreads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    increment();
                }
            });
        }

        for (int i = 0; i < m; i++) {
            decrementThreads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    decrement();
                }
            });
        }

        long startTime = System.nanoTime();

        for (Thread t : incrementThreads) t.start();
        for (Thread t : decrementThreads) t.start();
        for (Thread t : incrementThreads) t.join();
        for (Thread t : decrementThreads) t.join();

        long endTime = System.nanoTime();

        System.out.println("Final Counter Value: " + counter);
        System.out.println("Execution Time (ms): " + (endTime - startTime) / 1_000_000);
    }
}
