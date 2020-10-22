package com.RobinReh;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueLocks<E> {

    private Queue<E> queue;
    private int size;
    //Lock with fairness
    private ReentrantLock lock = new ReentrantLock(true);
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BlockingQueueLocks(int size){
        queue = new LinkedList<>();
        this.size = size;
    }

    public void put(E item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == size){ // If queue is full wait here
                notFull.await();
            }
            queue.add(item);
            notEmpty.signalAll(); // Signal that the queue is no longer empty

        } finally {
            lock.unlock();
        }

    }

    public E take() throws InterruptedException {
        lock.lock();
        try {
            while(queue.size() == 0){ // If queue is empty wait here
                notEmpty.await();
            }
            E item = queue.remove();
            notFull.signalAll(); // Signal that the queue is no longer full after removing an item
            return item;

        } finally {
            lock.unlock();
        }
    }

    public int length(){
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
