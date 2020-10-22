package com.RobinReh;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Quick ProducerConsumer test case using lock based BlockingQueue
 */
public class ProducerConsumer implements Tests{

    private String[] args;
    BlockingQueueLocks<FromTo> queue;
    Accounts accs;
    volatile boolean done = false;
    volatile AtomicInteger produced;

    /**
     * Arguments Max 3 - Mode, Threads, Accounts
     * Mode - Sequential, Lock
     * Threads - number of threads to start
     * Accounts - number of accounts to create
     *
     * @param args Mode, Threads, Accounts
     */
    public ProducerConsumer(String[] args){
        this.args = args;
    }

    /**
     *
     * @throws InterruptedException if thread is interrupted, sleep/await
     */
    @Override
    public void init() throws InterruptedException {
        // Initiate queue of size 10
        BlockingQueueLocks<FromTo> queue = new BlockingQueueLocks<>(10);

        // Create accounts for the test based on input
        accs = new LockAccounts(Integer.parseInt(args[2]));

        // Set amount of consumer threads.
        int threads = Integer.parseInt(args[1]);

        // Create a random to generate random delay and random sender/receiver for balance transfers
        Random rand = new Random();

        // Variable to stop threads, add one for consumed as well to not leave tasks undone
        produced = new AtomicInteger(0);

        // Producer of new tasks
        final Runnable Producer = ( ) -> {

            while(!done){
                if (produced.get() > 100) {
                    done = true;
                    break;
                }
                try {
                    queue.put(new FromTo(rand.nextInt(Integer.parseInt(args[2])), rand.nextInt(Integer.parseInt(args[2])), 100)); // Create random transfers to be done
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Produced, Current size = " + queue.length());
                try {
                    Thread.sleep(500+rand.nextInt(700)); // Sleep to simulate creating new tasks taking some time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                produced.incrementAndGet();
            }
        };

        // Consumer of tasks produced by Producer
        final Runnable Consumer = () -> {

            FromTo temp = new FromTo(0, 0, 0);
            while(!done) {
                try { // Remove from queue
                    temp = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                accs.transfer(temp.from, temp.to, temp.amount); // Execute task from queue
                System.out.println("Consumed, Current size = " + queue.length());
                try {
                    Thread.sleep(1000+rand.nextInt(1000)); // Sleep to simulate execution taking time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        // Start the test
        ExecutorService service = Executors.newFixedThreadPool(threads*2);
        for (int i = 0; i<threads/2+1; i++){
           service.submit(Producer);
        }
        Thread.sleep(100);
        for (int i = 0; i<threads; i++){
            service.submit(Consumer);
        }

        // Shut down and wait for every thread to finish
        service.shutdown();
        service.awaitTermination(60, TimeUnit.SECONDS);
        // Make sure no balance has been lost
        accs.totalBalance();
    }

    // Class only used to create tasks
    private class FromTo{
        public FromTo(int from, int to, int amount){
            this.from = from;
            this.to = to;
            this.amount = amount;
        }
        public int from;
        public int to;
        public int amount;
    }

}
