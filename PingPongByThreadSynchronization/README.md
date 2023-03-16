# Java Thread Synchronization: Ping-Pong Example

This project demonstrates various techniques of inter-thread synchronization in Java, using a simple "Ping-Pong" example. In the example, two threads take turns printing "Ping" and "Pong" in sequential order without overlapping or altering the sequence. The goal is to showcase the importance of thread synchronization, which ensures that multiple threads work in a coordinated manner and access shared resources in a predictable, sequential, and safe way.

The techniques demonstrated in this project include:
- Wait-Notify with Interrupt
- CyclicBarrier
- BlockingQueue
- Semaphore
- FutureTask
- CompletionService
- Exchanger
- CountDownLatch
- Phaser

Each technique has its own advantages and use cases, but they all ultimately serve the purpose of synchronizing the execution of multiple threads in a concurrent system.

# Techniques Used in the Application

1. **Synchronized methods**: This technique uses the `synchronized` keyword to ensure that only one thread can execute the `printPing` or `printPong` methods at a time. This maintains the order of printing "Ping" and "Pong".

2. **CyclicBarrier**: The `CyclicBarrier` technique involves two barriers with a fixed number of parties. Threads wait at the barriers until all parties arrive, ensuring the alternating order of "Ping" and "Pong" prints.

3. **BlockingQueue**: This approach uses two `BlockingQueue` instances to control the order of execution. The `printPing` and `printPong` methods exchange signals by putting and taking objects from the queues, ensuring the desired order.

4. **Semaphore**: The `Semaphore` technique employs two semaphores to control access to the printing of "Ping" and "Pong". Threads acquire permits from the semaphores before printing and release permits afterward, maintaining the proper order.

5. **FutureTask**: This method uses an `ExecutorService` to submit callable tasks for "Ping" and "Pong". Each task waits for a random amount of time before printing, and after execution, submits the next task for the other thread to execute.

6. **CompletionService**: In this approach, two `CompletionService` instances are used to manage the execution of "Ping" and "Pong". The `ping` and `pong` methods wait for the completion of the previous task before submitting a new task, maintaining the alternating order.

7. **Exchanger**: The `Exchanger` technique uses an `Exchanger` object to exchange signals between the two threads running the `Ping` and `Pong` classes. The threads use the `exchange` method to control the order of execution.

8. **CountDownLatch**: This method uses two `CountDownLatch` instances to manage the order of execution for "Ping" and "Pong". Each latch is awaited in the corresponding method, and the other latch's count is decremented after printing, ensuring the desired order.

9. **Phaser**: The `Phaser` technique uses two `Phaser` instances to control the order of execution for `printPing` and `printPong`. Threads wait for the current phase of their corresponding phasers and signal the arrival at the other phaser, maintaining the correct "Ping" and "Pong" order.


## Method1 WaitInterrupt: Wait-Notify with Interrupt

The `Method1WaitInterrupt` class demonstrates the use of the wait-notify mechanism with interrupt handling for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, a shared boolean variable, `pongWait`, is used to control the execution order of the two threads: `pinger` and `ponger`.

- The `pinger` thread prints "Ping" when `pongWait` is true, then sets it to false and notifies all waiting threads.
- The `ponger` thread prints "Pong" when `pongWait` is false, then sets it to true and notifies all waiting threads.

Both `pinger` and `ponger` use the `wait()` method to make the current thread wait until it receives a notification from another thread (in this case, through the `notifyAll()` method). When a thread is interrupted, an `InterruptedException` is caught, and the thread's interrupted status is set using `Thread.currentThread().interrupt()`.

In the `main` method, the `pinger` and `ponger` runnables are assigned to two separate `Thread` instances, which are started simultaneously. After 5 seconds, both threads are interrupted, stopping their execution.

## Method2 CyclicBarrier: CyclicBarrier

The `Method2CyclicBarrier` class demonstrates the use of the `CyclicBarrier` technique for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, two `CyclicBarrier` instances, `c1` and `c2`, are used to control the execution order of the two threads: `printPing` and `printPong`.

