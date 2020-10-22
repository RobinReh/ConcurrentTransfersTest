package com.RobinReh;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTest implements  Tests{

    private String[] args;
    private int nrOfTransfers;
    public ConcurrencyTest(String[] args, int nrOfTransfers) {
        this.args = args;
        this.nrOfTransfers = nrOfTransfers;
    }
    @Override
    public void init() throws InterruptedException {

        //Threads to start
        int threads = Integer.parseInt(args[1]);
        //Number of Accounts to create (Class Account)
        int nrOfAccounts = Integer.parseInt(args[2]);

        //Prepare accounts
        Accounts accs;
        if (args[0].equals("Lock")) {
            accs = new LockAccounts(nrOfAccounts);
        } else {
            accs = new SeqAccounts(nrOfAccounts);
        }

        // Start the test
        ExecutorService service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i<threads; i++){
            service.submit(new RandomTransferAction(accs, nrOfTransfers));
        }

        // Shut down and wait for every thread to finish
        service.shutdown();
        service.awaitTermination(30, TimeUnit.SECONDS);
        accs.totalBalance();
    }
}
