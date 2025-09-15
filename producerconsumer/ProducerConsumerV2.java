package producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// Producer
class UserModern implements Runnable {
    private final BlockingQueue<PrintTask> queue;
    private int jobId = 0;

    public UserModern(BlockingQueue<PrintTask> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                PrintTask task = new PrintTask(++jobId, "Document-" + jobId);
                System.out.println("Producing: " + task);
                queue.put(task);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// Consumer
class PrinterModern implements Runnable {
    private final BlockingQueue<PrintTask> queue;

    public PrinterModern(BlockingQueue<PrintTask> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                PrintTask task = queue.take();
                System.out.println("Consuming: " + task);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerV2 {
    public static void main(String[] args) {
        BlockingQueue<PrintTask> queue = new ArrayBlockingQueue<>(5);

        Thread userThread = new Thread(new UserModern(queue));
        Thread printerThread1 = new Thread(new PrinterModern(queue));
        Thread printerThread2 = new Thread(new PrinterModern(queue));

        System.out.println("Starting modern print simulation...");
        userThread.start();
        printerThread1.start();
        printerThread2.start();
    }
}
