package com.demo2020.bankAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class BankAccountController {

    @Autowired
    private BankAccountDAO accountDAO = new BankAccountDAO();

    @GetMapping(path="/getAllAccounts")
    public ArrayList<BankAccount> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    @GetMapping(path="/getAccount")
    public ResponseEntity getAccount(@RequestParam(value="accNum") String accountNumber) {
        BankAccount account = accountDAO.getAccount(accountNumber);
        if (account == null) {
            return new ResponseEntity("User with account number " + accountNumber
                    + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @DeleteMapping(path="/deleteAccount")
    public ResponseEntity deleteAccount(@RequestParam(value="accNum") String accountNumber) {

        BankAccount account = accountDAO.removeAccount(accountNumber);
        if (account == null) {
            return new ResponseEntity("User with account number " + accountNumber
                    + " not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Deleted account with account number: " + accountNumber, HttpStatus.OK);
    }

    @PostMapping(path="/addNewAccount")
    public ResponseEntity addNewAccount(@RequestBody BankAccount newAccount) {
        accountDAO.addNewAccount(newAccount);
        return new ResponseEntity<>("Created new account", HttpStatus.CREATED);
    }

    @PutMapping(path="/increaseBalance")
    public ResponseEntity increaseBalance(@RequestParam(value="accNum") String accountNumber,
                                       @RequestParam(value="amt") double amount) {
        if (amount < 0 ){
            return new ResponseEntity("Please input an amount greater than 0", HttpStatus.BAD_GATEWAY);
        }
        BankAccount account = accountDAO.getAccount(accountNumber);
        if (account == null) {
            return new ResponseEntity("User with account number " + accountNumber
                    + " not found", HttpStatus.NOT_FOUND);
        }
        account.increaseBalance(amount);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping(path="/decreaseBalance")
    public ResponseEntity decreaseBalance(@RequestParam(value="accNum") String accountNumber,
                                       @RequestParam(value="amt") double amount) {
        if (amount < 0 ){
            return new ResponseEntity("Please input an amount greater than 0", HttpStatus.BAD_GATEWAY);
        }


        BankAccount account = accountDAO.getAccount(accountNumber);
        if (account == null) {
            return new ResponseEntity("User with account number " + accountNumber
                    + " not found", HttpStatus.NOT_FOUND);
        }
        double curBalance = account.getBalance();

        if (amount > curBalance ){
            return new ResponseEntity("Please input an amount smaller than current balance: " + curBalance, HttpStatus.BAD_GATEWAY);
        }
        account.decreaseBalance(amount);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}


