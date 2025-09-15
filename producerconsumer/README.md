## Producer consumer

This is a solution to a common concurrency problem where one or more threads (producers) generate data or tasks, and one or more threads (consumers) process them. The two are connected by a shared, thread safe data structure, typically a queue or buffer.

The core components:
- Producer: The thread or component responsible for creating items (data, tasks, messages) and putting them onto the shared buffer.
- Consumer: The thread or component responsible for taking items from the shared buffer and processing them.
- Shared buffer (or queue): A thread safe data structure the holds the items. It must handle concurrenct access and has fixed capacity. This buffer is synchronization point b/w producers and consumers.

Inshort, the producers must wait if the buffer is full; consumers must wait if the buffer is empty.

### Why use this pattern

- **Decoupling**: Producer and consumer don't need to know about each other.
- **Improved performance and responsiveness**: It allows producers and consumers to operate at their own pace. A producer can quickly generate a batch of tasks and hand them off to the queue, freeing it up to do other work w/o waiting for each task to be completed.
- **Load balancing**: By having multiple consumers pulling from the same queue, we can naturally distribute the workload. If one consumer is busy with a long task, another can pick up the next item.
- **Buffering and smoothing**: The queue can absorb temporary spikes in production, ensuring a smoother, more consistent processing rate for the consumers.