- The `printPing` method first waits at `c1.await()` and then prints "PING". After that, it waits at `c2.await()`.
- The `printPong` method waits at both `c1.await()` and `c2.await()` before printing "PONG".

Both methods run in a loop and rely on the `CyclicBarrier` instances to maintain synchronization. If a thread is interrupted, an `InterruptedException` is caught, and the thread's interrupted status is set using `Thread.currentThread().interrupt()`.

In the `runPingPong` method, an `ExecutorService` with two threads is created, and both methods are submitted as separate tasks. The threads run simultaneously, and the `CyclicBarrier` instances ensure the correct execution order of "Ping" and "Pong" prints.

## Method3 BlockingQueue: BlockingQueue

The `Method3BlockingQueue` class demonstrates the use of the `BlockingQueue` technique for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, two `BlockingQueue` instances, `pingQueue` and `pongQueue`, are used to control the execution order of the two methods: `printPing` and `printPong`.

- The `printPing` method prints "PING" and then puts an object into `pongQueue`. After that, it waits to take an object from `pingQueue`.
- The `printPong` method waits to take an object from `pongQueue`, then prints "PONG" and puts an object into `pingQueue`.

Both methods run in an infinite loop and rely on the `BlockingQueue` instances to maintain synchronization. If a thread is interrupted, an `InterruptedException` is caught, and the thread's interrupted status is set using `Thread.currentThread().interrupt()`.

In the `main` method, an `ExecutorService` with two threads is created, and both methods are submitted as separate tasks. The threads run simultaneously, and the `BlockingQueue` instances ensure the correct execution order of "Ping" and "Pong" prints.

## Method4 Semaphore: Semaphore

The `Method4Semaphore` class demonstrates the use of the `Semaphore` technique for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, two `Semaphore` instances, `semaphorePing` and `semaphorePong`, are used to control the execution order of the two methods: `printPing` and `printPong`.

- The `printPing` method acquires a permit from `semaphorePing`, prints "PING", and then releases a permit to `semaphorePong`.
- The `printPong` method acquires a permit from `semaphorePong`, prints "PONG", and then releases a permit to `semaphorePing`.

Both methods run in an infinite loop and rely on the `Semaphore` instances to maintain synchronization. If a thread is interrupted, an `InterruptedException` is caught, and the thread's interrupted status is set using `Thread.currentThread().interrupt()`.

In the `main` method, an `ExecutorService` with two threads is created, and both methods are submitted as separate tasks. The threads run simultaneously, and the `Semaphore` instances ensure the correct execution order of "Ping" and "Pong" prints.


## Method5 FeatureTask: FutureTask

The `Method5FeatureTask` class demonstrates the use of `FutureTask` and `ExecutorService` for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, two `Callable<Void>` instances, `callablePing` and `callablePong`, are used to control the execution order of the two methods that print "Ping" and "Pong" respectively.

- The `callablePong` sleeps for a random amount of time, prints "Pong", and then submits the `callablePing` task to the `executor`.
- The `callablePing` sleeps for a random amount of time, prints "Ping", and then submits the `callablePong` task to the `executor`.

Both `callablePong` and `callablePing` run in a loop by submitting each other to the `ExecutorService` when they finish their task. The `executor` is a fixed thread pool with 5 threads.

In the `main` method, an instance of `Method5FeatureTask` is created, and the `callablePing` task is submitted to the `executor` to start the alternating execution of "Ping" and "Pong" prints.

## Method6 CompletionService: CompletionService

The `Method6CompletionService` class demonstrates the use of `CompletionService` and `ExecutorService` for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, two `CompletionService<Void>` instances, `pingCompletionService` and `pongCompletionService`, are used to control the execution order of two methods `ping` and `pong` that print "Ping" and "Pong" respectively.

- The `pong` method submits the `callablePong` task to `pongCompletionService`, waits for a task to complete in `pingCompletionService`, and then repeats the loop.
- The `ping` method waits for a task to complete in `pongCompletionService`, submits the `callablePing` task to `pingCompletionService`, and then repeats the loop.

