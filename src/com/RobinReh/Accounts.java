package com.RobinReh;

public interface Accounts {

    /**
     * Print balance in each account and total balance
     */
    void totalBalance();

    /**
     * Transfer balance between indexed accounts
     * @param from sender
     * @param to receiver
     * @param amount amount of balance to transfer
     * @return true if successful false otherwise
     */
    boolean transfer(int from, int to, int amount);

    /**
     * Transfer 100 balance between random accounts 'transfer' amount of times
     * @param transfers How many transfers to be made
     */
    void randomTransfers(int transfers);
}
