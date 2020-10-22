package com.RobinReh;

import java.util.ArrayList;
import java.util.Random;

public class SeqAccounts implements Accounts{
    ArrayList<Account> accounts;

    public SeqAccounts(int nrofaccs){
        accounts = new ArrayList<>();

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

        for (int i = 0; i<transfers; i++){
            transfer(rand.nextInt(accounts.size()), rand.nextInt(accounts.size()), 100);
        }
    }


}
