package threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Worker extends Thread {
    private final BlockingQueue<Runnable> queue;
    private boolean isStopped = false;

    public Worker(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isStopped()) {
            try {
                Runnable runnable = queue.take();
                runnable.run();
            } catch (InterruptedException e) {
                this.isStopped();
            }
        }
    }

    public synchronized void stopWorker() {
        this.isStopped = true;
        this.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}

class SimpleThreadPool {
    private final BlockingQueue<Runnable> queue;
    private final List<Worker> workers = new ArrayList<>();
    private boolean isStopped = false;

    public SimpleThreadPool(int numThreads, int maxNumTasks) {
        queue = new ArrayBlockingQueue<>(maxNumTasks);

        for (int i = 0; i < maxNumTasks; i++) {
            workers.add(new Worker(queue));
        }

        for (Worker worker : workers) {
            worker.start();
        }
    }

    public synchronized void execute(Runnable task) throws IllegalStateException {
        if (this.isStopped) {
            throw new IllegalStateException("Thread pool is stopped");
        }

        // `offer()` will return false if the queue is full, unlike `put()` which blocks
        if (!this.queue.offer(task)) {
            System.out.println("Task queue is full, task rejected.");
        }
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (Worker worker : workers) {
            worker.stopWorker();
        }
    }
}

public class ThreadPoolV1 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n**** Thread pool started ✅ ****\n");

        SimpleThreadPool threadPool = new SimpleThreadPool(3, 10);

        for (int i = 0; i < 20; i++) {
            int taskNo = i;
            threadPool.execute(() -> {
                String message = Thread.currentThread().getName() + ": Task " + taskNo;
                System.out.println(message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(10000);

        threadPool.stop();

        System.out.println("\n**** Thread pool stopped 🛑 ****\n");
    }
}
