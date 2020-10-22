package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockAccounts implements Accounts{
    ArrayList<Account> accounts;
    ReentrantLock lock = new ReentrantLock();

    public LockAccounts(int nrofaccs){
        accounts = new ArrayList<Account>();

        for (int i = 0; i<nrofaccs; i++) {
            accounts.add(new Account("Test" + i, 1000));
        }
    }

    public void totalBalance(){
        int total = 0;
        for(Account a: accounts){
            System.out.println("Balance = " + a.getBalance());
            total += a.getBalance();
        }
        System.out.println("Total balance = " + total);
    }


    public boolean transfer(int from, int to, int amount){
        if (from > accounts.size() || to > accounts.size()
                || from < 0 ||to < 0 || amount < 1) return false;

        if (accounts.get(from).getBalance() >= amount) {
                accounts.get(from).changeBalance(-amount);
                accounts.get(to).changeBalance(amount);
            }

        return false;
    }

    public void randomTransfers(int transfers){
        Random rand = new Random();

        for (int i = 0; i<transfers;i++){
            lock.lock();
            try {
                transfer(rand.nextInt(accounts.size()), rand.nextInt(accounts.size()), 100);
            } finally {
                lock.unlock();
            }

        }}



}
