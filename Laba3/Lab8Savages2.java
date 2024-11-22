package Laba3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lab8Savages2 {
    static final int POT_CAPACITY = 5;
    static int currentPortions = 0;
    static int nextToEat = 0;
    static final Lock lock = new ReentrantLock();
    static final Condition potEmpty = lock.newCondition();
    static final Condition[] savageConditions;

    static {
        int numSavages = 10;
        savageConditions = new Condition[numSavages];
        for (int i = 0; i < numSavages; i++) {
            savageConditions[i] = lock.newCondition();
        }
    }

    public static void main(String[] args) {
        int numSavages = 10;

        Thread cook = new Thread(() -> {
            while (true) {
                lock.lock();
                try {
                    if (currentPortions == 0) {
                        System.out.println("Cook refills the pot.");
                        currentPortions = POT_CAPACITY;
                        potEmpty.signalAll(); // Уведомить всех о заполнении кастрюли
                    }
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread[] savages = new Thread[numSavages];
        for (int i = 0; i < numSavages; i++) {
            final int savageId = i;
            savages[i] = new Thread(() -> {
                while (true) {
                    lock.lock();
                    try {
                        // Ждать своей очереди
                        while (currentPortions == 0 || nextToEat != savageId) {
                            if (currentPortions == 0) {
                                potEmpty.await();
                            } else {
                                savageConditions[savageId].await();
                            }
                        }

                        currentPortions--;
                        System.out.println("Savage " + savageId + " eats. Portions left: " + currentPortions);

                        // Передать очередь следующему
                        nextToEat = (savageId + 1) % numSavages;
                        savageConditions[nextToEat].signal();

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
