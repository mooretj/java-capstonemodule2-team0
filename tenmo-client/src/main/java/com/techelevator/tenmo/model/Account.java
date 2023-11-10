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
    public BigDecimal getBalance(){
        return this.balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private int accountId;
    private int userId;
    private BigDecimal balance;



    private String username;

    public Account(){}
}

