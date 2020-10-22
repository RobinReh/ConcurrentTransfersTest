package com.RobinReh;

public class Main {

    /**
     * Arguments Max 3 - Mode, Threads, Accounts
     * Mode - Sequential, Lock, ProducerConsumer
     * Threads - number of threads to start
     * Accounts - number of accounts to create
     *
     * @param args Mode, Threads, Accounts
     * @throws InterruptedException if unable to initiate tests
     */
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3) {
            System.out.println("Missing arguments!");
            return;
        }

       if (args[0].equals("Lock") || args[0].equals("Sequential")) {
           Tests test = new ConcurrencyTest(args, 10000000);
           test.init();
        } else if (args[0].equals("ProducerConsumer")){
           Tests PC = new ProducerConsumer(args);
           PC.init();
       }


    }


}


