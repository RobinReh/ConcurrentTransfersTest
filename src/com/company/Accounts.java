package com.company;

public interface Accounts {

    /**
     * Print balance in each account and total balance
     */
    public void totalBalance();

    /**
     * Transfer balance between indexed accounts
     * @param from sender
     * @param to receiver
     * @param amount amount of balance to transfer
     * @return
     */
    public boolean transfer(int from, int to, int amount);

    /**
     * Transfer 100 balance between random accounts 'transfer' amount of times
     * @param transfers
     */
    public void randomTransfers(int transfers);
}
