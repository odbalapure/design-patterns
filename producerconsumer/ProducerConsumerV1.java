package producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

class PrintTask {
    private final int jobId;
    private final String document;

    public PrintTask(int jobId, String document) {
        this.jobId = jobId;
        this.document = document;
    }

    @Override
    public String toString() {
        return "PrintTask {jobId=" + jobId + ", document=" + document + "}";
    }
}

class TaskQueue {
    private final Queue<PrintTask> queue = new LinkedList<>();
    private final int capacity;

    public TaskQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(PrintTask task) throws InterruptedException {
        // Wait till the queue is full
        while (queue.size() == capacity) {
            System.out.println("The queue is full, producer is waiting...");
            wait(); // Releases the lock and waits
        }

        queue.add(task);
        System.out.println("Produced: " + task);
        notifyAll(); // Wakes up all the threads (producers or consumers)
    }

    public synchronized PrintTask consume() throws InterruptedException {
        // Wait till the queue is empty
        while (queue.isEmpty()) {
            System.out.println("Queue is empty, consumer is waiting...");
            wait(); // Releases the lock and waits
        }

        PrintTask task = queue.poll();
        System.out.println("Consumed: " + task);
        notifyAll();
        return task;
    }
}

// The producer
class User implements Runnable {
    private final TaskQueue taskQueue;
    private int jobId = 0;

    public User(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                PrintTask task = new PrintTask(++jobId, "Document-" + jobId);
                taskQueue.produce(task);
                Thread.sleep(100); // Simulate time taken to create a task
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

// The Consumer
class Printer implements Runnable {
    private final TaskQueue taskQueue;

    public Printer(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                taskQueue.consume();
                Thread.sleep(500); // Simulate processing the task
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ProducerConsumerV1 {
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueue(5); // Buffer size of 5

        // One user (producer) and two printers (consumers)
        Thread userThread = new Thread(new User(taskQueue));
        Thread printerThread1 = new Thread(new Printer(taskQueue));
        Thread printerThread2 = new Thread(new Printer(taskQueue));

        System.out.println("Starting print simulation...");
        userThread.start();
        printerThread1.start();
        printerThread2.start();
    }
}
