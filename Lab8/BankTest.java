package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    Bank bank = new Bank();
    Account[] accounts = new Account[1000];
    double count=35;

    @Test
    void transfer() {

        double first = 0;
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(i);
            accounts[i].plus((int) (Math.random() * 1000));
            first += accounts[i].getBalance();
        }

        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < accounts.length; i++) {

            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {

                    Account from = accounts[(int) (Math.random() * accounts.length)];
                    Account to = accounts[(int) (Math.random() * accounts.length)];
                    bank.transfer(from, to, count);
                }
            });

            threads.add(thread);
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {}

        double second = 0;
        for (var item : accounts) {
            second += item.getBalance();
        }

        Assertions.assertEquals(first, second);
    }
/*
    public void createAccounts(Account[] accs){
        for (int j = 0; j < 100; j++) {
            Account from = accs[(int) (Math.random() * accs.length)];//accs[j];//
            Account to = accs[(int) (Math.random() * accs.length)];//accs[100-j];//
            bank.transfer(from, to, count);
        }
    }

 */
}

/*() -> {
                for (int j = 0; j < 100; j++) {
                    Account from = accounts[(int) (Math.random() * accounts.length)];
                    Account to = accounts[(int) (Math.random() * accounts.length)];
                    bank.transfer(from, to, 1);
                }
            }*/