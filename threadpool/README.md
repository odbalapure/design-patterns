## Thread Pool

Thread pool pattern is a concurrency design pattern used to manage and reuse a limited no. of threads to execute large no. of asynchronous tasks. It decouples the task submission from the mechanics of thread management.

The primary components:
- **Task queue**: A thread safe queue (often `BlockingQueue`) where tasks are submitted to the pool are held before being executed.
- **Worker thread**: A collection of threads that are created when the pool is initialized. Each worker continuosly pulls task from the task queue and executes them.
- **Thread pool manager**: Responsible for creating and managing worker threads, managing task queue, providing an interface for clients to submit tasks and shut down the pool.

Creating and destroying threads is an expensive operation. It consumes significant CPU time and memory. Eg: If an app creates a new thread for every short lived task, it will spend more time on thread management than on doing actual work - leading to poor performance and potential resource exhaustion.

The pattern solves this problem by creating a collection of pre-initialized worker threads. These threads are kept on standby, ready to be assigned tasks. Instead of creating a new thread, the app simply submits a task to the pool. The pool assigns it to an idle thread, and once the task is complete, the thread returns to the pool, for next assignment.

The workflow is straightforward:
- Client submits a task to the thread pool.
- The pool manager places the task in the task queue.
- An idle worker thread dequeues the task and executes it.
- After execution, worker thread loops back to the queue to look for another task.

## Why use the pattern

- **Improved performance**: Reduction of overhead by reusing the existing threads; results in much lower latency and higher thoughput.
- **Resource management**: Control max. # of concurrent threads. This is crucial to prevent app from consuming too many system resources. It prevents "one thread request" model from overwhelming the server under high load.
- **Increased responsiveness**: Abstracts away the complexities of thread management. The developer can focus on definig the tasks to be executed rather than the intricate details of creating, starting and cleanup threads.

> Non daemon threads keep running in the background and will prevent the Java program from exiting until they finish. The JVM only exits when all non-daemon threads have completed. Daemon threads, on the other hand, do not prevent the JVM from exiting.
