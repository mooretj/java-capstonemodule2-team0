package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    private int accountId;
    private int userId;
    private BigDecimal balance;

    public Account(){}
}
