package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolV2 {
    public static void main(String[] args) {
        System.out.println("\n**** Thread pool started ✅ ****\n");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 20; i++) {
            int taskNo = i;
            executor.submit(() -> {
                String message = Thread.currentThread().getName() + ": Task " + taskNo;
                System.out.println(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // NOTE: Crucial to shutdown executor service
        // It initiates orderly shutdown; stops accepting new tasks but
        // allows all previously submitted tasks to finish execution before the pool is
        // terminated.
        executor.shutdown();

        try {
            // Wait for 60s for all the tasks in the thread pool to finish
            // If all tasks finish within 60s it returns `true`
            // Else if returns `false` and terminate the thread pool forcefully.
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("\n**** Thread pool stopped 🛑 ****\n");
    }
}
