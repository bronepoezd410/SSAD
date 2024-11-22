package Laba3;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Lab8Savages1 {
    static int potCapacity = 5;
    static int currentPortions = 0;
    static final ReentrantLock lock = new ReentrantLock();
    static final Condition emptyPot = lock.newCondition();

    public static void main(String[] args) {
        Thread cook = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    if (currentPortions == 0) {
                        System.out.println("Cook is refilling the pot...");
                        currentPortions = potCapacity;
                        emptyPot.signalAll();
                    }
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread[] savages = new Thread[10];
        for (int i = 0; i < savages.length; i++) {
            savages[i] = new Thread(() -> {
                while (true) {
                    lock.lock();
                    try {
                        while (currentPortions == 0) {
                            emptyPot.await();
                        }
                        currentPortions--;
                        System.out.println(Thread.currentThread().getName() + " ate. Portions left: " + currentPortions);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }

        cook.start();
        for (Thread savage : savages) savage.start();
    }
}
