package com.company;

import java.util.concurrent.locks.ReentrantLock;

public class RandomTransferAction implements Runnable{
    int transfers;
    Accounts accs;

    public RandomTransferAction(Accounts accounts, int transfers) {
        this.accs = accounts;
        this.transfers = transfers;

    }

    @Override
    public void run() {
        accs.randomTransfers(transfers);
        System.out.println("DONE"); // Verify that thread finishes
    }
}
