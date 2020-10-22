package com.RobinReh;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueueSynchronized<E> {

    private final Queue<E> queue;
    private final int size;
    private final Object notEmpty = new Object();
    private final Object notFull = new Object();

    public BlockingQueueSynchronized(int size) {
        queue = new LinkedList<>();
        this.size = size;
    }

    public synchronized void put(E item) throws InterruptedException {

        while (queue.size() == size) { // If queue is full wait here
            notFull.wait();
        }
        queue.add(item);
        notEmpty.notifyAll(); // Signal that the queue is no longer empty


    }

    public E take() throws InterruptedException {

        while (queue.size() == 0) { // If queue is empty wait here
            notEmpty.wait();
        }
        E item = queue.remove();
        notFull.notifyAll(); // Signal that the queue is no longer full after removing an item
        return item;
    }
}