Both `ping` and `pong` methods run in an infinite loop and rely on the `CompletionService` instances to maintain synchronization. If a thread is interrupted, an `InterruptedException` is caught, and the thread's interrupted status is set using `Thread.currentThread().interrupt()`.

In the `main` method, an instance of `Method6CompletionService` is created, and the `ping` and `pong` methods are submitted as separate tasks to the `executor`. The threads run simultaneously, and the `CompletionService` instances ensure the correct execution order of "Ping" and "Pong" prints.

## Method7 Exchanger: Exchanger

The `Method7Exchanger` class demonstrates the use of `Exchanger` for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, an `Exchanger<Boolean>` instance is used to control the execution order of two `Runnable` implementations, `Ping` and `Pong`, which print "Ping" and "Pong" respectively.

- The `Ping` class receives the `Exchanger<Boolean>` instance in its constructor. In its `run` method, it uses the `exchange` method to send `false` to the `Pong` runnable, allowing it to print "Pong". It then waits to receive a `true` value from the `Pong` runnable before printing "Ping" again.
- The `Pong` class also receives the `Exchanger<Boolean>` instance in its constructor. In its `run` method, it waits for a `true` value from the `Ping` runnable, prints "Pong", and then sends `true` back to the `Ping` runnable.

Both `Ping` and `Pong` run in an infinite loop and rely on the `Exchanger` instance to maintain synchronization. If a thread is interrupted, an `InterruptedException` is caught, and the thread's interrupted status is set using `Thread.currentThread().interrupt()`.

In the `main` method of `Method7Exchanger`, the `Exchanger<Boolean>` instance is created, and the `Ping` and `Pong` runnables are passed to separate threads. The threads run simultaneously, and the `Exchanger` instance ensures the correct execution order of "Ping" and "Pong" prints.

## Method8 Latches: CountDownLatch

The `Method8Latches` class demonstrates the use of `CountDownLatch` for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, two `CountDownLatch` instances, `latchPing` and `latchPong`, are used to control the execution order of two methods, `printPing` and `printPong`, which print "Ping" and "Pong" respectively.

- In the `printPing` method, the thread waits for `latchPing` to reach 0 by calling `await()`. It then prints "Ping", resets `latchPing` to 1, and decrements `latchPong` using `countDown()`.
- In the `printPong` method, the thread first decrements `latchPing` using `countDown()`. Then, it waits for `latchPong` to reach 0 by calling `await()`, prints "Pong", and resets `latchPong` to 1.

Both methods run in an infinite loop and rely on the `CountDownLatch` instances to maintain synchronization. If a thread is interrupted, an `InterruptedException` is caught, and the thread's interrupted status is set using `Thread.currentThread().interrupt()`.

In the `main` method of `Method8Latches`, the `Method8Latches` instance is created, and the `printPing` and `printPong` methods are passed to separate threads in an `ExecutorService`. The threads run simultaneously, and the `CountDownLatch` instances ensure the correct execution order of "Ping" and "Pong" prints.

## Method9 Phaser: Phaser

The `Method9Phaser` class demonstrates the use of `Phaser` for synchronizing two threads that alternately print "Ping" and "Pong".

In this class, two `Phaser` instances, `phaserPing` and `phaserPong`, are used to control the execution order of two methods, `printPing` and `printPong`, which print "Ping" and "Pong" respectively.

- In the `printPing` method, the thread waits for the current phase of `phaserPing` by calling `awaitAdvance()`. It then prints "Ping" and signals the arrival of the `phaserPong` using `arrive()`.
- In the `printPong` method, the thread first signals the arrival of the `phaserPing` using `arrive()`. Then, it waits for the current phase of `phaserPong` by calling `awaitAdvance()`, and prints "Pong".

Both methods run in an infinite loop and rely on the `Phaser` instances to maintain synchronization.

In the `main` method of `Method9Phaser`, the `Method9Phaser` instance is created, and the `printPing` and `printPong` methods are passed to separate threads in an `ExecutorService`. The threads run simultaneously, and the `Phaser` instances ensure the correct execution order of "Ping" and "Pong" prints.
