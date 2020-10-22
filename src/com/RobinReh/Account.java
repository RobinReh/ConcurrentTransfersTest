package com.RobinReh;

public class Account {

    private int balance;
    private String name;

    public Account(String name, int balance) {
        this.balance = balance;
        this.name = name;
    }

    public boolean changeBalance(int change) {
        if ((this.balance + change) >= 0) {
            this.balance += change;
            return true;
        }
        else return false;

    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
            return balance;
     }

    public String getName() {
        return name;
    }
}
