package Laba3;

public class Lab8NoSync {
    static int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        int n = Integer.parseInt(args[0]); // Количество потоков типа 1
        int m = Integer.parseInt(args[1]); // Количество потоков типа 2

        Thread[] incrementThreads = new Thread[n];
        Thread[] decrementThreads = new Thread[m];

        for (int i = 0; i < n; i++) {
            incrementThreads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    counter++;
                }
            });
        }

        for (int i = 0; i < m; i++) {
            decrementThreads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    counter--;
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
// Без синхронизации потоки конкурируют за доступ к счётчику, что приводит к некорректным результатам.