package com.demo2020.bankAccount;

import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class BankAccountDAO {
    private ArrayList<BankAccount> bankAccounts = new ArrayList<>();

    public ArrayList<BankAccount> getAllAccounts() {
        return bankAccounts;
    }

    public BankAccount addNewAccount(BankAccount newBankAccount) {
        bankAccounts.add(newBankAccount);
        return newBankAccount;
    }

    public BankAccount removeAccount(String accountNumber) {
        BankAccount account = getAccount(accountNumber);
        if (account == null) return null;
        bankAccounts.remove(account);
        return account;
    }

    public BankAccount getAccount(String accountNumber) {
        for (BankAccount b : bankAccounts) {
            if (b.getAccountNumber().equals(accountNumber)) {
                return b;
            }
        }
        return null;
    }

}
